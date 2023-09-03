package epam.com.esm.controller.action;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.model.facade.interfaces.entity.action.OrderFacade;
import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.resources.data.Resources;
import epam.com.esm.view.resources.impl.action.OrderUserResourceModel;
import epam.com.esm.view.resources.impl.products.GiftCertificateResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.security.RolesAllowed;

/**
 * OrderController class is the REST controller, which consumes requested OrderDtoRequest object, forwards to relevant
 * method in OrderFacade facade, builds links for response OrderUserDtoResponse object or PageData and produces response
 * as the result of model's operations
 */
@RestController
@RequestMapping("orders")
@ControllerLink(name = "Order", mapping = "orders")
public class OrderController extends AbstractController {

    /**
     * Holds OrderFacade service object
     */
    private final OrderFacade orderFacade;

    /**
     * Holds OrderUserResourceModel service object
     */
    private final OrderUserResourceModel ouResModel;

    /**
     * Holds GiftCertificateResourceModel service object
     */
    private final GiftCertificateResourceModel gcResModel;

    /**
     * Constructs OrderController object with provided facade and resource model objects
     *
     * @param orderFacade service, provides operations for OrderUserDtoRequest objects
     * @param ouResModel service, provides link building operations for OrderUserDtoResponse objects
     * @param gcResModel service, provides link building operations for GiftCertificateDtoResponse objects
     */
    @Autowired
    public OrderController(OrderFacade orderFacade, OrderUserResourceModel ouResModel,
                           GiftCertificateResourceModel gcResModel) {
        this.orderFacade = orderFacade;
        this.ouResModel = ouResModel;
        this.gcResModel = gcResModel;
    }

    /**
     * Consumes OrderDtoRequest object, creates OrderUserDtoResponse as the result of facade create operation,
     * builds links for this object and produces response entity
     *
     * @param ouDtoRequest object with requested parameters
     * @return {@code HttpEntity<OrderUserDtoResponse>} response entity, represents result of create operation
     */
    @PostMapping("/create")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<OrderUserDtoResponse> create(@RequestBody OrderDtoRequest ouDtoRequest) {
        OrderUserDtoResponse resp = orderFacade.create(ouDtoRequest);
        ouResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.FIND_ALL);
        ouResModel.buildLinksUser(resp, Resources.READ, Resources.ORDERS);
        ouResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.ORDERS);
        ouResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates OrderUserDtoResponse as the result of facade find by id operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of searched order
     * @return {@code HttpEntity<OrderUserDtoResponse>} response entity, represents result of find by id operation
     */
    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public HttpEntity<OrderUserDtoResponse> findById(@PathVariable Long id) {
        checkPermissions("ROLE_ADMIN", "ROLE_USER", orderFacade.findById(id).getUser().getUsername());
        OrderUserDtoResponse resp = orderFacade.findById(id);

        if (checkRole("ROLE_ADMIN")) {
            ouResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.DELETE,
                                        Resources.FIND_ALL);
        } else {
            ouResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.FIND_ALL);
        }

        ouResModel.buildLinksUser(resp, Resources.READ, Resources.ORDERS);
        ouResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        ouResModel.addLinksMainPage(resp, Resources.MAIN);

        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates OrderUserDtoResponse as the result of facade delete operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre delete order
     * @return {@code HttpEntity<OrderUserDtoResponse>} response entity, represents result of delete operation
     */
    @DeleteMapping("/{id}/delete")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<OrderUserDtoResponse> delete(@PathVariable Long id) {
        OrderUserDtoResponse resp = orderFacade.delete(id);
        ouResModel.buildLinks(resp, Resources.CREATE, Resources.FIND_ALL);
        ouResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        ouResModel.buildLinksUser(resp, Resources.READ, Resources.ORDERS);
        ouResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all operation, builds links and produces response entity, which contains found items and requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<OrderUserDtoResponse>>} response entity, represents result of find all
     * operation
     */
    @GetMapping("/all")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<PageDataResponse<OrderUserDtoResponse>> findAll(WebRequest webRequest) {
        PageDataResponse<OrderUserDtoResponse> pdr = orderFacade.findAll(webRequest);
        ouResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        ouResModel.buildLinksRecordUser(pdr, Resources.READ, Resources.ORDERS);
        ouResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        ouResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL);
        ouResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL);
        ouResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and requested URL parameters from WebRequest, creates PageDataResponse object as the
     * result of facade find gift certificates operation, builds links and produces response entity, which contains
     * found items and requested params
     *
     * @param id path variable, represents id of entity for which gift certificates will be searched
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<OrderUserDtoResponse>>} response entity, represents result of
     * find gift certificates operation
     */
    @GetMapping("/{id}/gift/certificates")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public HttpEntity<PageDataResponse<GiftCertificateDtoResponse>> findGiftCertificates(@PathVariable Long id,
                                                                                         WebRequest webRequest) {
        checkPermissions("ROLE_ADMIN", "ROLE_USER", orderFacade.findById(id).getUser().getUsername());
        PageDataResponse<GiftCertificateDtoResponse> pdr = orderFacade.findGiftCertificates(id, webRequest);
        gcResModel.buildLinksRecords(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        ouResModel.buildLinksMenu(pdr, id, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.FIND_ALL);
        ouResModel.buildLinksPaginationMenu(pdr, id, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }
}