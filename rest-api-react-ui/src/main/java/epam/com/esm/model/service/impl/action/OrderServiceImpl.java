package epam.com.esm.model.service.impl.action;

import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.impl.purchase.PurchaseService;
import epam.com.esm.model.service.interfaces.entity.action.OrderService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.action.OrderRepository;
import epam.com.esm.persistence.repository.impl.products.GiftCertificateRepository;
import epam.com.esm.utils.search.request.builders.SpecificationFilter;
import epam.com.esm.utils.search.request.builders.SpecificationUtil;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;


/**
 * OrderServiceImpl class is the service class and implementor of OrderService interface.
 * Provides business logic operations for order
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Holds OrderRepository object
     */
    private final OrderRepository oRepo;

    /**
     * Holds GiftCertificateRepository object
     */
    private final GiftCertificateRepository gcRepo;

    /**
     * Holds BaseCrudRepository object
     */
    private final BaseCrudRepository<Order, BaseRepository<Order>> baseRepo;

    /**
     * Holds PurchaseDataService object
     */
    private final PurchaseService purchaseService;

    /**
     * Constructs OrderServiceImpl with OrderRepository, GiftCertificateRepository and
     * BaseCrudRepository objects
     *
     * @param oRepo     repository, provides jpa operations for order
     * @param gcRepo        repository, provides jpa operations for gift certificate
     * @param baseRepo  service, provides jpa crud operations
     * @param purchaseService service, provides purchase data operations
     */
    @Autowired
    public OrderServiceImpl(OrderRepository oRepo,
                            GiftCertificateRepository gcRepo,
                            BaseCrudRepository<Order, BaseRepository<Order>> baseRepo,
                            PurchaseService purchaseService) {
        this.oRepo = oRepo;
        this.gcRepo = gcRepo;
        this.baseRepo = baseRepo;
        this.purchaseService = purchaseService;
    }

    /**
     * Creates order
     * Consumes requested order object, adjusts it and produces created order object as the response
     *
     * @param order provided order object for creation
     * @return {@code Order} created order
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order create(Order order) {
        purchaseService.assembleOrder(order);
        return baseRepo.create(oRepo, order);
    }

    /**
     * Finds order
     * Consumes order id parameter value and produces found order object as the response
     *
     * @param id requested parameter value, holds order id value
     * @return {@code Order} found order
     */
    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return baseRepo.findById(oRepo, id).orElseThrow(
                () -> new NotFoundException("Order with (id = " + id + ") not found")
        );
    }

    /**
     * Deletes order
     * Consumes order id parameter value and produces deleted order object as the response
     *
     * @param id requested parameter value, holds order id value
     * @return {@code Order} deleted order
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order delete(Long id) {
        checkOrderExistence(id);
        return baseRepo.delete(oRepo, id);
    }

    /**
     * Finds all orders
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<Order>} object, holds response search params and found orders
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Order> findAll(SearchParamRequest spReq) {
        return baseRepo.findAll(oRepo, spReq, Order.class, GiftCertificate.class, User.class);
    }

    /**
     * Finds gift certificates by specified order id
     *
     * @param orderId requested parameter value, holds order id value
     * @param spReq requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findGiftCertificates(Long orderId,
                                                                     SearchParamRequest spReq) {
        checkOrderExistence(orderId);

        SpecificationFilter<GiftCertificate> filterSpecs = new SpecificationFilter<>(
                spReq, GiftCertificate.class, Tag.class
        );
        SpecificationUtil<GiftCertificate> util = new SpecificationUtil<>();
        Specification<GiftCertificate> spec = util.idJoinEquals(orderId, "orders", "id").and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = gcRepo.findAll(spec).size();
        Page<GiftCertificate> gCerts = gcRepo.findAll(spec, pageRequest);

        return initResponse(spReq, total, gCerts.getContent());
    }

    /**
     * Supplementary method, checks order for existence
     * Will throw NotFoundException if order wasn't found
     *
     * @param id requested parameter value, holds order id value
     */
    private void checkOrderExistence(Long id) {
        if (!oRepo.existsById(id)) {
            throw new NotFoundException("Order with (id = " + id + ") not found");
        }
    }
}