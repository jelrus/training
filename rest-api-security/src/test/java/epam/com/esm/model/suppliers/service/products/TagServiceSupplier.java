package epam.com.esm.model.suppliers.service.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TagServiceSupplier {

    public static Tag getProperTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag" + 1L);
        tag.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
        return tag;
    }

    public static Tag getProperTagAnotherGiftCertificates() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag" + 1L);
        tag.setGiftCertificates(getGiftCertificates(4L, 5L, 6L));
        return tag;
    }

    public static List<GiftCertificate> getGiftCertificates(Long ... ids) {
        List<GiftCertificate> gCerts = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificate gc = new GiftCertificate();
            gc.setId(id);
            gc.setName("giftCertificate_" + id);
            gc.setDescription("description_" + id);
            gc.setPrice(new BigDecimal("100.00"));
            gc.setDuration((int) (20 + id));
            gc.setCreate(LocalDateTime.now());
            gc.setUpdate(LocalDateTime.now());
            gCerts.add(gc);
        }

        return gCerts;
    }
}