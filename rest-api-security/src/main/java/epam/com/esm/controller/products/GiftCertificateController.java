package epam.com.esm.controller.products;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.model.facade.interfaces.entity.products.GiftCertificateFacade;
import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.resources.data.Resources;
import epam.com.esm.view.resources.impl.products.GiftCertificateResourceModel;
import epam.com.esm.view.resources.impl.products.TagResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.security.RolesAllowed;

/**
 * GiftCertificateController class is the REST controller, which consumes requested GiftCertificateDtoRequest object,
 * forwards to relevant method in GiftCertificateFacade facade and produces response GiftCertificateDtoResponse object
 * or PageData as the result of model's operations
 */
@RestController
@RequestMapping("gift/certificates")
@ControllerLink(name = "Gift Certificate", mapping = "gift/certificates")
public class GiftCertificateController extends AbstractController {

    /**
     * Holds GiftCertificateFacade service object
     */
    private final GiftCertificateFacade gcFacade;

    /**
     * Holds GiftCertificateResourceModel service object
     */
    private final GiftCertificateResourceModel gcResModel;

    /**
     * Holds TagResourceModel service object
     */
    private final TagResourceModel tResModel;

    /**
     * Constructs GiftCertificateController object with provided facade and resource model objects
     *
     * @param gcFacade service, provides operations for GiftCertificateDtoRequest objects
     * @param gcResModel service, provides link building operations for GiftCertificateDtoResponse objects
     * @param tResModel service, provides link building operations for TagDtoResponse objects
     */
    @Autowired
    public GiftCertificateController(GiftCertificateFacade gcFacade,
                                     GiftCertificateResourceModel gcResModel,
                                     TagResourceModel tResModel) {
        this.gcFacade = gcFacade;
        this.gcResModel = gcResModel;
        this.tResModel = tResModel;
    }

    /**
     * Consumes GiftCertificateDtoRequest object, creates GiftCertificateDtoResponse as the result of facade create
     * operation, builds links for this object and produces response entity
     *
     * @param gcDtoRequest object with requested parameters
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of create operation
     */
    @PostMapping("/create")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<GiftCertificateDtoResponse> create(@RequestBody GiftCertificateDtoRequest gcDtoRequest) {
        GiftCertificateDtoResponse resp = gcFacade.create(gcDtoRequest);
        gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS, Resources.FIND_ALL);
        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates GiftCertificateDtoResponse as the result of facade find by id operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of searched gift certificate
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of find by id operation
     */
    @GetMapping("/{id}")
    public HttpEntity<GiftCertificateDtoResponse> findById(@PathVariable Long id) {
        GiftCertificateDtoResponse resp = gcFacade.findById(id);

        if (checkRole("ROLE_ADMIN")) {
            gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS, Resources.UPDATE, Resources.DELETE,
                                        Resources.ADD_TAGS, Resources.DELETE_TAGS, Resources.FIND_ALL);
        } else {
            gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS, Resources.FIND_ALL);
        }

        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);

        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes GiftCertificateDtoRequest object and path variable, creates GiftCertificateDtoResponse as the result of
     * facade update operation, builds links for this object and produces response entity
     *
     * @param gc object with requested parameters
     * @param id path variable, represents id of pre update gift certificate
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of update operation
     */
    @PutMapping("/{id}/update")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<GiftCertificateDtoResponse> update(@RequestBody GiftCertificateDtoRequest gc,
                                                         @PathVariable Long id) {
        GiftCertificateDtoResponse resp = gcFacade.update(gc, id);
        gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS, Resources.FIND_ALL);
        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates GiftCertificateDtoResponse as the result of facade delete operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre delete gift certificate
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of delete operation
     */
    @DeleteMapping("/{id}/delete")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<GiftCertificateDtoResponse> delete(@PathVariable Long id) {
        GiftCertificateDtoResponse resp = gcFacade.delete(id);
        gcResModel.buildLinks(resp, Resources.CREATE, Resources.FIND_ALL);
        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all operation, builds links and produces response entity, which contains found items and requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<GiftCertificateDtoResponse>>} response entity, represents result of
     * find all operation
     */
    @GetMapping("/all")
    public HttpEntity<PageDataResponse<GiftCertificateDtoResponse>> findAll(WebRequest webRequest) {
        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAll(webRequest);

        if (checkRole("ROLE_ADMIN")) {
            gcResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                           Resources.FIND_ALL_TAGGED, Resources.FIND_ALL_NOT_TAGGED);
        } else {
            gcResModel.buildLinksMenu(pdr, Resources.FIND_ALL);
        }

        gcResModel.buildLinksRecords(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL);
        gcResModel.addLinksMainPage(pdr, Resources.MAIN);

        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all tagged operation, builds links and produces response entity, which contains found items and requested
     * params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<GiftCertificateDtoResponse>>} response entity, represents result of
     * find all tagged operation
     */
    @GetMapping("/all/tagged")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<PageDataResponse<GiftCertificateDtoResponse>> findTagged(WebRequest webRequest) {
        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAllTagged(webRequest);
        gcResModel.buildLinksRecords(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                       Resources.FIND_ALL_TAGGED, Resources.FIND_ALL_NOT_TAGGED);
        gcResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL_TAGGED);
        gcResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all not tagged operation, builds links and produces response entity, which contains found items and
     * requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<GiftCertificateDtoResponse>>} response entity, represents result of
     * find all not tagged operation
     */
    @GetMapping("/all/not_tagged")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<PageDataResponse<GiftCertificateDtoResponse>> findNotTagged(WebRequest webRequest) {
        PageDataResponse<GiftCertificateDtoResponse> pdr = gcFacade.findAllNotTagged(webRequest);
        gcResModel.buildLinksRecords(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                       Resources.FIND_ALL_TAGGED, Resources.FIND_ALL_NOT_TAGGED);
        gcResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL_NOT_TAGGED);
        gcResModel.addLinksMainPage(pdr, Resources.MAIN);
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
    public HttpEntity<PageDataResponse<TagDtoResponse>> findTags(@PathVariable Long id, WebRequest webRequest) {
        PageDataResponse<TagDtoResponse> pdr = gcFacade.findTags(id, webRequest);
        tResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksMenu(pdr, id, Resources.READ, Resources.FIND_ALL);
        gcResModel.buildLinksPaginationMenu(pdr, id, Resources.TAGS);
        tResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and GiftCertificateTagsDtoRequest object, creates PageDataResponse object as the
     * result of facade add tags operation, builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre update gift certificate
     * @param gc object with requested parameters
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of add tags operation
     */
    @PutMapping("/{id}/tags/add")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<GiftCertificateDtoResponse> addTags(@PathVariable Long id,
                                                          @RequestBody GiftCertificateTagsDtoRequest gc) {
        GiftCertificateDtoResponse resp = gcFacade.addTags(id, gc);
        gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable and GiftCertificateTagsDtoRequest object, creates PageDataResponse object as the
     * result of facade delete tags operation, builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre update gift certificate
     * @param gc object with requested parameters
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of delete tags
     * operation
     */
    @PutMapping("/{id}/tags/delete")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<GiftCertificateDtoResponse> deleteTags(@PathVariable Long id,
                                                             @RequestBody GiftCertificateTagsDtoRequest gc) {
        GiftCertificateDtoResponse resp = gcFacade.deleteTags(id, gc);
        gcResModel.buildLinks(resp, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }
}