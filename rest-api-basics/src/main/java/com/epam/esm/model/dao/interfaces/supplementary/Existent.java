package com.epam.esm.model.dao.interfaces.supplementary;

/**
 * Existent is the interface that delegates contracts related with existence of entity to implementor
 *
 * @param <T> describes id class holder
 */
public interface Existent<T> {

    /**
     * Checks if entity exists by requested id
     *
     * @param id requested parameter
     * @return {@code true} if entity exists
     */
    Boolean existById(T id);

    /**
     * Checks if entity exists by requested name
     *
     * @param name requested parameter
     * @return {@code true} if entity exists
     */
    Boolean existByName(String name);
}