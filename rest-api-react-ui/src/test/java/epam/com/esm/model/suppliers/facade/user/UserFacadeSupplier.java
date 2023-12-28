package epam.com.esm.model.suppliers.facade.user;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserFacadeSupplier {

    public static User getProperUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("user_" + 1L);
        u.setPassword("password");
        u.setOrders(getOrders(1L, 2L, 3L));
        return u;
    }

    public static List<Order> getOrders(Long ... ids) {
        List<Order> orders = new ArrayList<>();

        for (Long id: ids) {
            Order order = new Order();
            order.setId(id);
            order.setPurchaseDate(LocalDateTime.now());
            order.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
            order.setCost(order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                              .reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        return orders;
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

    public static UserDtoRequest getProperUserDtoRequest() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user_" + 1L);
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithEmptyUsername() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("");
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithBlankUsername() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername(" ");
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithNullUsername() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername(null);
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithUpperCaseUsername() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("USER");
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithSpecialCharactersUsername() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user!/*");
        u.setPassword("password");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithEmptyPassword() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user_" + 1L);
        u.setPassword("");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithBlankPassword() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user_" + 1L);
        u.setPassword(" ");
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithNullPassword() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user_" + 1L);
        u.setPassword(null);
        return u;
    }

    public static UserDtoRequest getUserDtoRequestWithSpacesPassword() {
        UserDtoRequest u = new UserDtoRequest();
        u.setUsername("user_" + 1L);
        u.setPassword("pass word");
        return u;
    }

    public static UserDtoResponse getProperUserDtoResponse(User user) {
        return new UserDtoResponse(user);
    }

    public static OrderDtoRequest getProperOrderDtoRequest() {
        OrderDtoRequest order = new OrderDtoRequest();
        order.setUsername("user_" + 1L);
        order.setGiftCertificates(getGiftCertificateDtoRequests(1L, 2L, 3L));
        return order;
    }

    public static List<GiftCertificateNameDtoRequest> getGiftCertificateDtoRequests(Long ... ids) {
        List<GiftCertificateNameDtoRequest> gCerts = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificateNameDtoRequest gc = new GiftCertificateNameDtoRequest();
            gc.setName("gc_" + id);
            gCerts.add(gc);
        }

        return gCerts;
    }
}