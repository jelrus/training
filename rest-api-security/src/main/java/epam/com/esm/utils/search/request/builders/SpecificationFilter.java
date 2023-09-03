package epam.com.esm.utils.search.request.builders;

import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.request.handlers.RequestParameterHandler;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Specification filter is the utility class, implements Specification interface, builds predicate for filtering
 *
 * @param <E> describes entity type
 */
public class SpecificationFilter<E> implements Specification<E> {

    /**
     * Holds predicates values
     */
    private final List<Predicate> predicates;

    /**
     * Holds orders values
     */
    private final List<Order> orders;

    /**
     * Holds search param request
     */
    private final SearchParamRequest searchParamRequest;

    /**
     * Holds request parameter handler
     */
    private final RequestParameterHandler requestParameterHandler;

    /**
     * Holds joined classes
     */
    private final Class<?>[] joined;

    /**
     * Constructs specification filter with search param request, request parameter handler and joined classes
     *
     * @param searchParamRequest provided search param request
     * @param target             provided target class
     * @param joined             provided classes for joins
     */
    public SpecificationFilter(SearchParamRequest searchParamRequest,
                               Class<?> target,
                               Class<?> ... joined) {
        this.predicates = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.searchParamRequest = searchParamRequest;
        this.requestParameterHandler = new RequestParameterHandler(target);
        this.joined = joined;
    }

    /**
     * Generates predicate for filtering
     *
     * @param root must not be {@literal null}.
     * @param query must not be {@literal null}.
     * @param criteriaBuilder must not be {@literal null}.
     * @return {@code Predicate} generated final predicate
     */
    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        addPredicates(root, criteriaBuilder);
        addOrders(root, criteriaBuilder);

        Predicate p = null;

        if (!predicates.isEmpty()) {
            p = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }

        query.distinct(true).orderBy(orders);
        return p;
    }

    /**
     * Adds orders for root and joined classes
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void addOrders(Root<E> root, CriteriaBuilder criteriaBuilder) {
        addRootOrders(root, criteriaBuilder);
        addJoinsOrders(root, criteriaBuilder);
    }

    /**
     * Adds predicates for root and joined classes
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void addPredicates(Root<E> root, CriteriaBuilder criteriaBuilder) {
        rootFullParams(root, criteriaBuilder);
        rootPartParams(root, criteriaBuilder);
        joinFullParams(root, criteriaBuilder);
        joinPartParams(root, criteriaBuilder);
    }

    /**
     * Adds root full params predicates
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void rootFullParams(Root<E> root, CriteriaBuilder criteriaBuilder) {
        predicates.add(
                requestParameterHandler.fetchRootFullParams(
                        searchParamRequest, root, criteriaBuilder, Joining.AND)
        );
    }

    /**
     * Adds root part params predicates
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void rootPartParams(Root<E> root, CriteriaBuilder criteriaBuilder) {
        predicates.add(requestParameterHandler.fetchRootPartParams(
                searchParamRequest, root, criteriaBuilder, Joining.AND)
        );
    }

    /**
     * Adds join full params predicates
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void joinFullParams(Root<E> root, CriteriaBuilder criteriaBuilder) {
        Arrays.stream(joined).forEach(
                c -> predicates.add(
                        requestParameterHandler.fetchJoinFullParams(
                                searchParamRequest, root, criteriaBuilder, c, Joining.AND)
                )
        );
    }

    /**
     * Adds join part params predicates
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void joinPartParams(Root<E> root, CriteriaBuilder criteriaBuilder) {
        Arrays.stream(joined).forEach(
                c -> predicates.add(
                        requestParameterHandler.fetchJoinPartParams(
                                searchParamRequest, root, criteriaBuilder, c, Joining.AND)
                )
        );
    }

    /**
     * Adds root orders
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void addRootOrders(Root<E> root, CriteriaBuilder criteriaBuilder) {
        orders.addAll(requestParameterHandler.fetchRootOrderBy(searchParamRequest, root, criteriaBuilder));
    }

    /**
     * Adds join orders
     *
     * @param root provided root
     * @param criteriaBuilder provided criteria builder
     */
    private void addJoinsOrders(Root<E> root, CriteriaBuilder criteriaBuilder) {
        Arrays.stream(joined).forEach(
                c -> orders.addAll(
                        requestParameterHandler.fetchJoinOrderBy(
                                searchParamRequest, root, criteriaBuilder, c)
                )
        );
    }
}