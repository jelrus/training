package epam.com.esm.model.service.interfaces.base;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * CrdService is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <E> describes child class of BaseEntity
 * @param <T> describes id class holder
 */
public interface CrdService<E extends BaseEntity, T> {

    /**
     * Contract for creating entity, should return created entity
     *
     * @param e provided object for creation
     * @return {@code E} created object
     */
    E create(E e);


    /**
     * Contract for finding entity, should return found entity
     *
     * @param id requested parameter value
     * @return {@code E} found object
     */
    E findById(T id);

    /**
     * Contract for deleting entity, should return deleted entity
     *
     * @param id requested parameter value
     * @return {@code E} deleted object
     */
    E delete(T id);

    /**
     * Contract for finding all entities by search param request
     *
     * @return {@code SearchParamResponse<E>} object, holds requested search params and collection of found items
     */
    SearchParamResponse<E> findAll(SearchParamRequest searchParamRequest);
}