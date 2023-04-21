package com.epam.esm.facade.interfaces.entity;

import com.epam.esm.facade.interfaces.general.CrdFacade;
import com.epam.esm.view.dto.request.TagDtoRequest;
import com.epam.esm.view.dto.response.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.TagDtoResponse;

import java.util.Set;

public interface TagFacade extends CrdFacade<TagDtoRequest, TagDtoResponse, Long> {

    Set<GiftCertificateDtoResponse> findGiftCertificatesByTag(Long tagId);

    TagDtoResponse addGiftCertificate(Long tagId, Long giftCertificateId);

    TagDtoResponse deleteGiftCertificate(Long tagId, Long giftCertificateId);

    Set<TagDtoResponse> findAllCertificated();
}