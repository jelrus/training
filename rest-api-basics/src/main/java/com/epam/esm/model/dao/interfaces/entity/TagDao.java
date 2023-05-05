package com.epam.esm.model.dao.interfaces.entity;

import com.epam.esm.model.dao.interfaces.base.CrdDao;
import com.epam.esm.model.dao.interfaces.supplementary.Existent;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;

import java.util.Set;

/**
 * TagDao is the interface that delegates CRD contracts from ancestor and specific operations for tag logic
 * contracts to implementor
 */
public interface TagDao extends CrdDao<Tag, Long>, Existent<Long> {

    /**
     * Finds tag for requested name value
     *
     * @param name requested parameter
     * @return {@code Tag} mapped object
     */
    Tag findByName(String name);

    /**
     * Finds tags with gift certificates
     *
     * @return {@code Set<Tag>} set of mapped tags
     */
    Set<Tag> findAllCertificated();

    /**
     * Finds gift certificates by tag for requested id value
     *
     * @param tagId requested parameter
     * @return {@code Set<GiftCertificate>} set of mapped gift certificates
     */
    Set<GiftCertificate> findGiftCertificates(Long tagId);
}