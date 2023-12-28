package epam.com.esm.model.facade.interfaces.base;

import epam.com.esm.view.dto.request.DtoRequest;
import epam.com.esm.view.dto.response.DtoResponse;

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
     * Contract for updating entity from dto request by requested id and producing dto response
     *
     * @param q requested object,  holds requested entity values
     * @return {@code A} response object
     */
    A update(Q q, T id);
}