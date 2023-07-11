package epam.com.esm.model.service.interfaces.entity.user;

import epam.com.esm.model.service.interfaces.base.CrudService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.service.ObjectDataTag;

import java.util.List;

/**
 * UserService is the interface that delegates CRUD contracts from ancestors and specific operations for
 * tag logic contracts to implementor
 */
public interface UserService extends CrudService<User, Long> {

    /**
     * Contract for making order
     *
     * @param userId requested parameter value, holds id value
     * @param order object, holds requested values for order
     * @return {@code User} user with added order
     */
    User makeOrder(Long userId, Order order);

    /**
     * Contract for producing SearchParamResponse object with found orders by requested search params
     * and provided id parameter value
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @param userId requested parameter value, holds id value
     * @return {@code SearchParamResponse<Order>} found orders
     */
    SearchParamResponse<Order> findOrders(SearchParamRequest searchParamRequest, Long userId);

    /**
     * Contract for producing SearchParamResponse object with found purchase data by requested search params
     * and provided id parameter value
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @param userId requested parameter value, holds id value
     * @return {@code SearchParamResponse<PurchaseData>} found purchase data
     */
    SearchParamResponse<PurchaseData> findPurchases(SearchParamRequest searchParamRequest, Long userId);

    /**
     * Contract for producing SearchParamResponse object with found tags by requested search params
     * and provided id parameter value
     *
     * @param userId requested parameter value, holds id value
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<Tag>} found tags
     */
    SearchParamResponse<Tag> findTags(SearchParamRequest searchParamRequest, Long userId);

    /**
     * Contract for finding popular tags
     *
     * @param userId requested parameter value, holds id value
     * @return {@code List<ObjectDataTag>} found popular tags
     */
    List<ObjectDataTag> findTagsByPopularity(Long userId);

    /**
     * Contract for finding max popular tags
     *
     * @param userId requested parameter value, holds id value
     * @return {@code List<ObjectDataTag>} found max popular tags
     */
    List<ObjectDataTag> findTagsWithMaxCount(Long userId);
}