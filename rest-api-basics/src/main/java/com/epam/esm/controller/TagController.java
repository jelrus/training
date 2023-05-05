package com.epam.esm.controller;

import com.epam.esm.model.facade.interfaces.entity.TagFacade;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * TagController class is the REST controller which consumes JSON as the request, forwards to relevant
 * method in facade and produces JSON as the result of model's operations
 */
@RestController
@RequestMapping("tags")
public class TagController {

    /**
     * Field to hold TagFacade service
     */
    private final TagFacade tagFacade;

    /**
     * Constructs TagController with TagFacade object
     * NOTE: If facade is null it will create and inject this dependency according to @Autowired annotation
     *
     * @param tagFacade GiftCertificateFacade object, which provides operations on gift certificates objects
     */
    @Autowired
    public TagController(TagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }

    /**
     * Consumes request object
     * Produces response object as the result of create operation
     *
     * @param tagReq TagDtoRequest request object for creation
     * @return {@code ResponseEntity<TagDtoResponse>} created tag
     */
    @PostMapping("/create")
    public ResponseEntity<TagDtoResponse> create(@RequestBody TagDtoRequest tagReq) {
        return ResponseEntity.ok().body(tagFacade.create(tagReq));
    }

    /**
     * Consumes URL param
     * Produces response object as the result of find by id operation
     *
     * @param id URL parameter, which holds tag id value
     * @return {@code ResponseEntity<TagDtoResponse>} found tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDtoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tagFacade.findById(id));
    }

    /**
     * Consumes URL param
     * Produces response object as the result of delete operation
     *
     * @param id URL parameter, which holds gift tag id value
     * @return {@code ResponseEntity<TagDtoResponse>} deleted tag
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TagDtoResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(tagFacade.delete(id));
    }

    /**
     * Produces set of response objects
     *
     * @return {@code ResponseEntity<Set<TagDtoResponse>>} set of all tags
     */
    @GetMapping("/all")
    public ResponseEntity<Set<TagDtoResponse>> findAll() {
        return ResponseEntity.ok().body(tagFacade.findAll());
    }

    /**
     * Produces set of response objects
     *
     * @return {@code ResponseEntity<Set<TagDtoResponse>>} set of certificated tags
     */
    @GetMapping("/all/certificated")
    public ResponseEntity<Set<TagDtoResponse>> findAllCertificated()  {
        return ResponseEntity.ok().body(tagFacade.findAllCertificated());
    }
}