package epam.com.esm.model.dao.interfaces.base;

import epam.com.esm.persistence.entity.BaseEntity;

/**
 * CrudDao is the interface that delegates contracts (create, read, delete) from ancestor and update contracts
 * to implementor
 *
 * @param <E> describes child class for BaseEntity
 * @param <T> describes id class holder
 */
public interface CrudDao<E extends BaseEntity, T> extends CrdDao<E, T> {

    /**
     * Contract for entity updating
     *
     * @param e requested entity for update
     * @return {@code true} if object was updated
     */
    Boolean update(E e);
}
