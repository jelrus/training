package com.epam.esm.model.facade.impl;

import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.model.facade.interfaces.entity.TagFacade;
import com.epam.esm.model.service.interfaces.entity.TagService;
import com.epam.esm.utils.verifiers.TagDtoVerifier;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TagFacadeImpl is the service class and implementor of TagFacade interface
 * Used for dto requests validation (refuses inappropriate requests) and tag conversion to dto response
 */
@Service
public class TagFacadeImpl implements TagFacade {

    /**
     * Field to hold TagService object
     */
    private final TagService tagService;

    /**
     * Constructs TagFacadeImpl with TagService object
     * NOTE: If tag service object is null it will create and inject this dependency according to @Autowired
     * annotation
     *
     * @param tagService tag service, which provides methods to manipulate tags
     */
    @Autowired
    public TagFacadeImpl(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Consumes dto request, verifies it and produces dto response as the result of creation
     *
     * @param dto requested object
     * @return {@code TagDtoResponse} if passed verification
     */
    @Override
    public TagDtoResponse create(TagDtoRequest dto) {
        return convertToDtoResp(tagService.create(TagDtoVerifier.verifyOnCreate(dto)));
    }

    /**
     * Consumes id value, finds tag and produces dto response if tag was found
     *
     * @param id requested parameter
     * @return {@code TagDtoResponse} created object
     */
    @Override
    public TagDtoResponse findById(Long id) {
        return convertToDtoResp(tagService.findById(id));
    }

    /**
     * Consumes id value, deletes tag and produces dto response if tag was deleted
     *
     * @param id requested parameter
     * @return {@code TagDtoResponse} deleted object
     */
    @Override
    public TagDtoResponse delete(Long id) {
        return convertToDtoResp(tagService.delete(id));
    }

    /**
     * Produces set of dto responses on find all request
     *
     * @return {@code Set<TagDtoResponse>} set of tag dto responses
     */
    @Override
    public Set<TagDtoResponse> findAll() {
        return tagService.findAll().stream().map(this::convertToDtoResp)
                                            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Produces set of dto responses on find all certificated request
     *
     * @return {@code Set<TagDtoResponse>} set of tag dto responses
     */
    @Override
    public Set<TagDtoResponse> findAllCertificated() {
        return tagService.findAllCertificated().stream().map(this::convertToDtoResp)
                                                        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Supplementary method for Tag to TagDtoResponse conversion
     * First converts main tag's values, then converts set of GiftCertificate to GiftCertificateDtoResponse
     *
     * @param tag requested object
     * @return {@code TagDtoResponse} converted object
     */
    private TagDtoResponse convertToDtoResp(Tag tag) {
        TagDtoResponse dtoResp = new TagDtoResponse(tag);

        if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
            dtoResp.setGiftCertificates(tag.getGiftCertificates().stream()
                    .map(GiftCertificateDtoResponse::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }

        return dtoResp;
    }
}