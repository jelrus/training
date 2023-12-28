package epam.com.esm.model.facade.interfaces.entity.user;

import epam.com.esm.model.facade.interfaces.base.CrudFacade;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * UserFacade is the interface that delegates CRUD contracts from ancestors and specific operations for
 * tag logic contracts to implementor
 */
public interface UserFacade extends CrudFacade<UserDtoRequest, UserDtoResponse, Long> {

    /**
     * Contract for making order by requested order and user id value
     *
     * @param userId requested parameter, holds user id value
     * @param order requested object, holds order requested values
     * @return {@code UserDtoResponse} response object
     */
    UserDtoResponse makeOrder(Long userId, OrderDtoRequest order);

    /**
     * Contract for finding orders by requested user id value and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @param userId requested parameter, holds requested user id value
     * @return {@code PageDataResponse<OrderDtoResponse>} object, contains found order dto responses and response params
     */
    PageDataResponse<OrderDtoResponse> findOrders(WebRequest webRequest, Long userId);

    /**
     * Contract for finding purchase data by requested user id value and producing PageDataResponse as the result
     *
     * @param userId requested parameter, holds requested user id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<PurchaseDataDtoResponse>} object, contains found purchase data dto responses and
     * response params
     */
    PageDataResponse<PurchaseDataDtoResponse> findPurchases(WebRequest webRequest, Long userId);

    /**
     * Contract for finding tags by requested user id value and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @param userId requested parameter, holds requested user id value
     * @return {@code PageDataResponse<TagDtoResponse>} object, contains found gift certificates dto responses and
     * response params
     */
    PageDataResponse<TagDtoResponse> findTags(WebRequest webRequest, Long userId);

    /**
     * Contract for finding tags by popularity by requested user id value
     *
     * @param userId requested parameter, holds requested user id value
     * @return {@code WrappedCollection<DtoDataTag>} found dto data tags
     */
    WrappedCollection<DtoDataTag> findTagsByPopularity(Long userId);

    /**
     * Contract for finding tags by max popularity by requested user id value
     *
     * @param userId requested parameter, holds requested user id value
     * @return {@code WrappedCollection<DtoDataTag>} found dto data tags
     */
    WrappedCollection<DtoDataTag> findTagsWithMaxCount(Long userId);
}