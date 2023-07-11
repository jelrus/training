package epam.com.esm.model.dao.interfaces.base;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * CrdDao is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <E> describes child class for BaseEntity
 * @param <T> describes id class holder
 */
public interface CrdDao<E extends BaseEntity, T> {

    /**
     * Contract for entity creation
     *
     * @param e requested entity for creation
     * @return {@code T} id value of created entity
     */
    T create(E e);

    /**
     * Contract for entity search by requested id value
     *
     * @param id requested parameter, holds object's id
     * @return {@code E} found object
     */
    E findById(T id);

    /**
     * Contract for entity deleting by requested id value
     *
     * @param id requested parameter, holds object's id
     * @return {@code true} if object was deleted
     */
    Boolean delete(T id);

    /**
     * Contract for finding all entities by search param request
     *
     * @return {@code SearchParamResponse<E>} object, that holds search response params and collection of found items
     */
    SearchParamResponse<E> findAll(SearchParamRequest searchParamRequest);
}