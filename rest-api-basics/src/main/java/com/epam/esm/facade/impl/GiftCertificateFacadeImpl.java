package com.epam.esm.facade.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.facade.interfaces.entity.GiftCertificateFacade;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.utils.search.dao.SearchParamResponse;
import com.epam.esm.view.dto.request.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.request.TagDtoRequest;
import com.epam.esm.view.dto.response.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    private final GiftCertificateService gcService;

    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateService gcService) {
        this.gcService = gcService;
    }

    //TODO: change return type for Long to retrieve full object on response
    @Override
    public GiftCertificateDtoResponse create(GiftCertificateDtoRequest gcDtoReq) {
        GiftCertificate gc = new GiftCertificate();

        if (verifyRequestObjectOnCreate(gcDtoReq)) {
            convertForCreate(gc, gcDtoReq);
        }
        System.out.println("Gift cert: " + gc);
        gcService.create(gc);
        return new GiftCertificateDtoResponse(gc);
    }

    @Override
    public GiftCertificateDtoResponse update(GiftCertificateDtoRequest gcDtoReq, Long id) {
        GiftCertificate gc = new GiftCertificate();

        if (verifyRequestObjectOnCreate(gcDtoReq)) {
            convertForUpdate(gc, gcDtoReq, id);
        }

        gcService.update(gc);
        return new GiftCertificateDtoResponse(gcService.findById(id));
    }

    @Override
    public GiftCertificateDtoResponse findById(Long id) {
        return new GiftCertificateDtoResponse(gcService.findById(id));
    }

    @Override
    public GiftCertificateDtoResponse delete(Long id) {
        GiftCertificateDtoResponse gcDtoResp = new GiftCertificateDtoResponse(gcService.findById(id));
        gcService.delete(id);
        return gcDtoResp;
    }

    @Override
    public Set<GiftCertificateDtoResponse> findAll() {
        return gcService.findAll().stream().map(GiftCertificateDtoResponse::new).collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificateDtoResponse> findAllTagged() {
        return gcService.findAllTagged().stream().map(GiftCertificateDtoResponse::new).collect(Collectors.toSet());
    }

    @Override
    public SearchParamResponse<GiftCertificate> findAllParamSearch(WebRequest webRequest) {
        //TODO: new class to populate request
        return null;
    }

    @Override
    public Set<TagDtoResponse> findTagsByGiftCertificate(Long giftCertificateId) {
        return gcService.findTagsByGiftCertificate(giftCertificateId).stream().map(TagDtoResponse::new)
                                                                              .collect(Collectors.toSet());
    }

    @Override
    public GiftCertificateDtoResponse addTag(Long giftCertificateId, Long tagId) {
        gcService.addTag(giftCertificateId, tagId);
        return new GiftCertificateDtoResponse(gcService.findById(giftCertificateId));
    }

    @Override
    public GiftCertificateDtoResponse deleteTag(Long giftCertificateId, Long tagId) {
        gcService.deleteTag(giftCertificateId, tagId);
        return new GiftCertificateDtoResponse(gcService.findById(giftCertificateId));
    }

    public Boolean verifyRequestObjectOnCreate(GiftCertificateDtoRequest request) {
        return true;
    }

    public Boolean verifyRequestObjectOnUpdate(GiftCertificateDtoRequest request) {
        return true;
    }

    private void convertForCreate(GiftCertificate gc, GiftCertificateDtoRequest gcDtoReq) {
        gc.setName(gcDtoReq.getName());
        gc.setDescription(gcDtoReq.getDescription());
        gc.setPrice(gcDtoReq.getPrice());
        gc.setDuration(gcDtoReq.getDuration());
        gc.setCreateDate(gcDtoReq.getCreateDate());
        gc.setLastUpdateDate(gcDtoReq.getLastUpdateDate());
        System.out.println("Dto req: " + gcDtoReq.getTags());
        gc.setTags(convertTagsToDtoReq(gcDtoReq.getTags()));
        System.out.println("Tags after: " + gc.getTags());
    }

    private void convertForUpdate(GiftCertificate gc, GiftCertificateDtoRequest gcDtoReq, Long id) {
        gc.setId(id);
        convertForCreate(gc, gcDtoReq);
    }

    private Set<Tag> convertTagsToDtoReq(Set<TagDtoRequest> tagsDtoReq) {
        Set<Tag> tags = new LinkedHashSet<>();

        for (TagDtoRequest t: tagsDtoReq) {
            Tag tag = new Tag();
            tag.setName(t.getName());
            tags.add(tag);
        }
        return tags;
    }
}