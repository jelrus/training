package epam.com.esm.model.facade.interfaces.base;

import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.DtoRequest;
import epam.com.esm.view.dto.response.DtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * CrdFacade is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <Q> describes child class of DtoRequest
 * @param <A> describes child class of DtoResponse
 * @param <T> describes id class holder
 */
public interface CrdFacade<Q extends DtoRequest, A extends DtoResponse, T> {

    /**
     * Contract for creating entity from dto request and producing dto response
     *
     * @param q requested object, holds requested entity values
     * @return {@code A} response object
     */
    A create(Q q);

    /**
     * Contract for finding entity by requested id value and producing dto response as the result
     *
     * @param id requested parameter, holds requested entity id value
     * @return {@code A} response object
     */
    A findById(T id);

    /**
     * Contract for deleting entity by requested id value and producing dto response as the result
     *
     * @param id requested parameter, holds requested entity id value
     * @return {@code A} response object
     */
    A delete(T id);

    /**
     * Contract for producing PageDataResponse object by requested search params
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<A>} object, contains found items and response params
     */
    PageDataResponse<A> findAll(WebRequest webRequest);
}