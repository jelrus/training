package com.epam.esm.view.dto.request.impl;

import com.epam.esm.view.dto.request.DtoRequest;

import java.util.Objects;
import java.util.Set;

/**
 * TagDtoRequest is the data class, which used for data transportation and preliminary object verification
 */
public class TagDtoRequest extends DtoRequest {

    /**
     * Field to hold name value
     */
    private String name;

    /**
     * Field to hold gift certificates tied to tag
     */
    private Set<GiftCertificateDtoRequest> giftCertificates;

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
     * Gets set from giftCertificates field
     *
     * @return {@code Set<GiftCertificateDtoRequest>} giftCertificates value
     */
    public Set<GiftCertificateDtoRequest> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new set to giftCertificates value
     *
     * @param giftCertificates value for setting
     */
    public void setGiftCertificates(Set<GiftCertificateDtoRequest> giftCertificates) {
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
        TagDtoRequest that = (TagDtoRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, giftCertificates);
    }
}