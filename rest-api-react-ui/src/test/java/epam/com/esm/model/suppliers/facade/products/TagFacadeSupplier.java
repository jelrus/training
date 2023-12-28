package epam.com.esm.model.suppliers.facade.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagFacadeSupplier {

    public static Tag getProperTag() {
        Tag t = new Tag();
        t.setId(1L);
        t.setName("t_" + 1L);
        t.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
        return t;
    }

    public static List<GiftCertificate> getGiftCertificates(Long ... ids) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificate gc = new GiftCertificate();
            gc.setId(id);
            gc.setName("gc_" + id);
            gc.setDescription("d_" + id);
            gc.setPrice(new BigDecimal("100.00"));
            gc.setDuration(1);
            gc.setCreate(LocalDateTime.now());
            gc.setUpdate(LocalDateTime.now());
            giftCertificates.add(gc);
        }

        return giftCertificates;
    }

    public static TagDtoRequest getProperTagDtoRequest() {
        TagDtoRequest tReq = new TagDtoRequest();
        tReq.setName("gc_" + 1L);
        tReq.setGiftCertificates(getGiftCertificateDtoRequests(1L, 2L, 3L));
        return tReq;
    }

    public static List<GiftCertificateNameDtoRequest> getGiftCertificateDtoRequests(Long ... ids) {
        List<GiftCertificateNameDtoRequest> giftCertificates = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificateNameDtoRequest gc = new GiftCertificateNameDtoRequest();
            gc.setName("gc_" + id);
            giftCertificates.add(gc);
        }

        return giftCertificates;
    }
    public static TagDtoResponse getProperTagDtoResponse(Tag t) {
        TagDtoResponse tResp = new TagDtoResponse(t);
        tResp.setGiftCertificates(t.getGiftCertificates().stream().map(GiftCertificateDtoResponse::new)
                                                         .collect(Collectors.toCollection(ArrayList::new)));
        return tResp;
    }

    public static TagGiftCertificatesDtoRequest getProperGiftCertificatesDtoRequest() {
        TagGiftCertificatesDtoRequest gcReq = new TagGiftCertificatesDtoRequest();
        gcReq.setGiftCertificates(getGiftCertificateDtoRequests(1L, 2L, 3L));
        return gcReq;
    }
}