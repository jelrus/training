package epam.com.esm.model.dao.interfaces.supplementary;

/**
 * Existent is the interface that delegates contracts related with existence of entity to implementor
 *
 * @param <T> describes id class holder
 */
public interface Existent<T> {

    /**
     * Contract for entity existence check by requested id
     *
     * @param id requested parameter, hold entity id value
     * @return {@code true} if entity exists
     */
    Boolean existsById(T id);
}