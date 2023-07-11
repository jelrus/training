package epam.com.esm.controller.user;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.controller.MainController;
import epam.com.esm.model.facade.interfaces.entity.user.UserFacade;
import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.view.dto.request.impl.audit.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.audit.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import epam.com.esm.view.resources.data.Resources;
import epam.com.esm.view.resources.impl.action.OrderResourceModel;
import epam.com.esm.view.resources.impl.products.TagResourceModel;
import epam.com.esm.view.resources.impl.user.UserResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * UserController class is the REST controller, which consumes requested UserDtoRequest object, forwards to relevant
 * method in UserFacade facade and produces response UserDtoResponse object or PageData as the result of model's
 * operations
 */
@RestController
@RequestMapping("users")
@ControllerLink(name = "User", mapping = "users")
public class UserController extends AbstractController {

    /**
     * Holds UserFacade service object
     */
    private final UserFacade userFacade;

    /**
     * Holds UserResourceModel service object
     */
    private final UserResourceModel uResModel;

    /**
     * Holds OrderResourceModel service object
     */
    private final OrderResourceModel oResModel;

    /**
     * Holds TagResourceModel service object
     */
    private final TagResourceModel tResModel;

    /**
     * Constructs UserController object with provided facade and resource model objects
     *
     * @param userFacade service, provides operations for UserDtoRequest objects
     * @param uResModel service, provides link building operations for UserDtoResponse object
     * @param oResModel service, provides link building operations for OrderDtoResponse object
     * @param tResModel service, provides link building operations for TagDtoResponse object
     */
    @Autowired
    public UserController(UserFacade userFacade, UserResourceModel uResModel, OrderResourceModel oResModel,
                          TagResourceModel tResModel) {
        this.userFacade = userFacade;
        this.uResModel = uResModel;
        this.oResModel = oResModel;
        this.tResModel = tResModel;
    }

    /**
     * Consumes UserDtoRequest object, creates UserDtoResponse as the result of facade create
     * operation, builds links for this object and produces response entity
     *
     * @param uDtoRequest object with requested parameters
     * @return {@code HttpEntity<UserDtoResponse>} response entity, represents result of create operation
     */
    @PostMapping("/create")
    public HttpEntity<UserDtoResponse> create(@RequestBody UserDtoRequest uDtoRequest) {
        UserDtoResponse resp = userFacade.create(uDtoRequest);
        uResModel.buildLinks(resp, Resources.READ, Resources.ORDERS, Resources.FIND_ALL);
        uResModel.buildLinksOrders(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates UserDtoResponse as the result of facade find by id operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of searched user
     * @return {@code HttpEntity<UserDtoResponse>} response entity, represents result of find by id operation
     */
    @GetMapping("/{id}")
    public HttpEntity<UserDtoResponse> findById(@PathVariable Long id) {
        UserDtoResponse resp = userFacade.findById(id);
        uResModel.buildLinks(resp, Resources.READ, Resources.UPDATE, Resources.DELETE, Resources.CREATE_ORDER,
                                   Resources.ORDERS, Resources.PURCHASES, Resources.TAGS,
                                   Resources.POPULAR_TAGS, Resources.POPULAR_MAX_TAGS, Resources.FIND_ALL);
        uResModel.buildLinksOrders(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes UserDtoRequest object and path variable, creates UserDtoResponse as the result of
     * facade update operation, builds links for this object and produces response entity
     *
     * @param uDtoRequest object with requested parameters
     * @param id path variable, represents id of pre update user
     * @return {@code HttpEntity<UserDtoResponse>} response entity, represents result of update operation
     */
    @PutMapping("/{id}/update")
    public HttpEntity<UserDtoResponse> update(@RequestBody UserDtoRequest uDtoRequest, @PathVariable Long id) {
        UserDtoResponse resp = userFacade.update(uDtoRequest, id);
        uResModel.buildLinks(resp, Resources.READ, Resources.ORDERS, Resources.FIND_ALL);
        uResModel.buildLinksOrders(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates UserDtoResponse as the result of facade delete operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre delete user
     * @return {@code HttpEntity<UserDtoResponse>} response entity, represents result of delete operation
     */
    @DeleteMapping("/{id}/delete")
    public HttpEntity<UserDtoResponse> delete(@PathVariable Long id) {
        UserDtoResponse resp = userFacade.delete(id);
        uResModel.buildLinks(resp, Resources.CREATE, Resources.FIND_ALL);
        uResModel.buildLinksOrders(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all operation, builds links and produces response entity, which contains found items and requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<UserDtoResponse>>} response entity, represents result of
     * find all operation
     */
    @GetMapping("/all")
    public HttpEntity<PageDataResponse<UserDtoResponse>> findAll(WebRequest webRequest) {
        PageDataResponse<UserDtoResponse> pdr = userFacade.findAll(webRequest);
        uResModel.buildLinksRecords(pdr, Resources.READ, Resources.ORDERS);
        uResModel.buildLinksOrders(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL);
        uResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL);
        uResModel.addLinksMainPage(MainController.class, pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes OrderDtoRequest object and path variable, creates UserDtoResponse object as the result of facade
     * make order operation, builds links and produces response entity, which represents result of make order operation
     *
     * @param oDtoRequest object with requested parameters
     * @param id path variable, represents id of entity for which make order operation applied
     * @return {@code HttpEntity<UserDtoResponse>} response entity, represents result of make order operation
     */
    @PostMapping("/{id}/orders/create")
    public HttpEntity<UserDtoResponse> makeOrder(@RequestBody OrderDtoRequest oDtoRequest, @PathVariable Long id) {
        UserDtoResponse resp = userFacade.makeOrder(id, oDtoRequest);
        uResModel.buildLinks(resp, Resources.READ, Resources.ORDERS, Resources.FIND_ALL);
        uResModel.buildLinksOrders(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable and requested URL parameters from WebRequest, creates PageDataResponse object as the
     * result of facade find orders operation, builds links and produces response entity, which contains
     * found items and requested params
     *
     * @param id path variable, represents id of entity for which orders will be searched
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<OrderDtoResponse>>} response entity, represents result of
     * find orders operation
     */
    @GetMapping("/{id}/orders")
    private HttpEntity<PageDataResponse<OrderDtoResponse>> findOrders(WebRequest webRequest, @PathVariable Long id) {
        PageDataResponse<OrderDtoResponse> pdr = userFacade.findOrders(webRequest, id);
        oResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        oResModel.buildLinksOrders(pdr, Resources.READ, Resources.TAGS);
        uResModel.buildLinksMenu(pdr, id, Resources.READ, Resources.ORDERS, Resources.PURCHASES,
                                          Resources.TAGS, Resources.FIND_ALL);
        uResModel.buildLinksPaginationMenu(pdr, id, Resources.TAGS);
        oResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and requested URL parameters from WebRequest, creates PageDataResponse object as the
     * result of facade find purchases operation, builds links and produces response entity, which contains
     * found items and requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @param id path variable, represents id of entity for which orders will be searched
     * @return {@code HttpEntity<PageDataResponse<PurchaseDataDtoResponse>>} response entity, represents result of
     * find purchases operation
     */
    @GetMapping("/{id}/purchases")
    public HttpEntity<PageDataResponse<PurchaseDataDtoResponse>> findPurchaseData(WebRequest  webRequest,
                                                                                  @PathVariable Long id) {
        PageDataResponse<PurchaseDataDtoResponse> pdr = userFacade.findPurchases(webRequest, id);
        uResModel.buildLinksPurchases(pdr, Resources.READ, Resources.TAGS);
        uResModel.buildLinksPaginationMenu(pdr, id, Resources.PURCHASES);
        uResModel.buildLinksMenu(pdr, id, Resources.READ);
        uResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and requested URL parameters from WebRequest, creates PageDataResponse object as the
     * result of facade find tags operation, builds links and produces response entity, which contains
     * found items and requested params
     *
     * @param id path variable, represents id of entity for which tags will be searched
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<TagDtoResponse>>} response entity, represents result of
     * find tags operation
     */
    @GetMapping("/{id}/tags")
    public HttpEntity<PageDataResponse<TagDtoResponse>> findTags(WebRequest webRequest, @PathVariable Long id) {
        PageDataResponse<TagDtoResponse> pdr = userFacade.findTags(webRequest, id);
        tResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        uResModel.buildLinksMenu(pdr, id, Resources.READ, Resources.ORDERS, Resources.GIFT_CERTIFICATES,
                                          Resources.TAGS, Resources.FIND_ALL);
        uResModel.buildLinksPaginationMenu(pdr, id, Resources.GIFT_CERTIFICATES);
        tResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable, creates WrappedCollection object as the result of facade find tags by popularity
     * operation, builds links and produces response entity, which contains found items
     *
     * @param id path variable, represents id of entity for which popular tags will be searched
     * @return {@code HttpEntity<PageDataResponse<DtoDataTag>>} response entity, represents result of
     * find popular tags operation
     */
    @GetMapping("/{id}/tags/popular")
    public HttpEntity<WrappedCollection<DtoDataTag>> findTagsByPopularity(@PathVariable Long id) {
        WrappedCollection<DtoDataTag> tags = userFacade.findTagsByPopularity(id);
        uResModel.buildLinksDataTags(tags, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.buildLinksWrappedCollectionMenu(tags, id, Resources.READ);
        uResModel.addLinksMainPage(tags, Resources.MAIN);
        return ResponseEntity.ok().body(tags);
    }

    /**
     * Consumes path variable, creates WrappedCollection object as the result of facade find tags by max popularity
     * operation, builds links and produces response entity, which contains found items
     *
     * @param id path variable, represents id of entity for which max popular tags will be searched
     * @return {@code HttpEntity<PageDataResponse<DtoDataTag>>} response entity, represents result of
     * find max popular tags operation
     */
    @GetMapping("/{id}/tags/popular/max")
    public HttpEntity<WrappedCollection<DtoDataTag>> findTagsByMaxPopularity(@PathVariable Long id) {
        WrappedCollection<DtoDataTag> tags = userFacade.findTagsWithMaxCount(id);
        uResModel.buildLinksDataTags(tags, Resources.READ, Resources.GIFT_CERTIFICATES);
        uResModel.buildLinksWrappedCollectionMenu(tags, id, Resources.READ);
        uResModel.addLinksMainPage(tags, Resources.MAIN);
        return ResponseEntity.ok().body(tags);
    }
}