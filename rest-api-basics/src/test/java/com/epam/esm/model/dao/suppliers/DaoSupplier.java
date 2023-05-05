package com.epam.esm.model.dao.suppliers;

import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.date.IsoDateFormatter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class DaoSupplier {

    public static GiftCertificate generateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Test Gift Certificate");
        giftCertificate.setDescription("Test Description");
        giftCertificate.setPrice(new BigDecimal("100.00"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreate(IsoDateFormatter.stringToDate("2023-05-05T07:11:45.696"));
        giftCertificate.setUpdate(IsoDateFormatter.stringToDate("2023-05-05T07:11:45.696"));
        return giftCertificate;
    }

    public static GiftCertificate generateGiftCertificateUpdate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Test Gift Certificate Update");
        giftCertificate.setDescription("Test Description Update");
        giftCertificate.setPrice(new BigDecimal("200.00"));
        giftCertificate.setDuration(2);
        giftCertificate.setCreate(IsoDateFormatter.stringToDate("2023-05-05T07:11:45.696"));
        giftCertificate.setUpdate(IsoDateFormatter.stringToDate("2023-05-05T07:11:45.696"));
        return giftCertificate;
    }

    public static Set<GiftCertificate> generateGiftCertificates(long gCertsCount, int reduction) {
        Set<GiftCertificate> certs = new LinkedHashSet<>();

        long count = 1;

        for (long i = 0; i < gCertsCount; i++) {
            GiftCertificate giftCertificate = generateGiftCertificate();
            giftCertificate.setId(i+1);

            if (i%reduction == 0) {
                Tag tag = generateTag();
                tag.setName("name" + (i+1));
                tag.setId(count++);
                giftCertificate.setTags(new LinkedHashSet<>(Collections.singletonList(tag)));
            }

            certs.add(giftCertificate);
        }

        return certs;
    }

    public static Set<Tag> generateTagsForCertificates(long tagsCount) {
        Set<Tag> tags = new LinkedHashSet<>();

        for (long i = 0; i < tagsCount; i++) {
            Tag tag = new Tag();
            tag.setId(i+1);
            tag.setName("name"+ i+1);
            tags.add(tag);
        }

        return tags;
    }

    public static Tag generateTag() {
        return new Tag();
    }

    public static Set<Tag> generateTags(long gCertsCount, int reduction) {
        Set<Tag> tags = new LinkedHashSet<>();

        long count = 1;

        for (long i = 0; i < gCertsCount; i++) {
            Tag tag = generateTag();
            tag.setName("test name");
            tag.setId(i+1);

            if (i%reduction == 0) {
                GiftCertificate giftCertificate = generateGiftCertificate();
                giftCertificate.setName("name" + (i+1));
                giftCertificate.setId(count++);
                tag.setGiftCertificates(new LinkedHashSet<>(Collections.singletonList(giftCertificate)));
            }

            tags.add(tag);
        }

        return tags;
    }

    public static Set<GiftCertificate> generateGiftCertificatesForTags(long gCertsCount) {
        Set<GiftCertificate> giftCertificates = new LinkedHashSet<>();

        for (long i = 0; i < gCertsCount; i++) {
            GiftCertificate giftCertificate = generateGiftCertificate();
            giftCertificate.setId(i+1);
            giftCertificate.setName("name"+(i+1));
            giftCertificates.add(giftCertificate);
        }

        return giftCertificates;
    }

    public static void addGiftCertsWithTagsToDb(Set<GiftCertificate> giftCertificates,
                                                GiftCertificateDao giftCertificateDao,
                                                TagDao tagDao) {
        for (GiftCertificate gCert: giftCertificates) {
            giftCertificateDao.create(gCert);

            if (gCert.getTags() != null && !gCert.getTags().isEmpty()) {
                gCert.getTags().forEach(t -> {
                    tagDao.create(t);
                    giftCertificateDao.addTag(gCert.getId(), t.getId());
                });
            } else {
                gCert.setTags(Collections.emptySet());
            }
        }
    }

    public static void addTagsWithGiftCertsToDb(Set<Tag> tags,
                                                GiftCertificateDao giftCertificateDao,
                                                TagDao tagDao) {
        for (Tag tag: tags) {
            tagDao.create(tag);

            if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
                tag.getGiftCertificates().forEach(gc -> giftCertificateDao.addTag(giftCertificateDao.create(gc),
                                                                                  gc.getId()));
            } else {
                tag.setGiftCertificates(Collections.emptySet());
            }
        }
    }
}