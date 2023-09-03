package epam.com.esm.model.suppliers.facade.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.converters.dto.products.GiftCertificateDtoConverter;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFacadeSupplier {

    public static OrderDtoRequest getProperOrderDtoRequest() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername("user");
        oReq.setGiftCertificates(getProperGiftCertificateDtoRequest(3));
        return oReq;
    }

    public static OrderDtoRequest getOrderDtoRequestWithEmptyGiftCertificates() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername("user");
        oReq.setGiftCertificates(Collections.emptyList());
        return oReq;
    }

    public static OrderDtoRequest getOrderDtoRequestWithNullGiftCertificates() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername("user");
        oReq.setGiftCertificates(null);
        return oReq;
    }

    public static OrderDtoRequest getOrderDtoRequestWithUsernameBlank() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername(" ");
        oReq.setGiftCertificates(getProperGiftCertificateDtoRequest(3));
        return oReq;
    }

    public static OrderDtoRequest getOrderDtoRequestWithUsernameEmpty() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername("");
        oReq.setGiftCertificates(getProperGiftCertificateDtoRequest(3));
        return oReq;
    }

    public static OrderDtoRequest getOrderDtoRequestWithUsernameNull() {
        OrderDtoRequest oReq = new OrderDtoRequest();
        oReq.setUsername(null);
        oReq.setGiftCertificates(getProperGiftCertificateDtoRequest(3));
        return oReq;
    }

    public static List<GiftCertificateNameDtoRequest> getProperGiftCertificateDtoRequest(int q) {
        List<GiftCertificateNameDtoRequest> gcReqs = new ArrayList<>();

        for (int i = 0; i<q; i++) {
            GiftCertificateNameDtoRequest gcNameDtoReq = new GiftCertificateNameDtoRequest();
            gcNameDtoReq.setName("gift_certificate_" + (i+1));
            gcReqs.add(gcNameDtoReq);
        }

        return gcReqs;
    }

    public static OrderUserDtoResponse getProperOrderUserDtoResponse(Order o) {
        OrderUserDtoResponse ouResp = new OrderUserDtoResponse(o);
        UserDtoResponse user = getProperUserDtoResponse(o);

        ouResp.setUser(user);
        ouResp.setGiftCertificates(o.getGiftCertificates()
                                    .stream()
                                    .map(gc -> GiftCertificateDtoConverter.toDto(gc, true))
                                    .collect(Collectors.toCollection(ArrayList::new)));
        return ouResp;
    }

    public static UserDtoResponse getProperUserDtoResponse(Order o) {
        UserDtoResponse user = new UserDtoResponse(o.getUser());
        user.setOrders(Collections.emptyList());
        return user;
    }

    public static Order getProperOrder() {
        Order o = new Order();
        o.setId(1L);
        o.setGiftCertificates(getGiftCertificates(1L,2L,3L));
        o.setCost(o.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add));
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(getProperUser());
        return o;
    }

    public static User getProperUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("user");
        u.setPassword("password");
        return u;
    }

    public static List<GiftCertificate> getGiftCertificates(Long ... ids) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificate gc = new GiftCertificate();
            gc.setId(id);
            gc.setName("gift_certificate_" + id);
            gc.setDescription("description_" + id);
            gc.setPrice(new BigDecimal("100.00"));
            gc.setDuration(10);
            gc.setCreate(LocalDateTime.now());
            gc.setUpdate(LocalDateTime.now());
            gc.setTags(Collections.emptyList());
            giftCertificates.add(gc);
        }

        return giftCertificates;
    }
}