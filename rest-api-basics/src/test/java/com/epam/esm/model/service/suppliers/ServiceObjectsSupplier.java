package com.epam.esm.model.service.suppliers;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.LinkedHashSet;

public final class ServiceObjectsSupplier {

    public static GiftCertificate generateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("Test Gift Certificate");
        giftCertificate.setDescription("Test Description");
        giftCertificate.setPrice(new BigDecimal("100.00"));
        giftCertificate.setDuration(2);
        giftCertificate.setCreate(new Date(System.currentTimeMillis()));
        giftCertificate.setUpdate(new Date(System.currentTimeMillis()));
        return giftCertificate;
    }

    public static GiftCertificate generateGiftCertificateNullTags() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        giftCertificate.setTags(null);
        return giftCertificate;
    }

    public static GiftCertificate generateGiftCertificateEmptySet() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        giftCertificate.setTags(Collections.emptySet());
        return giftCertificate;
    }

    public static GiftCertificate generateGiftCertificateWithTags() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        giftCertificate.setTags(new LinkedHashSet<>(Collections.singletonList(generateTag())));
        return giftCertificate;
    }

    public static Tag generateTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Test Tag");
        return tag;
    }

    public static SearchParamRequest generateSearchParamRequest() {
        return new SearchParamRequest();
    }

    public static SearchParamResponse<GiftCertificate> generateSearchParamResponse() {
        return new SearchParamResponse<>();
    }

    public static Tag generateTagNullGiftCertificates() {
        Tag tag = generateTag();
        tag.setGiftCertificates(null);
        return tag;
    }

    public static  Tag generateTagEmptySet() {
        Tag tag = generateTag();
        tag.setGiftCertificates(Collections.emptySet());
        return tag;
    }

    public static  Tag generateTagWithGiftCertificates() {
        Tag tag = generateTag();
        tag.setGiftCertificates(new LinkedHashSet<>(Collections.singletonList(generateGiftCertificate())));
        return tag;
    }
}