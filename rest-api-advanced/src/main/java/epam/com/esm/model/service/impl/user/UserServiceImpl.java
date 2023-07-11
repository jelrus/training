package epam.com.esm.model.service.impl.user;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.action.OrderDao;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.products.TagDao;
import epam.com.esm.model.dao.interfaces.entity.user.UserDao;
import epam.com.esm.model.service.interfaces.entity.user.UserService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.dao.ResultTag;
import epam.com.esm.utils.statistics.service.ObjectDataTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UserServiceImpl class is the service class and implementor of UserService interface.
 * Provides business logic operations for tag
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Holds UserDao object
     */
    private final UserDao userDao;

    /**
     * Holds OrderDao object
     */
    private final OrderDao orderDao;

    /**
     * Holds GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Holds TagDao object
     */
    private final TagDao tagDao;

    /**
     * Constructs UserServiceImpl with UserDao, OrderDao, GiftCertificateDao and TagDao objects
     *
     * @param userDao service, provides jpa operations for user
     * @param orderDao service, provides jpa operations for order
     * @param gcDao service, provides jpa operations for gift certificate
     * @param tagDao service, provides jpa operations for tag
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, OrderDao orderDao, GiftCertificateDao gcDao, TagDao tagDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.gcDao = gcDao;
        this.tagDao = tagDao;
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
        return getUserOnCreate(userDao.create(user));
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
        return getUser(id);
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
        return getUserOnUpdate(user);
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
        return getUserOnDelete(id);
    }

    /**
     * Finds all users
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<User>} object, holds response search params and found users
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<User> findAll(SearchParamRequest searchParamRequest) {
        return userDao.findAll(searchParamRequest);
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
        adjustOrder(order);
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
        return userDao.findOrders(spReq, userId);
    }

    /**
     * Finds purchase data by specified user id
     *
     * @param spReq requested object, holds search params values and found items
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<PurchaseData>} object, holds response search params and found purchase data
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<PurchaseData> findPurchases(SearchParamRequest spReq, Long userId) {
        checkIdExistence(userId);
        return userDao.findPurchases(spReq, userId);
    }

    /**
     * Finds tags by specified user id
     *
     * @param spReq requested object, holds search params values and found items
     * @param userId requested parameter value, holds user id value
     * @return {@code SearchParamResponse<PurchaseData>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findTags(SearchParamRequest spReq, Long userId) {
        checkIdExistence(userId);
        return userDao.findTags(spReq, userId);
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
        return userDao.findTagsByPopularity(userId).stream().map(convertTagsToObjects())
                                                            .collect(Collectors.toCollection(ArrayList::new));
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
        return userDao.findTagsWithMaxCount(userId).stream().map(convertTagsToObjects())
                                                            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Supplementary method, adjusts order data before create
     *
     * @param order requested order
     */
    private void adjustOrder(Order order) {
        order.setGiftCertificates(order.getGiftCertificates().stream()
                                                             .map(refineGiftCertificate())
                                                             .collect(Collectors.toCollection(ArrayList::new)));
        order.setCost(order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                          .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setPurchaseDate(LocalDateTime.now());
    }

    /**
     * Supplementary method, refines gift certificate's missing values
     *
     * @return {@code Function<GiftCertificate, GiftCertificate>} function, takes gift certificate with missing values
     * and returns refined gift certificate
     */
    private Function<GiftCertificate, GiftCertificate> refineGiftCertificate() {
        return gc -> {
            if (gcDao.existsByName(gc.getName())) {
                return gcDao.findByName(gc.getName());
            } else {
                throw new NotFoundException("Requested resource wasn't found (name = " + gc.getName() + ")");
            }
        };
    }

    /**
     * Supplementary method, checks if new name for user doesn't exist or same and compares ids if name already exists
     *
     * @param user requested user
     * @return {@code true} if check was successful
     */
    private boolean checkNameAndCompareIds(User user) {
        return userDao.existsByUsername(user.getUsername())
                && !Objects.equals(userDao.findByUsername(user.getUsername()).getId(), user.getId());
    }

    /**
     * Supplementary method, returns user on create
     *
     * @param id requested parameter value, holds user id value
     * @return {@code User} created user
     */
    private User getUserOnCreate(Long id) {
        checkCreate(id);
        return getUser(id);
    }

    /**
     * Supplementary method, returns user on update
     *
     * @param user requested pre update user
     * @return {@code User} updated user
     */
    private User getUserOnUpdate(User user) {
        checkUpdate(user);
        return getUser(user.getId());
    }

    /**
     * Supplementary method, returns user on delete
     *
     * @param id requested parameter value, holds user id value
     * @return {@code User} deleted user
     */
    private User getUserOnDelete(Long id) {
        User deleted = getUser(id);
        checkDelete(id);
        deleted.getOrders().forEach(o -> orderDao.delete(o.getId()));
        return deleted;
    }

    /**
     * Supplementary method, returns user on order create
     *
     * @param userId requested parameter value, holds user id value
     * @param order requested order
     * @return {@code User} user with created order
     */
    private User getUserOnOrderCreate(Long userId, Order order) {
        checkOrderCreate(userId, order);
        return getUser(userId);
    }

    /**
     * Supplementary method, returns user if exists by id
     *
     * @param id requested parameter value, holds user id value
     * @return {@code User} requested user
     */
    private User getUser(Long id) {
        checkIdExistence(id);
        return userDao.findById(id);
    }

    /**
     * Function, converts ResultTag to ObjectDataTag object
     *
     * @return {@code Function<ResultTag, ObjectDataTag>} conversion function
     */
    private Function<ResultTag, ObjectDataTag> convertTagsToObjects() {
        return rt -> new ObjectDataTag(tagDao.findByName(rt.getName()), rt.getOrderCost(), rt.getCount());
    }

    /**
     * Supplementary method, checks user existence by name
     *
     * @param user requested gift user
     */
    private void checkNameExistence(User user) {
        if (userDao.existsByUsername(user.getUsername())){
            throw new AlreadyExistsException("Resource unavailable");
        }
    }

    /**
     * Supplementary method, checks if user's name suitable for update
     *
     * @param user requested user
     */
    private void checkNameOnUpdate(User user) {
        if (checkNameAndCompareIds(user)) {
            throw new InputException("Requested resource unavailable");
        }
    }

    /**
     * Supplementary method, checks user existence by id
     *
     * @param id requested parameter value, holds user id value
     */
    private void checkIdExistence(Long id) {
        if (!userDao.existsById(id)) {
            throw new NotFoundException("Resource unavailable");
        }
    }

    /**
     * Supplementary method, checks if create was successful
     *
     * @param id requested parameter value, holds user id value
     */
    private void checkCreate(Long id) {
        if (id <= 0) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if update was successful
     *
     * @param user requested user
     */
    private void checkUpdate(User user) {
        if (!userDao.update(user)) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if delete was successful
     *
     * @param id requested parameter value, holds user id value
     */
    private void checkDelete(Long id) {
        if (!userDao.delete(id)) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if order for user was created
     *
     * @param userId requested parameter value, holds user id value
     * @param order requested order
     */
    private void checkOrderCreate(Long userId, Order order) {
        order.setUser(userDao.findById(userId));

        if (!userDao.makeOrder(userId, orderDao.create(order))) {
            throw new OperationFailedException("Bad request");
        }
    }
}