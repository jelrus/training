package com.epam.esm.model.service.interfaces.base;

import com.epam.esm.persistence.entity.BaseEntity;

/**
 * CrudService is the interface that delegates contracts (create, read, delete) from ancestor and update contracts
 * to implementor
 *
 * @param <E> describes child class of BaseEntity
 * @param <T> describes id class holder
 */
public interface CrudService<E extends BaseEntity, T> extends CrdService<E, T> {

    /**
     * Updates entity with candidate for update and returns it
     *
     * @param e requested object, candidate for update
     * @return {@code E} updated object
     */
    E update(E e);
}