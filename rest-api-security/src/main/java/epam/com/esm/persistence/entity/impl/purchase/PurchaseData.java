package epam.com.esm.persistence.entity.impl.purchase;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.purchase.type.Status;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.filter.annotations.FilterObject;
import epam.com.esm.utils.search.filter.annotations.FilterParameter;
import epam.com.esm.utils.search.filter.annotations.FilterPrefix;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * PurchaseData is the data class, serves as the model for PurchaseData object
 */
@Entity
@Audited
@Table(name = "purchase_data")
@FilterPrefix(prefix = "pd.")
public class PurchaseData extends BaseEntity {

    /**
     * Holds start value
     */
    @Column(name = "start")
    @FilterParameter(fieldName = "start", alias = {"gc.start", "start", "giftCertificateStart", "gcStart"})
    private LocalDateTime start;

    /**
     * Holds end value
     */
    @Column(name = "end")
    @FilterParameter(fieldName = "end", alias = {"gc.end", "end", "giftCertificateEnd", "gcEnd"})
    private LocalDateTime end;

    /**
     * Holds status value
     */
    @Column(name = "status", columnDefinition = "enum('ACTIVE', 'EXPIRED')")
    @Enumerated(EnumType.STRING)
    @FilterParameter(fieldName = "status", alias = {"gc.status", "status", "giftCertificateStatus", "gcStatus"})
    private Status status;

    /**
     * Holds user value
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_purchase_data",
               joinColumns = @JoinColumn(name = "purchase_data_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    @FilterObject(cls = User.class, path = "user")
    private User user;

    /**
     * Holds giftCertificate value
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "purchase_data_gift_certificate",
               joinColumns = @JoinColumn(name = "purchase_data_id"),
               inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    @FilterObject(cls = GiftCertificate.class, path = "giftCertificate")
    private GiftCertificate giftCertificate;

    /**
     * Constructs PurchaseData with superclass fields
     */
    public PurchaseData() {
        super();
    }

    /**
     * Gets value from start field
     *
     * @return {@code LocalDateTime} start value
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets new value to start field
     *
     * @param start value for setting
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets value from end field
     *
     * @return {@code LocalDateTime} end value
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets new value to end field
     *
     * @param end value for setting
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets value from status field
     *
     * @return {@code Status} status value
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets new value to status field
     *
     * @param status value for setting
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets value from user field
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
     * Gets values from giftCertificate field
     *
     * @return {@code GiftCertificate} giftCertificate value
     */
    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Sets new value to giftCertificate field
     *
     * @param giftCertificate value for setting
     */
    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
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
        PurchaseData that = (PurchaseData) o;
        return Objects.equals(start, that.start)
               && Objects.equals(end, that.end)
               && status == that.status && Objects.equals(user, that.user)
               && Objects.equals(giftCertificate, that.giftCertificate);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end, status, user, giftCertificate);
    }
}