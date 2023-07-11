package epam.com.esm.utils.search.request.components;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Objects;

/**
 * Criteria config is the data class, holds objects required for building criteria query
 *
 * @param <E> describes class for criteria building
 */
public class CriteriaConfig<E> {

    /**
     * Holds criteria builder
     */
    private final CriteriaBuilder criteriaBuilder;

    /**
     * Holds criteria query
     */
    private final CriteriaQuery<E> criteriaQuery;

    /**
     * Holds root
     */
    private final Root<E> root;

    /**
     * Constructs criteria config with provided entity manager and class
     *
     * @param entityManager provided entity manager
     * @param cls provided class
     */
    public CriteriaConfig(EntityManager entityManager, Class<E> cls) {
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(cls);
        this.root = criteriaQuery.from(cls);
    }

    /**
     * Gets criteriaBuilder
     *
     * @return {@code String} criteriaBuilder value
     */
    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    /**
     * Gets criteriaQuery
     *
     * @return {@code String} criteriaQuery value
     */
    public CriteriaQuery<E> getCriteriaQuery() {
        return criteriaQuery;
    }

    /**
     * Gets root
     *
     * @return {@code String} root value
     */
    public Root<E> getRoot() {
        return root;
    }

    /**
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriteriaConfig<?> that = (CriteriaConfig<?>) o;
        return Objects.equals(criteriaBuilder, that.criteriaBuilder)
               && Objects.equals(criteriaQuery, that.criteriaQuery)
               && Objects.equals(root, that.root);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(criteriaBuilder, criteriaQuery, root);
    }
}