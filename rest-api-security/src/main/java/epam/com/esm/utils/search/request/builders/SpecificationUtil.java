package epam.com.esm.utils.search.request.builders;

import epam.com.esm.persistence.entity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 * SpecificationUtil is the utility class, contains common specification methods
 *
 * @param <E> describes entity type
 */
public class SpecificationUtil<E extends BaseEntity> {

    /**
     * Generates specification for not null search
     *
     * @param join provided join name
     * @param param provided column name
     * @param cls provided join class
     * @return {@code Specification<E>} generated specification
     */
    public Specification<E> notNullByParam(String join, String param, Class<?> cls) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Expression<?> id = root.join(join, JoinType.LEFT).get(param).as(cls);
            return criteriaBuilder.isNotNull(id);
        };
    }

    /**
     * Generates specification for null search
     *
     * @param join provided join name
     * @param param provided column name
     * @param cls provided join class
     * @return {@code Specification<E>} generated specification
     */
    public Specification<E> nullByParam(String join, String param, Class<?> cls) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Expression<?> id = root.join(join, JoinType.LEFT).get(param).as(cls);
            return criteriaBuilder.isNull(id);
        };
    }

    /**
     * Generates specification for join id search
     *
     * @param id provided root id
     * @param join provided join name
     * @param idName provided id column name
     * @return {@code Specification<E>} generated specification
     */
    public Specification<E> idJoinEquals(Long id, String join, String idName) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Expression<?> candidateId = root.join(join).get(idName).as(Long.class);
            return criteriaBuilder.and(criteriaBuilder.equal(candidateId, id));
        };
    }

    /**
     * Generates specification for id search
     *
     * @param id provided root id
     * @param join provided join name
     * @param idName provided id column name
     * @return {@code Specification<E>} generated specification
     */
    public Specification<E> idGetEquals(Long id, String join, String idName) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Expression<?> candidateId = root.get(join).get(idName).as(Long.class);
            return criteriaBuilder.and(criteriaBuilder.equal(candidateId, id));
        };
    }

    /**
     * Generates specification for multi join id search
     *
     * @param id provided root id
     * @param idName provided root id column name
     * @param joins provided joins
     * @return {@code Specification<E>} generated specification
     */
    public Specification<E> idEqualsJoins(Long id, String idName, String ... joins) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Expression<Long> candidateId = getJoin(root, joins).get(idName).as(Long.class);
            return criteriaBuilder.and(criteriaBuilder.equal(candidateId, id));
        };
    }

    /**
     * Generates join
     *
     * @param root provided root
     * @param joins provided joins
     * @return {@code Join<?,?>} generated join
     */
    private Join<?,?> getJoin(Root<?> root, String ... joins) {
        Join<?,?> join = null;

        for (int i = 0; i < joins.length; i++) {
            if (i == 0) {
                join = root.join(joins[i]);
            } else {
                join = join.join(joins[i]);
            }
        }

        return join;
    }
}