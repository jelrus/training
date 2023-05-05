package com.epam.esm.model.facade.suppliers;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.date.IsoDateFormatter;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class FacadeObjectsSupplier {

    public static GiftCertificateDtoRequest generateProperDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Gift Certificate Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        dtoReq.setUpdate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateProperDtoRequestNullTags() {
        GiftCertificateDtoRequest dtoReq = generateProperDtoRequest();
        dtoReq.setTags(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateProperDtoRequestEmptyTags() {
        GiftCertificateDtoRequest dtoReq = generateProperDtoRequest();
        dtoReq.setTags(Collections.emptySet());
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateProperDtoRequestWithTags() {
        GiftCertificateDtoRequest dtoReq = generateProperDtoRequest();
        dtoReq.setTags(new LinkedHashSet<>(Collections.singletonList(generateProperDtoRequestTag())));
        return dtoReq;
    }

    public static TagDtoRequest generateProperDtoRequestTag() {
        TagDtoRequest tag = new TagDtoRequest();
        tag.setName("Test Tag Request");
        return tag;
    }

    public static GiftCertificate generateProperGiftCertificate() {
        GiftCertificate gCert = new GiftCertificate();
        gCert.setId(1L);
        gCert.setName("Test Gift Certificate Request");
        gCert.setDescription("Test Description");
        gCert.setPrice(new BigDecimal("100.00"));
        gCert.setDuration(1);
        gCert.setCreate(new Date(System.currentTimeMillis()));
        gCert.setUpdate(new Date(System.currentTimeMillis()));
        return gCert;
    }

    public static GiftCertificate generateProperGiftCertificateNullTags() {
        GiftCertificate gCert = generateProperGiftCertificate();
        gCert.setTags(null);
        return gCert;
    }

    public static GiftCertificate generateProperGiftCertificateEmptyTags() {
        GiftCertificate gCert = generateProperGiftCertificate();
        gCert.setTags(Collections.emptySet());
        return gCert;
    }

    public static GiftCertificate generateProperGiftCertificateWithTags() {
        GiftCertificate gCert = generateProperGiftCertificate();
        gCert.setTags(new LinkedHashSet<>(Collections.singletonList(generateProperTag())));
        return gCert;
    }

    public static TagDtoRequest generateProperTagDtoRequest() {
        TagDtoRequest tagDtoRequest = new TagDtoRequest();
        tagDtoRequest.setName("Test Tag");
        return tagDtoRequest;
    }

    public static TagDtoRequest generateNameCorruptedNullTagDtoRequest() {
        TagDtoRequest tagDtoRequest = new TagDtoRequest();
        tagDtoRequest.setName(null);
        return tagDtoRequest;
    }

    public static TagDtoRequest generateNameCorruptedEmptyTagDtoRequest() {
        TagDtoRequest tagDtoRequest = new TagDtoRequest();
        tagDtoRequest.setName("");
        return tagDtoRequest;
    }

    public static TagDtoRequest generateNameCorruptedBlankTagDtoRequest() {
        TagDtoRequest tagDtoRequest = new TagDtoRequest();
        tagDtoRequest.setName(" ");
        return tagDtoRequest;
    }

    public static Tag tagFromRequest(TagDtoRequest tagDtoRequest) {
        Tag tag = new Tag();
        tag.setName(tagDtoRequest.getName());
        return tag;
    }

    public static TagDtoRequest generateProperTagDtoRequestWithNullGiftCertificates() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequest();
        tagDtoRequest.setGiftCertificates(null);
        return tagDtoRequest;
    }

    public static TagDtoRequest generateProperTagDtoRequestWithEmptyGiftCertificates() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequest();
        tagDtoRequest.setGiftCertificates(Collections.emptySet());
        return tagDtoRequest;
    }

    public static TagDtoRequest generateProperTagDtoRequestWithGiftCertificates() {
        TagDtoRequest tagDtoRequest = generateProperTagDtoRequest();
        tagDtoRequest.setGiftCertificates(new LinkedHashSet<>(Collections.singletonList(generateProperDtoRequest())));
        return tagDtoRequest;
    }

    public static Tag generateProperTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Test Tag");
        return tag;
    }

    public static Tag generateProperTagWithNullGiftCertificates() {
        Tag tag = generateProperTag();
        tag.setGiftCertificates(null);
        return tag;
    }

    public static Tag generateProperTagWithEmptyGiftCertificates() {
        Tag tag = generateProperTag();
        tag.setGiftCertificates(Collections.emptySet());
        return tag;
    }

    public static Tag generateProperTagWithGiftCertificates() {
        Tag tag = generateProperTag();
        tag.setGiftCertificates(new LinkedHashSet<>(Collections.singletonList(generateProperGiftCertificate())));
        return tag;
    }

    public static GiftCertificateDtoResponse generateProperGiftCertificateResponse(GiftCertificate giftCertificate) {
        GiftCertificateDtoResponse giftCertificateDtoResponse = new GiftCertificateDtoResponse(giftCertificate);
        if (giftCertificate.getTags() != null && !giftCertificate.getTags().isEmpty()) {
            Set<TagDtoResponse> tags = giftCertificate.getTags().stream().map(TagDtoResponse::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            giftCertificateDtoResponse.setTags(tags);
        }
        return giftCertificateDtoResponse;
    }

    public static TagDtoResponse generateProperTagResponse(Tag tag) {
        TagDtoResponse tagDtoResponse = new TagDtoResponse(tag);
        if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
            Set<GiftCertificateDtoResponse> rs = tag.getGiftCertificates()
                                                    .stream()
                                                    .map(GiftCertificateDtoResponse::new)
                                                    .collect(Collectors.toCollection(LinkedHashSet::new));
            tagDtoResponse.setGiftCertificates(rs);
        }
        return tagDtoResponse;
    }

    public static GiftCertificateDtoRequest generateNameCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateNameCorruptedEmptyDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateNameCorruptedBlankDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName(" ");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateDescriptionCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateDescriptionCorruptedEmptyDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateDescriptionCorruptedBlankDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription(" ");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generatePriceCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generatePriceCorruptedLessThanZeroDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("-10.00"));
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateDurationCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateDurationCorruptedLessThanZeroDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(0);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateCreateDateCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateCreateDateCorruptedEmptyDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate("");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateCreateDateCorruptedBlankDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(" ");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateCreateDateCorruptedNotIsoFormatDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate("corrupted date");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateUpdateDateCorruptedNullDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        dtoReq.setUpdate(null);
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateUpdateDateCorruptedEmptyDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        dtoReq.setUpdate("");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateUpdateDateCorruptedBlankDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        dtoReq.setUpdate(" ");
        return dtoReq;
    }

    public static GiftCertificateDtoRequest generateUpdateDateCorruptedNotIsoFormatDtoRequest() {
        GiftCertificateDtoRequest dtoReq = new GiftCertificateDtoRequest();
        dtoReq.setName("Test Request");
        dtoReq.setDescription("Test Description");
        dtoReq.setPrice(new BigDecimal("100.00"));
        dtoReq.setDuration(1);
        dtoReq.setCreate(IsoDateFormatter.dateToString(new Date(System.currentTimeMillis())));
        dtoReq.setUpdate("corrupted date");
        return dtoReq;
    }

    public static GiftCertificate giftCertificateFromRequest(GiftCertificateDtoRequest dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setCreate(new Date(System.currentTimeMillis()));
        giftCertificate.setUpdate(new Date(System.currentTimeMillis()));
        return giftCertificate;
    }

    public static SearchParamRequest generateSearchParamRequest() {
        return new SearchParamRequest();
    }

    public static SearchParamResponse<GiftCertificate> generateSearchParamResponse() {
        return new SearchParamResponse<>();
    }
}