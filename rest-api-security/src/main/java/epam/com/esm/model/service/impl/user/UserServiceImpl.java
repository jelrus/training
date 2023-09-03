package epam.com.esm.model.service.impl.user;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.impl.purchase.PurchaseService;
import epam.com.esm.model.service.interfaces.entity.user.UserService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.action.OrderRepository;
import epam.com.esm.persistence.repository.impl.products.TagRepository;
import epam.com.esm.persistence.repository.impl.purchase.PurchaseDataRepository;
import epam.com.esm.persistence.repository.impl.user.RoleRepository;
import epam.com.esm.persistence.repository.impl.user.UserRepository;
import epam.com.esm.utils.search.request.builders.SpecificationFilter;
import epam.com.esm.utils.search.request.builders.SpecificationUtil;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.repository.ResultTag;
import epam.com.esm.utils.statistics.service.ObjectDataTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * UserServiceImpl class is the service class and implementor of UserService interface.
 * Provides business logic operations for tag
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Holds UserRepository object
     */
    private final UserRepository uRepo;

    /**
     * Holds OrderRepository object
     */
    private final OrderRepository oRepo;

    /**
     * Holds TagRepository object
     */
    private final TagRepository tRepo;

    /**
     * Holds PurchaseDataRepository object
     */
    private final PurchaseDataRepository pdRepo;

    /**
     * Holds RoleRepository object
     */
    private final RoleRepository rRepo;

    /**
     * Holds BaseCrudRepository object
     */
    private final BaseCrudRepository<User, UserRepository> baseRepo;

    /**
     * Holds PurchaseDataService object
     */
    private final PurchaseService pService;

    /**
     * Holds BCryptPasswordEncoder object
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructs UserServiceImpl with UserRepository, OrderRepository, GiftCertificateRepository, TagRepository
     * and BaseCrudRepository objects
     *
     * @param uRepo                 repository, provides jpa operations for user
     * @param oRepo                 repository, provides jpa operations for order
     * @param tRepo                 repository, provides jpa operations for tag
     * @param pdRepo                repository, provides jpa operations for purchase data
     * @param rRepo                 repository, provides jpa operations for role
     * @param baseRepo              service, provides jpa crud operations
     * @param pService              service, provides purchase data operations
     * @param bCryptPasswordEncoder password encoder
     */
    @Autowired
    public UserServiceImpl(UserRepository uRepo,
                           OrderRepository oRepo,
                           TagRepository tRepo,
                           PurchaseDataRepository pdRepo,
                           RoleRepository rRepo,
                           BaseCrudRepository<User, UserRepository> baseRepo,
                           PurchaseService pService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.uRepo = uRepo;
        this.oRepo = oRepo;
        this.tRepo = tRepo;
        this.pdRepo = pdRepo;
        this.rRepo = rRepo;
        this.baseRepo = baseRepo;
        this.pService = pService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Creates user
     * Consumes requested user object, adjusts it and produces created gift certificate object as the response
     *
     * @param user provided user object for creation
     * @return {@code User} created user
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User create(User user) {
        checkNameExistence(user);
        user.setRoles(Collections.singletonList(rRepo.findByName("ROLE_USER").orElseThrow(
                () -> new NotFoundException("Role not found")
        )));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return baseRepo.create(uRepo, user);
    }

    /**
     * Finds user
     * Consumes user id parameter value and produces found user object as the response
     *
     * @param id requested parameter value, holds user id value
     * @return {@code User} found user
     */
    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return baseRepo.findById(uRepo, id).orElseThrow(
                () -> new NotFoundException("User with (id = " + id + ") not found")
        );
    }

    /**
     * Updates user
     * Consumes requested user object, adjusts it and produces updated user object as the response
     *
     * @param user provided user object for update
     * @return {@code User} updated user
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User update(User user) {
        checkIdExistence(user.getId());
        checkNameOnUpdate(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return baseRepo.update(uRepo, user);
    }

    /**
     * Deletes user
     * Consumes user id parameter value and produces deleted user object as the response
     *
     * @param id requested parameter value, holds user id value
     * @return {@code User} deleted user
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User delete(Long id) {
        checkIdExistence(id);
        return baseRepo.delete(uRepo, id);
    }

    /**
     * Finds all users
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<User>} object, holds response search params and found users
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<User> findAll(SearchParamRequest spReq) {
        return baseRepo.findAll(uRepo, spReq, User.class, Order.class);
    }

    /**
     * Makes order for user
     *
     * @param userId requested parameter value, holds user id value
     * @param order object, holds requested values for order
     * @return {@code User} user with created order
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User makeOrder(Long userId, Order order) {
        checkIdExistence(userId);
        order.setUser(uRepo.findById(userId).get());
        pService.assembleOrder(order);
        return getUserOnOrderCreate(userId, order);
    }

    /**
     * Finds orders by specified user id
     *
     * @param spReq requested object, holds search params values and found items
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<Order>} object, holds response search params and found orders
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Order> findOrders(SearchParamRequest spReq, Long userId) {
        checkIdExistence(userId);

        SpecificationFilter<Order> filterSpecs = new SpecificationFilter<>(spReq, Order.class, GiftCertificate.class);
        SpecificationUtil<Order> util = new SpecificationUtil<>();
        Specification<Order> spec = util.idGetEquals(userId, "user", "id").and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = oRepo.findAll(spec).size();
        List<Order> orders = oRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, orders);
    }

    /**
     * Finds purchase data by specified user id
     *
     * @param req requested object, holds search params values and found items
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<PurchaseData>} object, holds response search params and found purchase data
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<PurchaseData> findPurchases(SearchParamRequest req, Long userId) {
        checkIdExistence(userId);

        SpecificationFilter<PurchaseData> filterSpecs = new SpecificationFilter<>(
                req, PurchaseData.class, GiftCertificate.class, User.class
        );
        SpecificationUtil<PurchaseData> util = new SpecificationUtil<>();
        Specification<PurchaseData> spec = util.idJoinEquals(userId, "user", "id").and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize());

        int total = pdRepo.findAll(spec).size();
        List<PurchaseData> purchases = pdRepo.findAll(spec, pageRequest).getContent();

        return initResponse(req, total, purchases);
    }

    /**
     * Finds tags by specified user id
     *
     * @param req requested object, holds search params values and found items
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<PurchaseData>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findTags(SearchParamRequest req, Long userId) {
        checkIdExistence(userId);

        SpecificationFilter<Tag> filterSpecs = new SpecificationFilter<>(req, Tag.class, GiftCertificate.class);
        SpecificationUtil<Tag> util = new SpecificationUtil<>();
        Specification<Tag> spec = util.idEqualsJoins(userId, "id", "giftCertificates", "orders", "user")
                                      .and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize());

        int total = tRepo.findAll(spec).size();
        List<Tag> tags = tRepo.findAll(spec, pageRequest).getContent();

        return initResponse(req, total, tags);
    }

    /**
     * Finds tags by popularity by specified user id
     *
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<ObjectDataTag>} object, holds response search params and
     * found tags by popularity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ObjectDataTag> findTagsByPopularity(Long userId) {
        checkIdExistence(userId);
        return uRepo.findTagsByPopularityAndMaxCost(userId)
                    .stream()
                    .map(convertTagsToObjects())
                    .collect(Collectors.toList());
    }

    /**
     * Finds tags by max popularity by specified user id
     *
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<ObjectDataTag>} object, holds response search params and
     * found tags by max popularity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ObjectDataTag> findTagsWithMaxCount(Long userId) {
        checkIdExistence(userId);
        List<ObjectDataTag> res = findTagsByPopularity(userId);

        if (res != null && !res.isEmpty()) {
            ObjectDataTag max = res.stream().max(Comparator.comparing(ObjectDataTag::getCount)
                                                           .thenComparing(ObjectDataTag::getCost)).get();
            System.out.println(max.getCount() + " " + max.getCost());
            return res.stream().filter(t -> t.getCount().equals(max.getCount()) && t.getCost().equals(max.getCost()))
                               .collect(Collectors.toCollection(ArrayList::new));
        } else {
            return res;
        }
    }

    /**
     * Supplementary method, checks if new name for user doesn't exist or same and compares ids if name already exists
     *
     * @param user requested user
     * @return {@code true} if check was successful
     */
    private boolean checkNameAndCompareIds(User user) {
        return uRepo.existsByUsername(user.getUsername())
               && !Objects.equals(uRepo.findByUsername(user.getUsername()).get().getId(), user.getId());
    }

    /**
     * Supplementary method, returns user on order create
     *
     * @param userId requested parameter value, holds user id value
     * @param order requested order
     * @return {@code User} user with created order
     */
    private User getUserOnOrderCreate(Long userId, Order order) {
        User user = uRepo.findById(userId).get();
        user.getOrders().add(oRepo.save(order));
        return uRepo.save(user);
    }

    /**
     * Function, converts ResultTag to ObjectDataTag object
     *
     * @return {@code Function<ResultTag, ObjectDataTag>} conversion function
     */
    private Function<ResultTag, ObjectDataTag> convertTagsToObjects() {
        return rt -> new ObjectDataTag(tRepo.findByName(rt.getName()).get(), rt.getOrderCost(), rt.getCount());
    }

    /**
     * Supplementary method, checks user existence by name
     *
     * @param user requested gift user
     */
    private void checkNameExistence(User user) {
        if (uRepo.existsByUsername(user.getUsername())){
            throw new AlreadyExistsException("User with (name = " +  user.getUsername() + ") already exists");
        }
    }

    /**
     * Supplementary method, checks if user's name suitable for update
     *
     * @param user requested user
     */
    private void checkNameOnUpdate(User user) {
        if (checkNameAndCompareIds(user)) {
            throw new InputException("User with (name = " +  user.getUsername() + ") already exists");
        }
    }

    /**
     * Supplementary method, checks user existence by id
     *
     * @param id requested parameter value, holds user id value
     */
    private void checkIdExistence(Long id) {
        if (!uRepo.existsById(id)) {
            throw new NotFoundException("User with (id = " +  id + ") not found");
        }
    }
}