package com.epam.esm.view.dto.request;

import com.epam.esm.view.dto.response.TagDtoResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class GiftCertificateDtoRequest extends DtoRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Date createDate;
    private Date lastUpdateDate;
    private Set<TagDtoRequest> tags;

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

    public Set<TagDtoRequest> getTags() {
        return tags;
    }

    public void setTags(Set<TagDtoRequest> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificateDtoRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}