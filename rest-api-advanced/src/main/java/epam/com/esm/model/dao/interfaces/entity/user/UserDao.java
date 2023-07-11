package epam.com.esm.model.dao.interfaces.entity.user;

import epam.com.esm.model.dao.interfaces.base.CrudDao;
import epam.com.esm.model.dao.interfaces.supplementary.Existent;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.dao.ResultTag;

import java.util.List;

public interface UserDao extends CrudDao<User, Long>, Existent<Long> {

    /**
     * Contract for user existence check by requested username value
     *
     * @param username requested parameter for search, holds user username value
     * @return {@code true} if user exists by username
     */
    Boolean existsByUsername(String username);

    /**
     * Contract for finding user by username
     *
     * @param username requested parameter for search, holds user username value
     * @return {@code User} found user
     */
    User findByUsername(String username);

    /**
     * Contract for making order for user
     *
     * @param userId requested parameter, holds user id value
     * @param orderId requested parameter, holds order id value
     * @return {@code true} if order was made
     */
    Boolean makeOrder(Long userId, Long orderId);

    /**
     * Contract for finding orders by specified user id and search param request
     *
     * @param userId requested parameter, holds user id value
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<Order>} object, which contains response params and list of found
     * orders
     */
    SearchParamResponse<Order> findOrders(SearchParamRequest searchParamRequest, Long userId);

    SearchParamResponse<PurchaseData> findPurchases(SearchParamRequest spReq, Long userId);

    /**
     * Contract for finding tags by specified user id and search param request
     *
     * @param userId requested parameter, holds user id value
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found tags
     */
    SearchParamResponse<Tag> findTags(SearchParamRequest searchParamRequest, Long userId);

    /**
     * Contract for finding tags by popularity by specified user id
     *
     * @param userId requested parameter, holds user id value
     * @return {@code List<ResultTag>} object, which contains tags sorted descended by popularity
     */
    List<ResultTag> findTagsByPopularity(Long userId);

    /**
     * Contract for finding tags with max popularity by specified user id
     *
     * @param userId requested parameter, holds user id value
     * @return {@code List<ResultTag>} object, which contains tags with max popularity
     */
    List<ResultTag> findTagsWithMaxCount(Long userId);
}