package epam.com.esm.persistence.entity.impl.action;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.filter.annotations.FilterObject;
import epam.com.esm.utils.search.filter.annotations.FilterParameter;
import epam.com.esm.utils.search.filter.annotations.FilterPrefix;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Order is the data class, serves as the model for Order object
 */
@Entity
@Audited
@Table(name = "orders")
@FilterPrefix(prefix = "o.")
public class Order extends BaseEntity {

    /**
     * Holds cost value
     */
    @Column(name = "cost", precision = 10, scale = 2)
    @FilterParameter(fieldName = "cost",
                     alias = {"o.cost", "cost", "oCost", "orderCost"})
    private BigDecimal cost;

    /**
     * Holds purchaseDate value
     */
    @Column(name = "purchase_date")
    @FilterParameter(fieldName = "purchaseDate",
                     alias = {"o.purchaseDate", "purchase_date" , "purchaseDate", "oPurchaseDate", "orderPurchaseDate"})
    private LocalDateTime purchaseDate;

    /**
     * Holds giftCertificates values
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "order_gift_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    @FilterObject(cls = GiftCertificate.class, path = "giftCertificates")
    private List<GiftCertificate> giftCertificates;

    /**
     * Holds user value
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @FilterObject(cls = User.class, path = "user")
    private User user;

    /**
     * Constructs Order with superclass fields
     */
    public Order() {
        super();
    }

    /**
     * Gets value from cost field
     *
     * @return {@code BigDecimal} cost value
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets new value to cost field
     *
     * @param cost value for setting
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets value from purchaseDate field
     *
     * @return {@code LocalDateTime} purchaseDate value
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets new value to purchaseDate field
     *
     * @param purchaseDate value for setting
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets values from giftCertificates field
     *
     * @return {@code List<GiftCertificate>} giftCertificates values
     */
    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new value to giftCertificates field
     *
     * @param giftCertificates value for setting
     */
    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    /**
     * Gets values from user field
     *
     * @return {@code User} user value
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets new value to user field
     *
     * @param user value for setting
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(cost, order.cost)
               && Objects.equals(purchaseDate, order.purchaseDate)
               && Objects.equals(giftCertificates, order.giftCertificates)
               && Objects.equals(user, order.user);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cost, purchaseDate, giftCertificates, user);
    }
}