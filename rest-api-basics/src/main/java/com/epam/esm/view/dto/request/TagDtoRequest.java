package com.epam.esm.view.dto.request;

import java.util.Set;

public class TagDtoRequest extends DtoRequest {

    private String name;
    private Set<GiftCertificateDtoRequest> giftCertificates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GiftCertificateDtoRequest> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(Set<GiftCertificateDtoRequest> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public String toString() {
        return "TagDtoRequest{" +
                "name='" + name + '\'' +
                ", giftCertificates=" + giftCertificates +
                '}';
    }
}