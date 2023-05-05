package com.epam.esm.view.dto.response.impl;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.utils.date.IsoDateFormatter;
import com.epam.esm.view.dto.response.DtoResponse;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * GiftCertificateDtoResponse is the data class, which used for data view as the result of request
 */
public class GiftCertificateDtoResponse extends DtoResponse {

    /**
     * Field to hold name value
     */
    private String name;

    /**
     * Field to hold description value
     */
    private String description;

    /**
     * Field to hold price value
     */
    private BigDecimal price;

    /**
     * Field to hold duration value
     */
    private Integer duration;

    /**
     * Field to hold create value
     */
    private String create;

    /**
     * Field to hold update value
     */
    private String update;

    /**
     * Field to hold tags tied to gift certificate
     */
    private Set<TagDtoResponse> tags;

    /**
     * Constructs GiftCertificateDtoResponse object from GiftCertificate entity by copying its fields
     *
     * @param gCert requested object
     */
    public GiftCertificateDtoResponse(GiftCertificate gCert) {
        super(gCert.getId());
        setName(gCert.getName());
        setDescription(gCert.getDescription());
        setPrice(gCert.getPrice());
        setDuration(gCert.getDuration());
        setCreate(IsoDateFormatter.dateToString(gCert.getCreate()));
        setUpdate(IsoDateFormatter.dateToString(gCert.getUpdate()));
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
     * @return {@code String} create value
     */
    public String getCreate() {
        return create;
    }

    /**
     * Sets new value to create field
     *
     * @param create value for setting
     */
    public void setCreate(String create) {
        this.create = create;
    }

    /**
     * Gets value from update field
     *
     * @return {@code String} update value
     */
    public String getUpdate() {
        return update;
    }

    /**
     * Sets new value to update field
     *
     * @param update value for setting
     */
    public void setUpdate(String update) {
        this.update = update;
    }

    /**
     * Gets set from tags field
     *
     * @return {@code Set<TagDtoResponse>} tags value
     */
    public Set<TagDtoResponse> getTags() {
        return tags;
    }

    /**
     * Sets new set to tags field
     *
     * @param tags value for setting
     */
    public void setTags(Set<TagDtoResponse> tags) {
        this.tags = tags;
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
        GiftCertificateDtoResponse that = (GiftCertificateDtoResponse) o;
        return Objects.equals(name, that.name)
               && Objects.equals(description, that.description)
               && Objects.equals(price, that.price)
               && Objects.equals(duration, that.duration)
               && Objects.equals(create, that.create)
               && Objects.equals(update, that.update)
               && Objects.equals(tags, that.tags);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, create, update, tags);
    }
}