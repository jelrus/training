package com.epam.esm.service.interfaces.general;

import com.epam.esm.entity.base.BaseEntity;

import java.util.Set;

public interface CrdService<E extends BaseEntity, T> {

    Boolean create(E entity);

    E findById(T id);

    Boolean delete(T id);

    Set<E> findAll();
}