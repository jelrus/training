package epam.com.esm.model.suppliers.service.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderServiceSupplier {

    public static Order getProperOrder() {
        Order o = new Order();
        o.setId(1L);
        o.setGiftCertificates(getGiftCertificates(1L, 2L, 3L));
        o.setCost(o.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add));
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(getUser());
        return o;
    }

    public static Order getOrderNullUser() {
        Order o = new Order();
        o.setId(1L);
        o.setGiftCertificates(Collections.emptyList());
        o.setCost(o.getGiftCertificates().stream().map(GiftCertificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(null);
        return o;
    }

    public static Order getEmptyGiftCertificatesOrder() {
        Order o = new Order();
        o.setId(1L);
        o.setGiftCertificates(Collections.emptyList());
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(getUser());
        return o;
    }

    public static Order getNullGiftCertificatesOrder() {
        Order o = new Order();
        o.setId(1L);
        o.setGiftCertificates(null);
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(getUser());
        return o;
    }

    public static Order getNullGiftCertificateOrder() {
        Order o = new Order();
        o.setId(1L);

        List<GiftCertificate> gCerts = new ArrayList<>();
        gCerts.add(null);

        o.setGiftCertificates(gCerts);
        o.setPurchaseDate(LocalDateTime.now());
        o.setUser(getUser());
        return o;
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

    public static User getUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("user");
        u.setPassword("password");
        u.setOrders(new ArrayList<>());
        return u;
    }
}