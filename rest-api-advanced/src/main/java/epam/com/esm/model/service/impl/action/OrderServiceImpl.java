package epam.com.esm.model.service.impl.action;

import epam.com.esm.exception.types.EmptyOrderException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.action.OrderDao;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.user.UserDao;
import epam.com.esm.model.service.interfaces.entity.action.OrderService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * OrderServiceImpl class is the service class and implementor of OrderService interface.
 * Provides business logic operations for order
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Holds OrderDao object
     */
    private final OrderDao orderDao;

    /**
     * Holds UserDao object
     */
    private final UserDao userDao;

    /**
     * Holds GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Constructs OrderServiceImpl with OrderDao, GiftCertificateDao and UserDao objects
     *
     * @param orderDao service, provides jpa operations for order
     * @param gcDao service, provides jpa operations for gift certificate
     * @param userDao service, provides jpa operations for user
     */
    @Autowired
    public OrderServiceImpl(OrderDao orderDao, GiftCertificateDao gcDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.gcDao = gcDao;
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
        adjustUser(order);
        adjustOrder(order);
        adjustPurchaseDate(order);
        return getOrderOnCreate(orderDao.create(order));
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
        return getOrder(id);
    }

    /**
     * Updates order
     * Consumes requested order object, adjusts it and produces updated order object as the response
     *
     * @param order provided order object for update
     * @return {@code Order} updated order
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order update(Order order) {
        checkOrderExistence(order.getId());
        adjustUser(order);
        adjustOrder(order);
        return getOrderOnUpdate(order);
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
        return getOrderOnDelete(id);
    }

    /**
     * Finds all orders
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<Order>} object, holds response search params and found orders
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Order> findAll(SearchParamRequest searchParamRequest) {
        return orderDao.findAll(searchParamRequest);
    }

    /**
     * Finds gift certificates by specified order id
     *
     * @param orderId requested parameter value, holds order id value
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findGiftCertificates(Long orderId,
                                                                     SearchParamRequest searchParamRequest) {
        checkOrderExistence(orderId);
        return orderDao.findGiftCertificates(orderId, searchParamRequest);
    }

    /**
     * Supplementary method, adjusts user for order before create/update operations
     *
     * @param order provided order object
     */
    private void adjustUser(Order order) {
        checkForNullUser(order);
        checkForUserExistence(order);
        order.setUser(userDao.findByUsername(order.getUser().getUsername()));
    }

    /**
     * Supplementary method, adjusts order before create/update operations
     *
     * @param order provided order object
     */
    private void adjustOrder(Order order) {
        checkGiftCertificatesNullOrEmpty(order);
        adjustGiftCertificates(order);
        adjustCost(order);
    }

    /**
     * Supplementary method, adjusts gift certificates for order in methods where full order object is required
     *
     * @param order provided order object
     */
    private void adjustGiftCertificates(Order order) {
        order.setGiftCertificates(
                order.getGiftCertificates().stream().map(refineGiftCertificate())
                                                    .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    /**
     * Supplementary method, calculates order's cost
     *
     * @param order provided order object
     */
    private void adjustCost(Order order) {
        order.setCost(
                order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    /**
     * Supplementary method, adjusts purchase date before create operation
     *
     * @param order provided order object
     */
    private void adjustPurchaseDate(Order order) {
        order.setPurchaseDate(LocalDateTime.now());
    }

    /**
     * Supplementary method, converts requested gift certificate with partial values to full object
     *
     * @return {@code Function<GiftCertificate, GiftCertificate>} function for converting
     */
    private Function<GiftCertificate, GiftCertificate> refineGiftCertificate() {
        return gc -> {
            checkForNullGiftCertificate(gc);
            checkForGiftCertificateExistence(gc);
            return gcDao.findByName(gc.getName());
        };
    }

    /**
     * Supplementary method, that produces full order object if create operation was successful
     *
     * @param id requested parameter value, holds order id value
     * @return {@code Order} full order object
     */
    private Order getOrderOnCreate(Long id) {
        checkCreate(id);
        return getOrder(id);
    }

    /**
     * Supplementary method, that produces full order object if update operation was successful
     *
     * @param order provided order object
     * @return {@code Order} full order object
     */
    private Order getOrderOnUpdate(Order order) {
        checkUpdate(order);
        return getOrder(order.getId());
    }

    /**
     * Supplementary method, that produces full order object if delete operation was successful
     *
     * @param id requested parameter value, holds order id value
     * @return {@code Order} full order object
     */
    private Order getOrderOnDelete(Long id) {
        Order deleted = getOrder(id);
        checkDelete(id);
        return deleted;
    }

    /**
     * Supplementary method, that produces full order object
     *
     * @param id requested parameter value, holds order id value
     * @return {@code Order} full order object
     */
    private Order getOrder(Long id) {
        checkOrderExistence(id);
        return orderDao.findById(id);
    }

    /**
     * Supplementary method, checks user for nullity
     * Will throw InputException if user in order is null
     *
     * @param order provided order object
     */
    private void checkForNullUser(Order order) {
        if (order.getUser() == null) {
            throw new InputException("Response failed due unexpected input");
        }
    }

    /**
     * Supplementary method, checks user existence
     * Will throw NotFoundException if user wasn't found
     *
     * @param order provided order object
     */
    private void checkForUserExistence(Order order) {
        if (!userDao.existsByUsername(order.getUser().getUsername())) {
            throw new NotFoundException("Requested resource wasn't found " +
                                        "(username = " + order.getUser().getUsername() + ")");
        }
    }

    /**
     * Supplementary method, checks gift certificate for nullity
     * Will throw InputException if user in order is null
     *
     * @param giftCertificate provided gift certificate object
     */
    private void checkForNullGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            throw new InputException("Response failed due unexpected input");
        }
    }

    /**
     * Supplementary method, checks gift certificate existence
     * Will throw NotFoundException if gift certificate wasn't found
     *
     * @param giftCertificate provided gift certificate object
     */
    private void checkForGiftCertificateExistence(GiftCertificate giftCertificate) {
        if (!gcDao.existsByName(giftCertificate.getName())) {
            throw new NotFoundException("Requested resource wasn't found (name = " + giftCertificate.getName() + ")");
        }
    }

    /**
     * Supplementary method, checks order's gift certificates collection on nullity and emptiness
     * Will throw EmptyOrderException if collection is null or empty
     *
     * @param order provided order object
     */
    private void checkGiftCertificatesNullOrEmpty(Order order) {
        if (order.getGiftCertificates() == null || order.getGiftCertificates().isEmpty()) {
            throw new EmptyOrderException("Order (id = " + order.getId() + " is empty.");
        }
    }

    /**
     * Supplementary method, checks order for existence
     * Will throw NotFoundException if order wasn't found
     *
     * @param id requested parameter value, holds order id value
     */
    private void checkOrderExistence(Long id) {
        if (!orderDao.existsById(id)) {
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Supplementary method, checks if create operation was successful
     * Will throw OperationFailedException if create operation failed
     *
     * @param id requested parameter value, holds order id value
     */
    private void checkCreate(Long id) {
        if (id <= 0) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if update operation was successful
     * Will throw OperationFailedException if update operation failed
     *
     * @param order provided order object
     */
    private void checkUpdate(Order order) {
        if (!orderDao.update(order)) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if delete operation was successful
     * Will throw OperationFailedException if delete operation failed
     *
     * @param id requested parameter value, holds order id value
     */
    private void checkDelete(Long id) {
        if (!orderDao.delete(id)) {
            throw new OperationFailedException("Bad request");
        }
    }
}