package com.epam.esm.model.service.interfaces.base;

import com.epam.esm.persistence.entity.BaseEntity;

import java.util.Set;

/**
 * CrdService is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <E> describes child class of BaseEntity
 * @param <T> describes id class holder
 */
public interface CrdService<E extends BaseEntity, T> {

    /**
     * Creates entity and returns it
     *
     * @param e candidate for creation
     * @return {@code E} created object
     */
    E create(E e);

    /**
     * Updates entity and returns it
     *
     * @param id requested parameter
     * @return {@code E} updated object
     */
    E findById(T id);

    /**
     * Deletes entity and returns it
     *
     * @param id requested parameter
     * @return {@code E} deleted object
     */
    E delete(T id);

    /**
     * Find all entities and return them
     *
     * @return {@code Set<E>} set of objects
     */
    Set<E> findAll();
}