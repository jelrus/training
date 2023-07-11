package epam.com.esm.model.suppliers.service.user;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserServiceSupplier {

    public static User getProperUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user_" + user.getId());
        user.setOrders(getOrders());
        return user;
    }

    public static User getAnotherUser() {
        User user = new User();
        user.setId(2L);
        user.setUsername("user_" + user.getId());
        user.setOrders(getOrders());
        return user;
    }

    public static Order getOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
        order.setCost(order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                          .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setPurchaseDate(LocalDateTime.now());
        order.setUser(getProperUser());
        return order;
    }

    public static List<Order> getOrders(Long ... ids) {
        List<Order> orders = new ArrayList<>();

        for (Long id: ids) {
            Order order = new Order();
            order.setId(id);
            order.setCost(order.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                              .reduce(BigDecimal.ZERO, BigDecimal::add));
            order.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
            order.setPurchaseDate(LocalDateTime.now());
        }

        return orders;
    }

    public static List<GiftCertificate> getGiftCertificates(Long ... ids) {
        List<GiftCertificate> gCerts = new ArrayList<>();

        for (Long id: ids) {
            GiftCertificate gc = new GiftCertificate();
            gc.setId(id);
            gc.setName("giftCertificate" + id);
            gc.setDescription("description" + id);
            gc.setPrice(new BigDecimal("100.00"));
            gc.setDuration((int) (20 + id));
            gc.setCreate(LocalDateTime.now());
            gc.setUpdate(LocalDateTime.now());
            gCerts.add(gc);
        }

        return gCerts;
    }
}