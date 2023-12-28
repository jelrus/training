package epam.com.esm.persistence.entity.impl.products;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.search.filter.annotations.FilterObject;
import epam.com.esm.utils.search.filter.annotations.FilterParameter;
import epam.com.esm.utils.search.filter.annotations.FilterPrefix;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Tag is the data class, serves as the model for Tag object
 */
@Entity
@Audited
@Table(name = "tag")
@FilterPrefix(prefix = "t.")
public class Tag extends BaseEntity {

    /**
     * Holds name value
     */
    @Column(name = "name")
    @FilterParameter(fieldName = "name", alias = {"t.name", "tName", "tagName"})
    private String name;

    /**
     * Holds giftCertificates values
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    @FilterObject(cls = GiftCertificate.class, path = "giftCertificates")
    private List<GiftCertificate> giftCertificates;

    /**
     * Constructs Tag with superclass fields
     */
    public Tag() {
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
     * Gets values from giftCertificates field
     *
     * @return {@code List<GiftCertificate>} giftCertificates value
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
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name)
               && Objects.equals(giftCertificates, tag.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, giftCertificates);
    }
}