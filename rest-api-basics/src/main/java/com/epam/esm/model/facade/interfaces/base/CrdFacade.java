package com.epam.esm.model.facade.interfaces.base;

import com.epam.esm.view.dto.request.DtoRequest;
import com.epam.esm.view.dto.response.DtoResponse;

import java.util.Set;

/**
 * CrdFacade is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <Q> describes child class of DtoRequest
 * @param <A> describes child class of DtoResponse
 * @param <T> describes id class holder
 */
public interface CrdFacade<Q extends DtoRequest, A extends DtoResponse, T> {

    /**
     * Creates entity from dto request and as the result produces dto response
     *
     * @param q requested object
     * @return {@code A} result object
     */
    A create(Q q);

    /**
     * Finds entity by requested id value and produces dto response as the result
     *
     * @param id requested parameter
     * @return {@code A} result object
     */
    A findById(T id);

    /**
     * Deletes entity by requested id value and produces dto response as the result
     *
     * @param id requested parameter
     * @return {@code A} result object
     */
    A delete(T id);

    /**
     * Finds all entities and produces dto responses as the result
     *
     * @return {@code Set<A>} result objects
     */
    Set<A> findAll();
}