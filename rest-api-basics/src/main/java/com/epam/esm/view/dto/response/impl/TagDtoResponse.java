package com.epam.esm.view.dto.response.impl;

import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.view.dto.response.DtoResponse;

import java.util.Objects;
import java.util.Set;

/**
 * TagDtoResponse is the data class, which used for data view as the result of request
 */
public class TagDtoResponse extends DtoResponse {

    /**
     * Field to hold name vale
     */
    private String name;

    /**
     * Field to hold gift certificates tied to tag
     */
    private Set<GiftCertificateDtoResponse> giftCertificates;

    /**
     * Constructs TagDtoResponse object from Tag entity by copying its fields
     *
     * @param tag requested object
     */
    public TagDtoResponse(Tag tag) {
        super(tag.getId());
        setName(tag.getName());
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
     * Gets set from giftCertificates field
     *
     * @return {@code Set<GiftCertificateDtoResponse>} giftCertificates value
     */
    public Set<GiftCertificateDtoResponse> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new set to giftCertificates value
     *
     * @param giftCertificates value for setting
     */
    public void setGiftCertificates(Set<GiftCertificateDtoResponse> giftCertificates) {
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
        TagDtoResponse that = (TagDtoResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(giftCertificates, that.giftCertificates);
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