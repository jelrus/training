package com.epam.esm.persistence.entity.impl;

import com.epam.esm.persistence.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * GiftCertificate is the data class, serves as the model for Gift Certificate object, extends BaseEntity class
 * <p>
 * Contains:
 * - fields that describes Gift Certificate object
 * - getters to retrieve these fields
 * - setters to set new values for fields
 * - equals and hashcode to maintain proper object's comparison
 */
public class GiftCertificate extends BaseEntity {

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
    private Date create;

    /**
     * Field to hold update value
     */
    private Date update;

    /**
     * Field to hold Tags tied to Gift Certificate
     */
    private Set<Tag> tags;

    /**
     * Constructs Gift Certificate with superclass fields
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
     * Sets new value for name field
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
     * Sets new value for description field
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
     * Sets new value for price field
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
     * Sets new value for duration field
     *
     * @param duration value for setting
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets value from create field
     *
     * @return {@code Date} create value
     */
    public Date getCreate() {
        return create;
    }

    /**
     * Sets new value for create field
     *
     * @param create value for setting
     */
    public void setCreate(Date create) {
        this.create = create;
    }

    /**
     * Gets value from update field
     *
     * @return {@code Date} update value
     */
    public Date getUpdate() {
        return update;
    }

    /**
     * Sets new value for update field
     *
     * @param update value for setting
     */
    public void setUpdate(Date update) {
        this.update = update;
    }

    /**
     * Gets set of tags from tags field
     *
     * @return {@code Set<Tag>} set of tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Sets tags for Gift Certificate
     *
     * @param tags set for setting
     */
    public void setTags(Set<Tag> tags) {
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
        GiftCertificate that = (GiftCertificate) o;
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