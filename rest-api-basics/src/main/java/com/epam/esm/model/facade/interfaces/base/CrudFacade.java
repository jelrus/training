package com.epam.esm.model.facade.interfaces.base;

import com.epam.esm.view.dto.request.DtoRequest;
import com.epam.esm.view.dto.response.DtoResponse;

/**
 * CrudFacade is the interface that delegates contracts (create, read, delete) from ancestor and update contracts
 * to implementor
 *
 * @param <Q> describes child class of DtoRequest
 * @param <A> describes child class of DtoResponse
 * @param <T> describes id class holder
 */
public interface CrudFacade<Q extends DtoRequest, A extends DtoResponse, T> extends CrdFacade<Q, A, T> {

    /**
     * Updates entity with requested id and dto request values as the result
     *
     * @param q requested object
     * @param id requested parameter
     * @return {@code A} result object
     */
    A update(Q q, T id);
}