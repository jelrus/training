package com.epam.esm.utils.search.dao;

import com.epam.esm.entity.base.BaseEntity;

import java.util.Collections;
import java.util.Set;

public class SearchParamResponse<E extends BaseEntity> {

    private String tagName;
    private String partName;
    private String partDescription;
    private Boolean orderDate;
    private String sortDate;
    private Boolean orderName;
    private String sortName;
    private Long itemsSize;
    private Set<E> items;

    public SearchParamResponse() {
        this.items = Collections.emptySet();
        this.itemsSize = 0L;
    }

    public SearchParamResponse(SearchParamRequest spReq) {
        this.tagName = spReq.getTagName();
        this.partName = spReq.getPartName();
        this.partDescription = spReq.getPartDescription();
        this.orderDate = spReq.getOrderDate();
        this.sortDate = spReq.getSortDate();
        this.orderName = spReq.getOrderName();
        this.sortName = spReq.getSortName();
        this.items = Collections.emptySet();
        this.itemsSize = 0L;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Boolean getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Boolean orderDate) {
        this.orderDate = orderDate;
    }

    public String getSortDate() {
        return sortDate;
    }

    public void setSortDate(String sortDate) {
        this.sortDate = sortDate;
    }

    public Boolean getOrderName() {
        return orderName;
    }

    public void setOrderName(Boolean orderName) {
        this.orderName = orderName;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Long getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(Long itemsSize) {
        this.itemsSize = itemsSize;
    }

    public Set<E> getItems() {
        return items;
    }

    public void setItems(Set<E> items) {
        this.items = items;
    }
}