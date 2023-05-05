package com.epam.esm.model.facade.impl;

import com.epam.esm.exception.types.InputException;
import com.epam.esm.model.service.interfaces.entity.TagService;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.verifiers.TagDtoVerifier;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.model.facade.suppliers.FacadeObjectsSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagFacadeImplTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagFacadeImpl tagFacade;

    @Test
    public void willCreateWithNullGiftCertificatesProperDto() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequestWithNullGiftCertificates();
        Tag tag = generateProperTagWithNullGiftCertificates();
        TagDtoResponse tagDtoResponse = generateProperTagResponse(tag);
        when(tagService.create(TagDtoVerifier.verifyOnCreate(tagDtoRequest))).thenReturn(tag);
        TagDtoResponse result = tagFacade.create(tagDtoRequest);
        Assertions.assertEquals(tagDtoResponse, result);
    }

    @Test
    public void willCreateWithEmptyNullGiftCertificatesProperDto() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequestWithEmptyGiftCertificates();
        Tag tag = generateProperTagWithEmptyGiftCertificates();
        TagDtoResponse tagDtoResponse = generateProperTagResponse(tag);
        when(tagService.create(TagDtoVerifier.verifyOnCreate(tagDtoRequest))).thenReturn(tag);
        TagDtoResponse result = tagFacade.create(tagDtoRequest);
        Assertions.assertEquals(tagDtoResponse, result);
    }

    @Test
    public void willCreateWithGiftCertificatesProperDto() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequestWithGiftCertificates();
        Tag tag = generateProperTagWithGiftCertificates();
        TagDtoResponse tagDtoResponse = generateProperTagResponse(tag);
        when(tagService.create(TagDtoVerifier.verifyOnCreate(tagDtoRequest))).thenReturn(tag);
        TagDtoResponse result = tagFacade.create(tagDtoRequest);
        Assertions.assertEquals(tagDtoResponse, result);
    }

    @Test
    public void willThrowOnCorruptedNameNullCreate() {
        TagDtoRequest gcDtoReq = generateNameCorruptedNullTagDtoRequest();
        Assertions.assertThrows(InputException.class, () -> tagFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedNameEmptyCreate() {
        TagDtoRequest gcDtoReq = generateNameCorruptedEmptyTagDtoRequest();
        Assertions.assertThrows(InputException.class, () -> tagFacade.create(gcDtoReq));
    }

    @Test
    public void willThrowOnCorruptedNameBlankCreate() {
        TagDtoRequest gcDtoReq = generateNameCorruptedBlankTagDtoRequest();
        Assertions.assertThrows(InputException.class, () -> tagFacade.create(gcDtoReq));
    }

    @Test
    public void willFindById() {
        Tag tag = generateProperTag();
        TagDtoResponse tagDtoResponse = new TagDtoResponse(tag);
        when(tagService.findById(tag.getId())).thenReturn(tag);
        TagDtoResponse result = tagFacade.findById(tag.getId());
        Assertions.assertEquals(tagDtoResponse, result);
    }

    @Test
    public void willDelete() {
        TagDtoRequest tagDtoReq = generateProperTagDtoRequest();
        Tag tag = tagFromRequest(tagDtoReq);
        TagDtoResponse tagDtoResp = generateProperTagResponse(tag);
        when(tagService.delete(tag.getId())).thenReturn(tag);
        TagDtoResponse result = tagFacade.delete(tag.getId());
        Assertions.assertEquals(tagDtoResp, result);
    }

    @Test
    public void willFindAll() {
        Tag tag = generateProperTagWithEmptyGiftCertificates();
        Set<Tag> tags = new LinkedHashSet<>(Collections.singletonList(tag));
        when(tagService.findAll()).thenReturn(tags);
        Set<TagDtoResponse> result = tagFacade.findAll();
        Assertions.assertFalse(result.isEmpty());

        TagDtoResponse tagDtoResp = generateProperTagResponse(tag);
        Set<TagDtoResponse> converted = new LinkedHashSet<>(Collections.singletonList(tagDtoResp));
        Assertions.assertEquals(converted, result);
    }

    @Test
    public void willFindAllCertificated() {
        Tag tag = generateProperTagWithGiftCertificates();
        Set<Tag> tags = new LinkedHashSet<>(Collections.singletonList(tag));
        when(tagService.findAllCertificated()).thenReturn(tags);
        Set<TagDtoResponse> result = tagFacade.findAllCertificated();
        Assertions.assertFalse(result.isEmpty());

        TagDtoResponse tagDtoResp = generateProperTagResponse(tag);
        Set<TagDtoResponse> converted = new LinkedHashSet<>(Collections.singletonList(tagDtoResp));
        Assertions.assertEquals(converted, result);
    }
}