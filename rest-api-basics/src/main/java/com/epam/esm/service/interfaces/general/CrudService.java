package com.epam.esm.service.interfaces.general;

import com.epam.esm.entity.base.BaseEntity;

public interface CrudService<E extends BaseEntity, T> extends CrdService<E, T> {

    Boolean update(E entity);
}