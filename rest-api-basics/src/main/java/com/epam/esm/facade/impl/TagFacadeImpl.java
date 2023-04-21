package com.epam.esm.facade.impl;

import com.epam.esm.facade.interfaces.entity.TagFacade;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.InputException;
import com.epam.esm.service.interfaces.entity.TagService;
import com.epam.esm.view.dto.request.TagDtoRequest;
import com.epam.esm.view.dto.response.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.TagDtoResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagFacadeImpl implements TagFacade {

    private final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final TagService tagService;

    @Autowired
    public TagFacadeImpl(TagService tagService) {
        this.tagService = tagService;
    }

    //TODO: change return type for Long to retrieve full object on response
    @Override
    public TagDtoResponse create(TagDtoRequest tagDtoReq) {
        Tag tag = new Tag();

        if (verifyRequestObjectOnCreate(tagDtoReq)) {
            convertForCreate(tag, tagDtoReq);
        }

        tagService.create(tag);
        return new TagDtoResponse(tag);
    }

    @Override
    public TagDtoResponse findById(Long id) {
        return new TagDtoResponse(tagService.findById(id));
    }

    @Override
    public TagDtoResponse delete(Long id) {
        TagDtoResponse tagDtoResp = new TagDtoResponse(tagService.findById(id));
        tagService.delete(id);
        return tagDtoResp;
    }

    @Override
    public Set<TagDtoResponse> findAll() {
        return tagService.findAll().stream().map(TagDtoResponse::new).collect(Collectors.toSet());
    }

    @Override
    public Set<TagDtoResponse> findAllCertificated() {
        return tagService.findAllCertificated().stream().map(TagDtoResponse::new).collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificateDtoResponse> findGiftCertificatesByTag(Long tagId) {
        return tagService.findGiftCertificatesByTag(tagId).stream().map(GiftCertificateDtoResponse::new)
                                                                   .collect(Collectors.toSet());
    }

    @Override
    public TagDtoResponse addGiftCertificate(Long tagId, Long giftCertificateId) {
        tagService.addGiftCertificate(tagId, giftCertificateId);
        return new TagDtoResponse(tagService.findById(tagId));
    }

    @Override
    public TagDtoResponse deleteGiftCertificate(Long tagId, Long giftCertificateId) {
        tagService.deleteGiftCertificate(tagId, giftCertificateId);
        return new TagDtoResponse(tagService.findById(tagId));
    }

    public Boolean verifyRequestObjectOnCreate(TagDtoRequest tagDtoReq) {
        if (!StringUtils.isBlank(tagDtoReq.getName())) {
            return true;
        } else {
            LOGGER_ERROR.error("[Tag] Request object verification failed");
            throw new InputException("Request object verification failed");
        }
    }

    private void convertForCreate(Tag tag, TagDtoRequest tagDtoReq) {
        tagDtoReq.setName(tag.getName());
    }
}