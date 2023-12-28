package epam.com.esm.persistence.repository.crud;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

import java.util.Optional;

/**
 * BaseCrudRepository is the interface, delegates crud contracts to implementor
 *
 * @param <E> describes entity type
 * @param <R> describes repository type
 */
public interface BaseCrudRepository<E extends BaseEntity, R extends BaseRepository<E>> {

    /**
     * Contract for creating entity, should return created entity
     *
     * @param rep provided repository
     * @param e provided entity
     * @return {@code E} created entity
     */
    E create(R rep, E e);

    /**
     * Contract for updating entity, should return updated entity
     *
     * @param rep provided repository
     * @param e provided entity
     * @return {@code E} updated entity
     */
    E update(R rep, E e);

    /**
     * Contract for deleting entity, should return deleted entity
     *
     * @param rep provided repository
     * @param id provided entity
     * @return {@code E} deleted entity
     */
    E delete(R rep, Long id);

    /**
     * Contract for finding entity, should return optional of found entity
     *
     * @param rep provided entity
     * @param id provided id
     * @return {@code Optional<E>} result of finding
     */
    Optional<E> findById(R rep, Long id);

    /**
     * Contract for finding all entities, should return response with params and collection of found entities
     *
     * @param rep provided repository
     * @param spReq provided search param request
     * @param target provided root class for filtering
     * @param joined provided joined classes for filtering
     * @return {@code SearchParamResponse<E>} object, holds filtering params and collection of found entities
     */
    SearchParamResponse<E> findAll(R rep, SearchParamRequest spReq, Class<?> target, Class<?> ... joined);
}