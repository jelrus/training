package com.epam.esm.view.dto.response;

import com.epam.esm.entity.impl.GiftCertificate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GiftCertificateDtoResponse extends DtoResponse {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Date createDate;
    private Date lastUpdateDate;
    private Set<TagDtoResponse> tags;

    public GiftCertificateDtoResponse(GiftCertificate giftCertificate) {
        super(giftCertificate.getId());
        setName(giftCertificate.getName());
        setDescription(giftCertificate.getDescription());
        setPrice(giftCertificate.getPrice());
        setDuration(giftCertificate.getDuration());
        setCreateDate(giftCertificate.getCreateDate());
        setLastUpdateDate(giftCertificate.getLastUpdateDate());
        setTags(giftCertificate.getTags().stream().map(TagDtoResponse::new).collect(Collectors.toSet()));
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

    public Set<TagDtoResponse> getTags() {
        return tags;
    }

    public void setTags(Set<TagDtoResponse> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateDtoResponse that = (GiftCertificateDtoResponse) o;
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
        return Objects.hash(name, description, price, duration, createDate, lastUpdateDate, tags);
    }
}