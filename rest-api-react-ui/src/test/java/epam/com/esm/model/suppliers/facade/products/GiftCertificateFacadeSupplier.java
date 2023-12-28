package epam.com.esm.model.suppliers.facade.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateFacadeSupplier {

    public static GiftCertificate getProperGiftCertificate() {
        GiftCertificate gc = new GiftCertificate();
        gc.setId(1L);
        gc.setName("gc_" + 1L);
        gc.setDescription("d_" + 1L);
        gc.setPrice(new BigDecimal("100.00"));
        gc.setDuration(1);
        gc.setCreate(LocalDateTime.now());
        gc.setUpdate(LocalDateTime.now());
        gc.setTags(getTags(1L, 2L, 3L));
        return gc;
    }

    public static List<Tag> getTags(Long ... ids) {
        List<Tag> tags = new ArrayList<>();

        for (Long id: ids) {
            Tag t = new Tag();
            t.setId(id);
            t.setName("tag_" + id);
            tags.add(t);
        }

        return tags;
    }

    public static GiftCertificateDtoRequest getProperGiftCertificateDtoRequest() {
        GiftCertificateDtoRequest gcReq = new GiftCertificateDtoRequest();
        gcReq.setName("gc_" + 1L);
        gcReq.setDescription("d_" + 1L);
        gcReq.setPrice(new BigDecimal("100.00"));
        gcReq.setDuration(1);
        gcReq.setTags(getTagsDtoRequests(1L, 2L, 3L));
        return gcReq;
    }

    public static List<TagDtoRequest> getTagsDtoRequests(Long ... ids) {
        List<TagDtoRequest> tags = new ArrayList<>();

        for (Long id: ids) {
            TagDtoRequest t = new TagDtoRequest();
            t.setName("tag_" + id);
            tags.add(t);
        }

        return tags;
    }

    public static GiftCertificateDtoResponse getProperGiftCertificateDtoResponse(GiftCertificate gc) {
        GiftCertificateDtoResponse gcResp = new GiftCertificateDtoResponse(gc);
        gcResp.setTags(gc.getTags().stream().map(TagDtoResponse::new)
                                            .collect(Collectors.toCollection(ArrayList::new)));
        return gcResp;
    }

    public static GiftCertificateTagsDtoRequest getProperTagsDtoRequest() {
        GiftCertificateTagsDtoRequest gcReq = new GiftCertificateTagsDtoRequest();
        gcReq.setTags(getTagsDtoRequests(1L, 2L, 3L));
        return gcReq;
    }
}