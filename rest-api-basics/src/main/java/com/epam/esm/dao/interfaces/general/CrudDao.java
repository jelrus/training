package com.epam.esm.dao.interfaces.general;

import com.epam.esm.entity.base.BaseEntity;

public interface CrudDao<E extends BaseEntity, T> extends CrdDao<E, T> {

    Boolean update(E entity);
}