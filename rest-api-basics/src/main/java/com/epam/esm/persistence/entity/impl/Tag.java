package com.epam.esm.persistence.entity.impl;

import com.epam.esm.persistence.entity.BaseEntity;

import java.util.Objects;
import java.util.Set;

/**
 * Tag is the data class, serves as model for Tag object, extends BaseEntity class
 * <p>
 * - fields that describes Tag object
 * - getters to retrieve this fields
 * - setters to set new values for fields
 * - equals and hashcode to maintain proper object's comparison
 */
public class Tag extends BaseEntity {

    /**
     * Field to hold name value
     */
    private String name;

    /**
     * Field to hold Gift Certificates tied to Tag
     */
    private Set<GiftCertificate> giftCertificates;

    /**
     * Construct Tag with superclass fields
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
     * Sets new value for name field
     *
     * @param name value for setting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets set of gift certificates from giftCertificates field
     *
     * @return {@code String} set of gift certificates
     */
    public Set<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets gift certificates for Tag
     *
     * @param giftCertificates set for setting
     */
    public void setGiftCertificates(Set<GiftCertificate> giftCertificates) {
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
        return Objects.equals(name, tag.name) && Objects.equals(giftCertificates, tag.giftCertificates);
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