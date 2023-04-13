package com.epam.esm.entity.impl;

import com.epam.esm.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

//TODO: ISO8601 for dates in Java Class or in DB?
public class GiftCertificate extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Date createDate;
    private Date lastUpdateDate;
    private Set<Tag> tags;

    public GiftCertificate() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(description, that.description) &&
               Objects.equals(price, that.price) &&
               Objects.equals(duration, that.duration) &&
               Objects.equals(createDate, that.createDate) &&
               Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
               Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, createDate, lastUpdateDate, tags);
    }
}