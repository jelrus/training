package com.epam.esm.model.dao.interfaces.entity;

import com.epam.esm.model.dao.interfaces.base.CrudDao;
import com.epam.esm.model.dao.interfaces.supplementary.Existent;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;

import java.util.Set;

/**
 * GiftCertificateDao is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate, Long>, Existent<Long> {

    /**
     * Finds gift certificate for requested name value
     *
     * @param name requested parameter
     * @return {@code GiftCertificate} mapped object
     */
    GiftCertificate findByName(String name);

    /**
     * Finds gift certificates with tags
     *
     * @return {@code Set<GiftCertificate>} collection of mapped gift certificates
     */
    Set<GiftCertificate> findAllTagged();

    /**
     * Finds all gift certificates by search param request
     *
     * @param spr object, which contains request params maps
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains request params map and
     * set of mapped gift certificates
     */
    SearchParamResponse<GiftCertificate> findAllSearch(SearchParamRequest spr);

    /**
     * Finds tags by gift certificate for requested id value
     *
     * @param gCertId requested parameter
     * @return {@code Set<Tag>} set of mapped tags
     */
    Set<Tag> findTags(Long gCertId);

    /**
     * Adds tag to gift certificate
     *
     * @param gCertId requested parameter
     * @param tagId requested parameter
     * @return {@code true} if tag was created
     */
    Boolean addTag(Long gCertId, Long tagId);

    /**
     * Deletes tag from gift certificate
     *
     * @param gCertId requested parameter
     * @param tagId requested parameter
     * @return {@code true} if tag was deleted
     */
    Boolean deleteTag(Long gCertId, Long tagId);
}