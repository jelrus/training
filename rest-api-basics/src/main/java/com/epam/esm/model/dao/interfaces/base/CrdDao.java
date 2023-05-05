package com.epam.esm.model.dao.interfaces.base;

import com.epam.esm.persistence.entity.BaseEntity;

import java.util.Set;

/**
 * CrdDao is the interface that delegates create, read and delete contracts to implementor
 *
 * @param <E> describes child class for BaseEntity
 * @param <T> describes id class holder
 */
public interface CrdDao<E extends BaseEntity, T> {

    /**
     * Creates entity
     *
     * @param e requested entity for creation
     * @return {@code T} value for id of created entity
     */
    T create(E e);

    /**
     * Finds entity for requested id value
     *
     * @param id requested parameter
     * @return {@code E} mapped object
     */
    E findById(T id);

    /**
     * Deletes entity for requested id value
     *
     * @param id requested parameter
     * @return {@code true} if row was deleted
     */
    Boolean delete(T id);

    /**
     * Finds all entities
     *
     * @return {@code Set<E>} collection of mapped objects
     */
    Set<E> findAll();
}