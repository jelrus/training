package epam.com.esm.model.dao.impl.action;

import epam.com.esm.model.dao.impl.purchase.PurchaseDataDao;
import epam.com.esm.model.dao.interfaces.entity.action.OrderDao;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.request.builders.FilterBuilder;
import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * OrderDaoImpl class is the service class and implementor of OrderDao interface
 * Used to execute JPA commands on datasource via Entity Manager
 */
@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

    /**
     * Holds EntityManager object, which used to interact with the persistence context
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Holds PurchaseDataDao service object
     */
    private final PurchaseDataDao purchaseDataDao;

    /**
     * Constructs OrderDaoImpl object with provided dao object
     *
     * @param purchaseDataDao service, provides operations for PurchaseData objects
     */
    public OrderDaoImpl(PurchaseDataDao purchaseDataDao) {
        this.purchaseDataDao = purchaseDataDao;
    }

    /**
     * Creates order
     *
     * @param order requested object, holds order values for create
     * @return {@code Long} id of created order
     */
    @Override
    public Long create(Order order) {
        purchaseDataDao.generatePurchaseData(order, order.getUser());
        return entityManager.merge(order).getId();
    }

    /**
     * Finds order by id
     *
     * @param id requested parameter for search, holds order id value
     * @return {@code Order} found order
     */
    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    /**
     * Updates order
     *
     * @param order requested object, holds order values for update
     * @return {@code true} if order was updated
     */
    @Override
    public Boolean update(Order order) {
        return entityManager.merge(order) != null;
    }

    /**
     * Deletes order
     *
     * @param id requested parameter for delete, holds order id value
     * @return {@code true} if order was deleted
     */
    @Override
    public Boolean delete(Long id) {
        entityManager.remove(entityManager.find(Order.class, id));
        return entityManager.find(Order.class, id) == null;
    }

    /**
     * Finds all orders by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Order>} object, which contains response params and list of found orders
     */
    @Override
    public SearchParamResponse<Order> findAll(SearchParamRequest spReq) {
        FilterBuilder<Order> fb = new FilterBuilder<>(entityManager, Order.class, GiftCertificate.class, User.class);
        fb.applyRequest(spReq).addSearchPredicates(Joining.AND).buildPredicates(Joining.AND).addOrders();
        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Checks if requested by id order exists
     *
     * @param id requested parameter for existence check, holds order id value
     * @return {@code true} if order exists by id
     */
    @Override
    public Boolean existsById(Long id) {
        return (Long) entityManager.createQuery("SELECT count(o.id) FROM Order o WHERE o.id = :id")
                                   .setParameter("id", id)
                                   .getSingleResult() == 1;
    }

    /**
     * Finds gift certificates by specified order id and search param request
     *
     * @param orderId requested parameter, holds order id value
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * gift certificates
     */
    @Override
    public SearchParamResponse<GiftCertificate> findGiftCertificates(Long orderId, SearchParamRequest spReq) {
        FilterBuilder<GiftCertificate> fb = new FilterBuilder<>(entityManager, GiftCertificate.class, Tag.class);

        Expression<Long> id = fb.getRoot().join("orders").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, orderId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }
}