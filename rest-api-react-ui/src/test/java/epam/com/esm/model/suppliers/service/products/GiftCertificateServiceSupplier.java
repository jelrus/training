package epam.com.esm.model.suppliers.service.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateServiceSupplier {

    public static GiftCertificate getProperGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("gift_certificate_" + 1L);
        giftCertificate.setDescription("description_" + 1L);
        giftCertificate.setPrice(new BigDecimal("100.00"));
        giftCertificate.setDuration(100);
        giftCertificate.setCreate(LocalDateTime.now());
        giftCertificate.setUpdate(LocalDateTime.now());
        giftCertificate.setTags(getTags(1L, 2L, 3L));
        return giftCertificate;
    }

    public static GiftCertificate getProperGiftCertificateAnotherTags() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("gift_certificate_" + 1L);
        giftCertificate.setDescription("description_" + 1L);
        giftCertificate.setPrice(new BigDecimal("100.00"));
        giftCertificate.setDuration(100);
        giftCertificate.setCreate(LocalDateTime.now());
        giftCertificate.setUpdate(LocalDateTime.now());
        giftCertificate.setTags(getTags(4L, 5L, 6L));
        return giftCertificate;
    }

    public static GiftCertificate getAnotherProperGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(2L);
        giftCertificate.setName("gift_certificate_updated");
        giftCertificate.setDescription("description_updated");
        giftCertificate.setPrice(new BigDecimal("100.00"));
        giftCertificate.setDuration(100);
        giftCertificate.setCreate(LocalDateTime.now());
        giftCertificate.setUpdate(LocalDateTime.now());
        giftCertificate.setTags(getTags(1L, 2L, 3L, 4L, 5L));
        return giftCertificate;
    }

    public static List<Tag> getTags(Long ... ids) {
        List<Tag> tags = new ArrayList<>();

        for (Long id: ids) {
            Tag tag = new Tag();
            tag.setId(id);
            tag.setName("name" + id);
            tags.add(tag);
        }

        return tags;
    }
}