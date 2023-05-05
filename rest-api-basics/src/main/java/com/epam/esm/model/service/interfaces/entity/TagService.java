package com.epam.esm.model.service.interfaces.entity;

import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.model.service.interfaces.base.CrdService;

import java.util.Set;

/**
 * TagService is the interface that delegates CRD contracts from ancestor and specific operations for
 * tag logic contracts to implementor
 */
public interface TagService extends CrdService<Tag, Long> {

    /**
     * Finds all certificated tags
     *
     * @return {@code Set<Tag>} set of tags
     */
    Set<Tag> findAllCertificated();
}