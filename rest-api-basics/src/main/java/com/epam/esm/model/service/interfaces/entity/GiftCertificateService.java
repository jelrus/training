package com.epam.esm.model.service.interfaces.entity;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.model.service.interfaces.base.CrudService;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;

import java.util.Set;

/**
 * GiftCertificateService is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateService extends CrudService<GiftCertificate, Long> {

    /**
     * Finds all tagged certificates and returns them
     *
     * @return {@code Set<GiftCertificate>} set of gift certificates
     */
    Set<GiftCertificate> findAllTagged();

    /**
     * Finds all gift certificates by search param request and set them and params to search param response
     *
     * @param spr object, which holds search params values
     * @return {@code SearchParamResponse<GiftCertificate>} object, which hold result of search and search param values
     */
    SearchParamResponse<GiftCertificate> findAllSearch(SearchParamRequest spr);

    /**
     * Adds tag to gift certificate
     *
     * @param gCertId requested param, holder for gift certificate id value
     * @param tagId requested param, holder for tag id value
     * @return {@code GiftCertificate} updated object
     */
    GiftCertificate addTag(Long gCertId, Long tagId);

    /**
     * Deletes tag from gift certificate
     *
     * @param gCertId requested param, holder for gift certificate id value
     * @param tagId requested param, holder for tag id value
     * @return {@code GiftCertificate} updated object
     */
    GiftCertificate deleteTag(Long gCertId, Long tagId);
}