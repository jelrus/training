package com.epam.esm.dao.interfaces.general;

import com.epam.esm.entity.base.BaseEntity;

import java.util.Set;

public interface CrdDao<E extends BaseEntity, T> {

    T create(E entity);

    E findById(T id);

    Boolean delete(T id);

    Set<E> findAll();
}