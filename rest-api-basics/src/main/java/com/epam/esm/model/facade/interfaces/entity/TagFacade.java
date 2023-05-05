package com.epam.esm.model.facade.interfaces.entity;

import com.epam.esm.model.facade.interfaces.base.CrdFacade;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;

import java.util.Set;

/**
 * TagFacade is the interface that delegates CRD contracts from ancestor and specific operations for
 * tag logic contracts to implementor
 */
public interface TagFacade extends CrdFacade<TagDtoRequest, TagDtoResponse, Long> {

    /**
     * Finds tags with gift certificates and produces dto responses as the result
     *
     * @return {@code Set<TagDtoResponse>} collection of mapped tags dto responses
     */
    Set<TagDtoResponse> findAllCertificated();
}