package com.epam.esm.facade.interfaces.entity;

import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.facade.interfaces.general.CrudFacade;
import com.epam.esm.utils.search.dao.SearchParamResponse;
import com.epam.esm.view.dto.request.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.response.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.TagDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.Set;

public interface GiftCertificateFacade extends CrudFacade<GiftCertificateDtoRequest, GiftCertificateDtoResponse, Long> {

    Set<TagDtoResponse> findTagsByGiftCertificate(Long giftCertificateId);

    GiftCertificateDtoResponse addTag(Long giftCertificateId, Long tagId);

    GiftCertificateDtoResponse deleteTag(Long giftCertificateId, Long tagId);

    Set<GiftCertificateDtoResponse> findAllTagged();

    //TODO: additional entity to get rid of searchParamResponse and to hook params from webRequest
    SearchParamResponse<GiftCertificate> findAllParamSearch(WebRequest webRequest);
}