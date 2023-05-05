package com.epam.esm.model.facade.interfaces.entity;

import com.epam.esm.model.facade.interfaces.base.CrudFacade;
import com.epam.esm.utils.search.response.DataResponse;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.Set;

/**
 * GiftCertificateFacade is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateFacade extends CrudFacade<GiftCertificateDtoRequest, GiftCertificateDtoResponse, Long> {

    /**
     * Finds gift certificates with tags and produces dto responses as the result
     *
     * @return {@code Set<GiftCertificateDtoResponse>} collection of mapped gift certificates dto responses
     */
    Set<GiftCertificateDtoResponse> findAllTagged();

    /**
     * Finds all gift certificates by search param request and produces data response object as the result
     *
     * @param webRequest requested object, which holds search params
     * @return {@code DataResponse<GiftCertificateDtoResponse>} result object
     */
    DataResponse<GiftCertificateDtoResponse> findAllSearch(WebRequest webRequest);

    /**
     * Adds tag to gift certificate
     *
     * @param gCertId requested parameter, holder for gift certificate id value
     * @param tagId requested parameter, holder for tag id value
     * @return {@code GiftCertificateDtoResponse}
     */
    GiftCertificateDtoResponse addTag(Long gCertId, Long tagId);

    /**
     * Deletes tag from gift certificate
     *
     * @param gCertId requested parameter, holder for gift certificate id value
     * @param tagId requested parameter, holder for tag id value
     * @return {@code GiftCertificateDtoResponse}
     */
    GiftCertificateDtoResponse deleteTag(Long gCertId, Long tagId);
}