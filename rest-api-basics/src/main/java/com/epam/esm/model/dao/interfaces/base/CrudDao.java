package com.epam.esm.model.dao.interfaces.base;

import com.epam.esm.persistence.entity.BaseEntity;

/**
 * CrudDao is the interface that delegates contracts (create, read, delete) from ancestor and update contracts
 * to implementor
 *
 * @param <E> describes child class for BaseEntity
 * @param <T> describes id class holder
 */
public interface CrudDao<E extends BaseEntity, T> extends CrdDao<E, T>{

    /**
     * Updates entity with requested values
     *
     * @param e requested entity for update
     * @return {@code true} if row was updated
     */
    Boolean update(E e);
}