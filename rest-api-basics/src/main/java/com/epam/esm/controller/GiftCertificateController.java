package com.epam.esm.controller;

import com.epam.esm.model.facade.interfaces.entity.GiftCertificateFacade;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import com.epam.esm.utils.search.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Set;

/**
 * GiftCertificateController class is the REST controller which consumes JSON as the request, forwards to relevant
 * method in facade and produces JSON as the result of model's operations
 */
@RestController
@RequestMapping("gift/certificates")
public class GiftCertificateController {

    /**
     * Field to hold GiftCertificateFacade service
     */
    private final GiftCertificateFacade gcFacade;

    /**
     * Constructs GiftCertificateController with GiftCertificateFacade object
     * NOTE: If facade is null it will create and inject this dependency according to @Autowired annotation
     *
     * @param gcFacade GiftCertificateFacade object, which provides operations on gift certificates objects
     */
    @Autowired
    public GiftCertificateController(GiftCertificateFacade gcFacade) {
        this.gcFacade = gcFacade;
    }

    /**
     * Consumes request object
     * Produces response object as the result of create operation
     *
     * @param gcReq GiftCertificateDtoRequest request object for creation
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} created gift certificate
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> create(@RequestBody GiftCertificateDtoRequest gcReq) {
        return ResponseEntity.ok().body(gcFacade.create(gcReq));
    }

    /**
     * Consumes URL param
     * Produces response object as the result of find by id operation
     *
     * @param id URL parameter, which holds gift certificate id value
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} found gift certificate
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(gcFacade.findById(id));
    }

    /**
     * Consumes request object and URL param
     * Produces response object as the result of update operation
     *
     * @param gcReq GiftCertificateDtoRequest request object for update
     * @param id URL parameter, which holds gift certificate id value
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} updated gift certificate
     */
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> update(@RequestBody GiftCertificateDtoRequest gcReq,
                                                             @PathVariable Long id) {
        return ResponseEntity.ok().body(gcFacade.update(gcReq, id));
    }

    /**
     * Consumes URL param
     * Produces response object as the result of delete operation
     *
     * @param id URL parameter, which holds gift certificate id value
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} deleted gift certificate
     */
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(gcFacade.delete(id));
    }

    /**
     * Produces set of response objects
     *
     * @return {@code ResponseEntity<Set<GiftCertificateDtoResponse>>} set of all gift certificates
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<GiftCertificateDtoResponse>> findAll() {
        return ResponseEntity.ok().body(gcFacade.findAll());
    }

    /**
     * Produces set of response objects
     *
     * @return {@code ResponseEntity<Set<GiftCertificateDtoResponse>>} set of tagged gift certificates
     */
    @GetMapping(value = "/all/tagged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<GiftCertificateDtoResponse>> findTagged()  {
        return ResponseEntity.ok().body(gcFacade.findAllTagged());
    }

    /**
     * Consumes URL params from web request
     * Produces set of response objects based on web request params
     *
     * @param webReq object, which holds URL request params for search
     * @return {@code <DataResponse<GiftCertificateDtoResponse>>} as the result of search based on URL params
     */
    @GetMapping(value = "/all/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<GiftCertificateDtoResponse>> findAllSearch(WebRequest webReq) {
        return ResponseEntity.ok().body(gcFacade.findAllSearch(webReq));
    }

    /**
     * Consumes URL params
     * Produces response object as the result of add tag operation
     *
     * @param gCertId URL parameter, which holds gift certificate id value
     * @param tagId URL parameter, which holds tag id value
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} as the result of add tag operation
     */
    @PostMapping(value = "/{gCertId}/tags/add/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> addTag(@PathVariable Long gCertId, @PathVariable Long tagId) {
        return ResponseEntity.ok().body(gcFacade.addTag(gCertId, tagId));
    }

    /**
     * Consumes URL params
     * Produces response object as the result of delete tag operation
     *
     * @param gCertId URL parameter, which holds gift certificate id value
     * @param tagId URL parameter, which holds tag id value
     * @return {@code ResponseEntity<GiftCertificateDtoResponse>} as the result of delete tag operation
     */
    @DeleteMapping(value = "/{gCertId}/tags/delete/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDtoResponse> deleteTag(@PathVariable Long gCertId, @PathVariable Long tagId) {
        return ResponseEntity.ok().body(gcFacade.deleteTag(gCertId, tagId));
    }
}