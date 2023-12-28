package epam.com.esm.persistence.repository.crud.impl;

import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.utils.search.request.builders.SpecificationFilter;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * BaseCrudRepositoryImpl is the service class, provides implementation of BaseCrudRepository interface contracts
 *
 * @param <E> describes entity type
 * @param <R> describes repository type
 */
@Service
public class BaseCrudRepositoryImpl<E extends BaseEntity, R extends BaseRepository<E>>
        implements BaseCrudRepository<E, R> {

    /**
     * Creates entity
     *
     * @param rep provided repository
     * @param e provided entity
     * @return {@code E} created entity
     */
    @Override
    public E create(R rep, E e) {
        return rep.save(e);
    }

    /**
     * Updates entity
     *
     * @param rep provided repository
     * @param e provided entity
     * @return {@code E} updated entity
     */
    @Override
    public E update(R rep, E e) {
        return rep.save(e);
    }

    /**
     * Deletes entity
     *
     * @param rep provided repository
     * @param id provided entity
     * @return {@code E} deleted entity
     */
    @Override
    public E delete(R rep, Long id) {
        E e = rep.findById(id).orElseThrow(
                () -> new NotFoundException("Entity with (id = " + id + ") not found")
        );
        rep.deleteById(id);
        return e;
    }

    /**
     * Finds entity
     *
     * @param rep provided entity
     * @param id provided id
     * @return {@code Optional<E>} optional of found entity
     */
    @Override
    public Optional<E> findById(R rep, Long id) {
        return rep.findById(id);
    }

    /**
     * Finds all entities by provided params
     *
     * @param rep provided repository
     * @param spReq provided search param request
     * @param target provided root class for filtering
     * @param joined provided joined classes for filtering
     * @return {@code SearchParamResponse<E>} response, holds filtering params and collection of found entities
     */
    @Override
    public SearchParamResponse<E> findAll(R rep, SearchParamRequest spReq, Class<?> target, Class<?> ... joined) {
        SpecificationFilter<E> fs = new SpecificationFilter<>(spReq, target, joined);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());
        return initResponse(spReq, rep.findAll(fs).size(), rep.findAll(fs, pageRequest).getContent());
    }
}