package epam.com.esm.controller.products;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.model.facade.interfaces.entity.products.TagFacade;
import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
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
 * TagController class is the REST controller, which consumes requested TagDtoRequest object, forwards to relevant
 * method in TagFacade facade and produces response TagDtoResponse object or PageData as the result of model's
 * operations
 */
@RestController
@RequestMapping("tags")
@ControllerLink(name = "Tag", mapping = "tags")
public class TagController extends AbstractController {

    /**
     * Holds TagFacade service object
     */
    private final TagFacade tagFacade;

    /**
     * Holds TagResourceModel service object
     */
    private final TagResourceModel tResModel;

    /**
     * Holds GiftCertificateResourceModel service object
     */
    private final GiftCertificateResourceModel gcResModel;

    /**
     * Constructs GiftCertificateController object with provided facade and resource model objects
     *
     * @param tagFacade service, provides operations for TagDtoRequest objects
     * @param gcResModel service, provides link building operations for TagDtoResponse object
     * @param tResModel service, provides link building operations for GiftCertificateDtoResponse object
     */
    @Autowired
    public TagController(TagFacade tagFacade, TagResourceModel tResModel, GiftCertificateResourceModel gcResModel) {
        this.tagFacade = tagFacade;
        this.tResModel = tResModel;
        this.gcResModel = gcResModel;
    }

    /**
     * Consumes TagDtoRequest object, creates TagDtoResponse as the result of facade create
     * operation, builds links for this object and produces response entity
     *
     * @param tDtoRequest object with requested parameters
     * @return {@code HttpEntity<GiftCertificateDtoResponse>} response entity, represents result of create operation
     */
    @PostMapping("/create")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<TagDtoResponse> create(@RequestBody TagDtoRequest tDtoRequest) {
        TagDtoResponse resp = tagFacade.create(tDtoRequest);
        tResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.FIND_ALL);
        tResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        tResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates TagDtoResponse as the result of facade find by id operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of searched tag
     * @return {@code HttpEntity<TagDtoResponse>} response entity, represents result of find by id operation
     */
    @GetMapping("/{id}")
    public HttpEntity<TagDtoResponse> findById(@PathVariable Long id) {
        TagDtoResponse resp = tagFacade.findById(id);

        if (checkRole("ROLE_ADMIN")) {
            tResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.DELETE,
                                       Resources.ADD_GIFT_CERTIFICATES, Resources.DELETE_GIFT_CERTIFICATES,
                                       Resources.FIND_ALL);
        } else {
            tResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES, Resources.FIND_ALL);
        }

        tResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        tResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable, creates TagDtoResponse as the result of facade delete operation,
     * builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre delete tag
     * @return {@code HttpEntity<TagDtoResponse>} response entity, represents result of delete operation
     */
    @DeleteMapping("/{id}/delete")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<TagDtoResponse> delete(@PathVariable Long id) {
        TagDtoResponse resp = tagFacade.delete(id);
        tResModel.buildLinks(resp, Resources.CREATE, Resources.FIND_ALL);
        tResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        tResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all operation, builds links and produces response entity, which contains found items and requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<TagDtoResponse>>} response entity, represents result of
     * find all operation
     */
    @GetMapping("/all")
    public HttpEntity<PageDataResponse<TagDtoResponse>> findAll(WebRequest webRequest) {
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAll(webRequest);

        if (checkRole("ROLE_ADMIN")) {
            tResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                          Resources.FIND_ALL_CERTIFICATED, Resources.FIND_ALL_NOT_CERTIFICATED);
        } else {
            tResModel.buildLinksMenu(pdr, Resources.FIND_ALL);
        }

        tResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        tResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL);
        tResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all certificated operation, builds links and produces response entity, which contains found items and
     * requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<TagDtoResponse>>} response entity, represents result of
     * find all certificated operation
     */
    @GetMapping("/all/certificated")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<PageDataResponse<TagDtoResponse>> findCertificated(WebRequest webRequest) {
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAllCertificated(webRequest);
        tResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        tResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                               Resources.FIND_ALL_CERTIFICATED, Resources.FIND_ALL_NOT_CERTIFICATED);
        tResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL_CERTIFICATED);
        tResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes requested URL parameters from WebRequest, creates PageDataResponse object as the result of facade
     * find all not certificated operation, builds links and produces response entity, which contains found items and
     * requested params
     *
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<TagDtoResponse>>} response entity, represents result of
     * find all not certificated operation
     */
    @GetMapping("/all/not_certificated")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<PageDataResponse<TagDtoResponse>> findNotCertificated(WebRequest webRequest) {
        PageDataResponse<TagDtoResponse> pdr = tagFacade.findAllNotCertificated(webRequest);
        tResModel.buildLinksRecords(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(pdr, Resources.READ, Resources.TAGS);
        tResModel.buildLinksMenu(pdr, Resources.CREATE, Resources.FIND_ALL,
                                               Resources.FIND_ALL_CERTIFICATED, Resources.FIND_ALL_NOT_CERTIFICATED);
        tResModel.buildLinksPaginationMenu(pdr, Resources.FIND_ALL_NOT_CERTIFICATED);
        tResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and requested URL parameters from WebRequest, creates PageDataResponse object as the
     * result of facade find gift certificates operation, builds links and produces response entity, which contains
     * found items and requested params
     *
     * @param id path variable, represents id of entity for which gift certificates will be searched
     * @param webRequest object, holds requested URL parameters
     * @return {@code HttpEntity<PageDataResponse<GiftCertificateDtoResponse>>} response entity, represents result of
     * find gift certificates operation
     */
    @GetMapping("/{id}/gift/certificates")
    public HttpEntity<PageDataResponse<GiftCertificateDtoResponse>> findGiftCertificates(@PathVariable Long id,
                                                                                         WebRequest webRequest){
        PageDataResponse<GiftCertificateDtoResponse> pdr = tagFacade.findGiftCertificates(id, webRequest);
        gcResModel.buildLinksRecords(pdr, Resources.READ, Resources.TAGS);
        gcResModel.buildLinksTags(pdr, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksMenu(pdr, id, Resources.READ, Resources.FIND_ALL);
        tResModel.buildLinksPaginationMenu(pdr, id, Resources.GIFT_CERTIFICATES);
        gcResModel.addLinksMainPage(pdr, Resources.MAIN);
        return ResponseEntity.ok().body(pdr);
    }

    /**
     * Consumes path variable and TagGiftCertificatesDtoRequest object, creates PageDataResponse object as the
     * result of facade add gift certificates operation, builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre update tag
     * @param t object with requested parameters
     * @return {@code HttpEntity<TagDtoResponse>} response entity, represents result of add gift certificates operation
     */
    @PutMapping("/{id}/gift/certificates/add")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<TagDtoResponse> addGiftCertificates(@PathVariable Long id,
                                                          @RequestBody TagGiftCertificatesDtoRequest t) {
        TagDtoResponse resp = tagFacade.addGiftCertificates(id, t);
        tResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        tResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }

    /**
     * Consumes path variable and TagGiftCertificatesDtoRequest object, creates PageDataResponse object as the
     * result of facade add gift certificates operation, builds links for this object and produces response entity
     *
     * @param id path variable, represents id of pre update tag
     * @param t object with requested parameters
     * @return {@code HttpEntity<TagDtoResponse>} response entity, represents result of delete gift certificates
     * operation
     */
    @PutMapping("/{id}/gift/certificates/delete")
    @RolesAllowed("ROLE_ADMIN")
    public HttpEntity<TagDtoResponse> deleteGiftCertificates(@PathVariable Long id,
                                                             @RequestBody TagGiftCertificatesDtoRequest t) {
        TagDtoResponse resp = tagFacade.deleteGiftCertificates(id, t);
        tResModel.buildLinks(resp, Resources.READ, Resources.GIFT_CERTIFICATES);
        tResModel.buildLinksGiftCertificates(resp, Resources.READ, Resources.TAGS);
        tResModel.addLinksMainPage(resp, Resources.MAIN);
        return ResponseEntity.ok().body(resp);
    }
}