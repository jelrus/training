package epam.com.esm.model.service.interfaces.base;

import epam.com.esm.persistence.entity.BaseEntity;

/**
 * CrudService is the interface that delegates contracts (create, read, delete) from ancestor and update contracts
 * to implementor
 *
 * @param <E> describes child class of BaseEntity
 * @param <T> describes id class holder
 */
public interface CrudService<E extends BaseEntity, T> extends CrdService<E, T> {

    /**
     * Contract for updating entity, should return updated entity
     *
     * @param e provided object for update
     * @return {@code E} updated object
     */
    E update(E e);
}