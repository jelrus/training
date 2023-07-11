package epam.com.esm.model.dao.impl.user;

import epam.com.esm.model.dao.interfaces.entity.user.UserDao;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.request.builders.FilterBuilder;
import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.dao.ResultTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * UserDaoImpl class is the service class and implementor of UserDao interface
 * Used to execute JPA commands on datasource via Entity Manager
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    /**
     * Holds EntityManager object, which used to interact with the persistence context
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates user
     *
     * @param user requested object, holds user values for create
     * @return {@code Long} id of created user
     */
    @Override
    public Long create(User user) {
        return entityManager.merge(user).getId();
    }

    /**
     * Finds user by id
     *
     * @param id requested parameter for search, holds user id value
     * @return {@code User} found user
     */
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Updates user
     *
     * @param user requested object, holds user values for update
     * @return {@code true} if user was updated
     */
    @Override
    public Boolean update(User user) {
        return entityManager.merge(user) != null;
    }

    /**
     * Deletes user
     *
     * @param id requested parameter for delete, holds user id value
     * @return {@code true} if user was deleted
     */
    @Override
    public Boolean delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
        return entityManager.find(User.class, id) == null;
    }

    /**
     * Finds all users by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<User>} object, which contains response params and list of found users
     */
    @Override
    public SearchParamResponse<User> findAll(SearchParamRequest spReq) {
        FilterBuilder<User> fb = new FilterBuilder<>(entityManager, User.class, Order.class);
        fb.applyRequest(spReq).addSearchPredicates(Joining.AND).buildPredicates(Joining.AND).addOrders();
        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Checks if requested by id user exists
     *
     * @param id requested parameter for existence check, holds user id value
     * @return {@code true} if user exists by id
     */
    @Override
    public Boolean existsById(Long id) {
        String existsById = "SELECT count(u.id) FROM User u WHERE u.id = :id";
        return (Long) entityManager.createQuery(existsById)
                                   .setParameter("id", id)
                                   .getSingleResult() == 1;
    }

    /**
     * Finds user by username
     *
     * @param username requested parameter for search, holds user username value
     * @return {@code true} if user exists by username
     */
    @Override
    public Boolean existsByUsername(String username) {
        String existsByUsername = "SELECT count(u.id) FROM User u WHERE u.username = :username";
        return (Long) entityManager.createQuery(existsByUsername)
                                   .setParameter("username", username)
                                   .getSingleResult() == 1;
    }

    /**
     * Finds user by username
     *
     * @param username requested parameter for search, holds user username value
     * @return {@code User} found user
     */
    @Override
    public User findByUsername(String username) {
        String findByUsername = "SELECT u FROM User u WHERE u.username = :username";
        return (User) entityManager.createQuery(findByUsername)
                                   .setParameter("username", username)
                                   .getSingleResult();
    }

    /**
     * Makes order for user
     *
     * @param userId requested parameter, holds user id value
     * @param orderId requested parameter, holds order id value
     * @return {@code true} if order was made
     */
    @Override
    public Boolean makeOrder(Long userId, Long orderId) {
        User u = entityManager.find(User.class, userId);
        Order o = entityManager.find(Order.class, orderId);
        return u.getOrders().add(o);
    }

    /**
     * Finds orders by specified user id and search param request
     *
     * @param userId requested parameter, holds user id value
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Order>} object, which contains response params and list of found
     * orders
     */
    @Override
    public SearchParamResponse<Order> findOrders(SearchParamRequest spReq, Long userId) {
        FilterBuilder<Order> fb = new FilterBuilder<>(entityManager, Order.class, GiftCertificate.class);

        Expression<Long> id = fb.getRoot().get("user").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, userId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds purchases by specified user id and search param request
     *
     * @param spReq requested object, contains params required for search
     * @param userId requested parameter, holds user id value
     * @return {@code SearchParamResponse<PurchaseData>} object, which contains response params and list of found
     * purchases
     */
    @Override
    public SearchParamResponse<PurchaseData> findPurchases(SearchParamRequest spReq, Long userId) {
        FilterBuilder<PurchaseData> fb = new FilterBuilder<>(entityManager, PurchaseData.class,
                                                                            GiftCertificate.class, User.class);

        Expression<Long> id = fb.getRoot().join("user").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, userId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), fb.runQuery(true));
    }

    /**
     * Finds tags by specified user id and search param request
     *
     * @param userId requested parameter, holds user id value
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found tags
     */
    @Override
    public SearchParamResponse<Tag> findTags(SearchParamRequest spReq, Long userId) {
        FilterBuilder<Tag> fb = new FilterBuilder<>(entityManager, Tag.class, GiftCertificate.class);

        Expression<Long> id = fb.getRoot().join("giftCertificates")
                                          .join("orders")
                                          .join("user").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, userId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds tags by popularity by specified user id
     *
     * @param userId requested parameter, holds user id value
     * @return {@code List<ResultTag>} object, which contains tags sorted descended by popularity
     */
    public List<ResultTag> findTagsByPopularity(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<User> uRoot = query.from(User.class);

        Join<User, Order> uo = uRoot.join("orders");
        Join<Order, GiftCertificate> ogc = uo.join("giftCertificates");
        Join<GiftCertificate, Tag> gct = ogc.join("tags");

        query.multiselect(
                gct.get("name").alias("name"),
                cb.max(uo.get("cost")).alias("cost"),
                cb.count(gct.get("name")).alias("count")
        );

        query.groupBy(gct.get("name"));
        query.orderBy(cb.desc(cb.count(gct.get("name"))));
        query.where(cb.equal(uRoot.get("id"), userId));

        return convertObtainedDataForTags(entityManager.createQuery(query).getResultList());
    }

    /**
     * Finds tags with max popularity by specified user id
     *
     * @param userId requested parameter, holds user id value
     * @return {@code List<ResultTag>} object, which contains tags with max popularity
     */
    @Override
    public List<ResultTag> findTagsWithMaxCount(Long userId) {
        List<ResultTag> res = findTagsByPopularity(userId);
        ResultTag max = res.stream().max(Comparator.comparing(ResultTag::getCount)).get();
        return res.stream().filter(t -> t.getCount().equals(max.getCount()))
                           .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Supplementary method for mapping retrieved data to ResultTag list
     *
     * @param results requested object, which contains raw search data
     * @return {@code List<ResultTag>} converted tags from raw data
     */
    private List<ResultTag> convertObtainedDataForTags(List<Object[]> results) {
        return results.stream().map(rt -> new ResultTag((String) rt[0], (BigDecimal) rt[1], (Long) rt[2]))
                               .collect(Collectors.toCollection(ArrayList::new));
    }
}