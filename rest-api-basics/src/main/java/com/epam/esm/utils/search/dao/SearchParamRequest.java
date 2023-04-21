package com.epam.esm.utils.search.dao;

public class SearchParamRequest {

    private String tagName;
    private String partName;
    private String partDescription;
    private Boolean orderDate;
    private String sortDate;
    private Boolean orderName;
    private String sortName;

    public SearchParamRequest() {}

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

    @Override
    public String toString() {
        return "SearchParamRequest{" +
                "tagName='" + tagName + '\'' +
                ", partName='" + partName + '\'' +
                ", partDescription='" + partDescription + '\'' +
                ", orderDate=" + orderDate +
                ", sortDate='" + sortDate + '\'' +
                ", orderName=" + orderName +
                ", sortName='" + sortName + '\'' +
                '}';
    }
}