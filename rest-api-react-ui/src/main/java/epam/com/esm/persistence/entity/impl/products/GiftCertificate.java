package epam.com.esm.persistence.entity.impl.products;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.utils.converters.date.IsoDateConverter;
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
 * Order is the data class, serves as the model for GiftCertificate object
 */
@Entity
@Audited
@Table(name = "gift_certificate")
@FilterPrefix(prefix = "gc.")
public class GiftCertificate extends BaseEntity {

    /**
     * Holds name value
     */
    @Column(name = "name")
    @FilterParameter(fieldName = "name",
                     alias = {"gc.name", "gcName", "giftCertificateName"})
    private String name;

    /**
     * Holds description value
     */
    @Column(name = "description")
    @FilterParameter(fieldName = "description",
                     alias = {"gc.description", "gcDescription", "giftCertificateDescription", "description"})
    private String description;

    /**
     * Holds price value
     */
    @Column(name = "price", precision = 10, scale = 2)
    @FilterParameter(fieldName = "price",
                     alias = {"gc.price", "gcPrice", "giftCertificatePrice", "price"})
    private BigDecimal price;

    /**
     * Holds duration value
     */
    @Column(name = "duration")
    @FilterParameter(fieldName = "duration",
                     alias = {"gc.duration", "gcDuration", "giftCertificateDuration", "duration"})
    private Integer duration;

    /**
     * Holds create value
     */
    @Convert(converter = IsoDateConverter.class)
    @Column(name = "create_date")
    @FilterParameter(fieldName = "create",
                     alias = {"gc.create", "create_date", "gcCreate", "gc.create", "giftCertificateCreate", "create"})
    private LocalDateTime create;

    /**
     * Holds update value
     */
    @Convert(converter = IsoDateConverter.class)
    @Column(name = "last_update_date")
    @FilterParameter(fieldName = "update",
                     alias = {"gc.update", "last_update_date", "gcUpdate", "giftCertificateUpdate", "update"})
    private LocalDateTime update;

    /**
     * Holds tags values
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @FilterObject(cls = Tag.class, path = "tags")
    private List<Tag> tags;

    /**
     * Holds orders values
     */
    @ManyToMany(mappedBy = "giftCertificates",
                cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> orders;

    /**
     * Holds purchaseData values
     */
    @OneToMany(mappedBy = "giftCertificate", cascade = CascadeType.ALL)
    private List<PurchaseData> purchaseData;


    /**
     * Constructs GiftCertificate with superclass fields
     */
    public GiftCertificate() {
        super();
    }

    /**
     * Gets value from name field
     *
     * @return {@code String} name value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new value to name field
     *
     * @param name value for setting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value from description field
     *
     * @return {@code String} description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets new value to description field
     *
     * @param description value for setting
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets value from price field
     *
     * @return {@code BigDecimal} price value
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets new value to price field
     *
     * @param price value for setting
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets value from duration field
     *
     * @return {@code Integer} duration value
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets new value to duration field
     *
     * @param duration value for setting
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets value from create field
     *
     * @return {@code LocalDateTime} create value
     */
    public LocalDateTime getCreate() {
        return create;
    }

    /**
     * Sets new value to create field
     *
     * @param create value for setting
     */
    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    /**
     * Gets value from update field
     *
     * @return {@code LocalDateTime} update value
     */
    public LocalDateTime getUpdate() {
        return update;
    }

    /**
     * Sets new value to update field
     *
     * @param update value for setting
     */
    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    /**
     * Gets value from tags field
     *
     * @return {@code List<Tag>} tags values
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets new value to tags field
     *
     * @param tags value for setting
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Gets values from orders field
     *
     * @return {@code List<Order>} orders value
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Sets new value to orders field
     *
     * @param orders value for setting
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets value from purchaseData field
     *
     * @return {@code List<PurchaseData>} purchaseData value
     */
    public List<PurchaseData> getPurchaseData() {
        return purchaseData;
    }

    /**
     * Sets new value to purchaseData field
     *
     * @param purchaseData value for setting
     */
    public void setPurchaseData(List<PurchaseData> purchaseData) {
        this.purchaseData = purchaseData;
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
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(name, that.name)
               && Objects.equals(description, that.description)
               && Objects.equals(price, that.price)
               && Objects.equals(duration, that.duration)
               && Objects.equals(create, that.create)
               && Objects.equals(update, that.update)
               && Objects.equals(tags, that.tags)
               && Objects.equals(orders, that.orders)
               && Objects.equals(purchaseData, that.purchaseData);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), name, description, price, duration, create, update, tags, orders, purchaseData
        );
    }
}