package com.epam.esm.entity.impl;

import com.epam.esm.entity.base.BaseEntity;

import java.util.Objects;
import java.util.Set;

public class Tag extends BaseEntity {

    private String name;
    private Set<GiftCertificate> giftCertificates;

    public Tag() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(Set<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) &&
               Objects.equals(giftCertificates, tag.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, giftCertificates);
    }

    @Override
    public String toString() {
        return "Tag{id=" + getId() + '\'' +
                "name='" + name + '\'' +
                ", giftCertificates=" + giftCertificates +
                '}';
    }
}