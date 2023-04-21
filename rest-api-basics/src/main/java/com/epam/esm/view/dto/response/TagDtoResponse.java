package com.epam.esm.view.dto.response;

import com.epam.esm.entity.impl.Tag;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TagDtoResponse extends DtoResponse {

    private String name;
    private Set<GiftCertificateDtoResponse> giftCertificates;

    public TagDtoResponse(Tag tag) {
        super(tag.getId());
        setName(tag.getName());
        setGiftCertificates(tag.getGiftCertificates().stream().map(GiftCertificateDtoResponse::new)
                                                              .collect(Collectors.toSet()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GiftCertificateDtoResponse> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(Set<GiftCertificateDtoResponse> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDtoResponse that = (TagDtoResponse) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(giftCertificates, that.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, giftCertificates);
    }
}