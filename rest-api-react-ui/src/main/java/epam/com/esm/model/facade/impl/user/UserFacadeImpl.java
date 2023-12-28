package epam.com.esm.model.facade.impl.user;

import epam.com.esm.model.facade.interfaces.entity.user.UserFacade;
import epam.com.esm.model.service.interfaces.entity.user.UserService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.converters.dto.custom.DtoDataTagConverter;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.action.OrderDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.data.impl.purchase.PurchaseDataHandler;
import epam.com.esm.utils.search.data.impl.user.UserDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.utils.verifiers.action.OrderDtoVerifier;
import epam.com.esm.utils.verifiers.user.UserDtoVerifier;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static epam.com.esm.utils.converters.dto.user.UserDtoConverter.toDto;

/**
 * UserFacadeImpl is the service class and implementor of UserFacade interface
 * Validates and adjusts user dto request before forwarding it to service and converts to dto response
 * after receiving answer from service
 */
@Service
public class UserFacadeImpl implements UserFacade {

    /**
     * Holds UserService object
     */
    private final UserService uService;

    /**
     * Holds UserDtoVerifier object
     */
    private final UserDtoVerifier userDtoVerifier;

    /**
     * Holds OrderDtoVerifier object
     */
    private final OrderDtoVerifier orderDtoVerifier;

    /**
     * Constructs UserFacadeImpl with UserService, UserDtoVerifier and OrderDtoVerifier objects
     *
     * @param uService service, provides logic operations for user
     * @param userDtoVerifier service, provides validations operations for user
     * @param orderDtoVerifier service, provides validations operations for order
     */
    @Autowired
    public UserFacadeImpl(UserService uService, UserDtoVerifier userDtoVerifier, OrderDtoVerifier orderDtoVerifier) {
        this.uService = uService;
        this.userDtoVerifier = userDtoVerifier;
        this.orderDtoVerifier = orderDtoVerifier;
    }

    /**
     * Consumes dto request, validates it and produces dto response as the result of creation
     *
     * @param dto requested object, holds requested values for user
     * @return {@code UserDtoResponse} created user
     */
    @Override
    public UserDtoResponse create(UserDtoRequest dto) {
        return toDto(uService.create(userDtoVerifier.verifyOnCreate(dto)), true);
    }

    /**
     * Consumes id parameter value, finds user and produces dto response if user was found
     *
     * @param id requested parameter value, holds user id value
     * @return {@code UserDtoResponse} found user
     */
    @Override
    public UserDtoResponse findById(Long id) {
        return toDto(uService.findById(id), true);
    }

    /**
     * Consumes dto request and id parameter value, validates it and produces dto response as the result of update
     *
     * @param dto requested object, holds requested values for user
     * @param id requested parameter value, holds user id value
     * @return {@code UserDtoResponse} updated user
     */
    @Override
    public UserDtoResponse update(UserDtoRequest dto, Long id) {
        return toDto(uService.update(userDtoVerifier.verifyOnUpdate(dto, uService.findById(id))), true);
    }

    /**
     * Consumes id value, deletes user and produces dto response if user was deleted
     *
     * @param id requested parameter value, holds user id value
     * @return {@code UserDtoResponse} deleted user
     */
    @Override
    public UserDtoResponse delete(Long id) {
        return toDto(uService.delete(id), true);
    }

    /**
     * Consumes web request, finds by its URL request params users and produces page data response as the result
     * of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<UserDtoResponse>} object, holds found users and search response params
     */
    @Override
    public PageDataResponse<UserDtoResponse> findAll(WebRequest webRequest) {
        AbstractDataHandler<User, UserDtoResponse> adh = new UserDataHandler();
        return adh.processOutput(uService.findAll(adh.processSearch(webRequest)));
    }

    /**
     * Consumes dto request and id parameter value, validates it and produces dto response as the result of making order
     *
     * @param userId requested parameter, holds user id value
     * @param dto requested object, holds order requested values
     * @return {@code UserDtoResponse} user with created order
     */
    @Override
    public UserDtoResponse makeOrder(Long userId, OrderDtoRequest dto) {
        return toDto(uService.makeOrder(userId, orderDtoVerifier.verifyWithoutUser(dto)), true);
    }

    /**
     * Consumes web request and user id parameter value, finds by its URL request params orders of significant
     * user and produces page data response as the result of search
     *
     * @param userId requested parameter value, holds user id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<OrderDtoResponse>} object, holds found orders and search response params
     */
    @Override
    public PageDataResponse<OrderDtoResponse> findOrders(WebRequest webRequest, Long userId) {
        AbstractDataHandler<Order, OrderDtoResponse> adh = new OrderDataHandler();
        return adh.processOutput(uService.findOrders(adh.processSearch(webRequest), userId));
    }

    /**
     * Consumes web request and user id parameter value, finds by its URL request params tags of significant user and
     * produces page data response as the result of search
     *
     * @param userId requested parameter value, holds user id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, holds found tags and search response params
     */
    @Override
    public PageDataResponse<TagDtoResponse> findTags(WebRequest webRequest, Long userId) {
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        return adh.processOutput(uService.findTags(adh.processSearch(webRequest), userId));
    }

    /**
     * Consumes web request and user id parameter value, finds by its URL request params purchase data of significant
     * user and produces page data response as the result of search
     *
     * @param webRequest requested object, contains URL params
     * @param userId requested parameter value, holds user id value
     * @return {@code PageDataResponse<PurchaseDataDtoResponse>} object, holds found purchase data and search
     * response params
     */
    @Override
    public PageDataResponse<PurchaseDataDtoResponse> findPurchases(WebRequest webRequest, Long userId) {
        AbstractDataHandler<PurchaseData, PurchaseDataDtoResponse> adh = new PurchaseDataHandler();
        return adh.processOutput(uService.findPurchases(adh.processSearch(webRequest), userId));
    }

    /**
     * Consumes user id parameter value, finds tags by popularity descended of significant user and produces wrapped
     * collection response as the result of search
     *
     * @param userId requested parameter, holds requested user id value
     * @return {@code WrappedCollection<DtoDataTag>} object, hold found tags
     */
    @Override
    public WrappedCollection<DtoDataTag> findTagsByPopularity(Long userId) {
        return new WrappedCollection<>(uService.findTagsByPopularity(userId)
                                               .stream()
                                               .map(DtoDataTagConverter::toDto)
                                               .collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Consumes user id parameter value, finds tags by max popularity of significant user and produces wrapped
     * collection response as the result of search
     *
     * @param userId requested parameter, holds requested user id value
     * @return {@code WrappedCollection<DtoDataTag>} object, hold found tags
     */
    @Override
    public WrappedCollection<DtoDataTag> findTagsWithMaxCount(Long userId) {
        return new WrappedCollection<>(uService.findTagsWithMaxCount(userId)
                                               .stream()
                                               .map(DtoDataTagConverter::toDto)
                                               .collect(Collectors.toCollection(ArrayList::new)));
    }
}