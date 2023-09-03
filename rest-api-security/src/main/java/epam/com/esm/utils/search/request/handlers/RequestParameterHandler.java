package epam.com.esm.utils.search.request.handlers;

import epam.com.esm.utils.search.filter.handlers.FilterAnnotationHandler;
import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RequestParameterHandler is the service class, builds predicates and orders from provided search params
 */
public class RequestParameterHandler {

    /**
     * Holds filter annotation handler
     */
    private final FilterAnnotationHandler fah;

    /**
     * Constructs request parameter handler with provided class
     *
     * @param cls provided class
     */
    public RequestParameterHandler(Class<?> cls) {
        this.fah = new FilterAnnotationHandler(cls);
    }

    /**
     * Generates predicate based on root full search params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @param joining provided joining
     * @return {@code Predicate} generated predicate
     */
    public Predicate fetchRootFullParams(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb, Joining joining) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, List<String>> me : spReq.getFullParams().entrySet()) {
            if (me.getKey().startsWith(fah.getPrefix())) {
                String replacement = me.getKey().replace(fah.getPrefix(), "");
                predicates.add(root.get(replacement).as(String.class).in(me.getValue()));
            }
        }

        return joining.equals(Joining.AND)
               ? cb.and(predicates.toArray(new Predicate[0]))
               : cb.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Generates predicate based on join full search params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @param joined provided joined class
     * @param joining provided joining
     * @return {@code Predicate} generated predicate
     */
    public Predicate fetchJoinFullParams(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb, Class<?> joined,
                                         Joining joining) {
        List<Predicate> predicates = new ArrayList<>();
        String prefix = fah.getClassMap().get(joined).getPrefix();
        String joinName = fah.getClassMap().get(joined).getJoinName();

        for (Map.Entry<String, List<String>> me : spReq.getFullParams().entrySet()) {
            if (me.getKey().startsWith(prefix)) {
                String replacement = me.getKey().replace(prefix, "");
                me.getValue().forEach(
                        x -> predicates.add(
                                cb.like(root.join(joinName, JoinType.LEFT).get(replacement).as(String.class), x)
                        )
                );
            }
        }

        return joining.equals(Joining.AND)
               ? cb.and(predicates.toArray(new Predicate[0]))
               : cb.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Generates predicate based on root part search params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @param joining provided joining
     * @return {@code Predicate} generated predicate
     */
    public Predicate fetchRootPartParams(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb, Joining joining) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, List<String>> me : spReq.getPartParams().entrySet()) {
            if (me.getKey().startsWith(fah.getPrefix())) {
                String replacement = me.getKey().replace(fah.getPrefix(), "");
                me.getValue().forEach(x -> predicates.add(
                        cb.like(root.get(replacement).as(String.class), "%" + x + "%"))
                );
            }
        }

        return joining.equals(Joining.AND)
               ? cb.and(predicates.toArray(new Predicate[0]))
               : cb.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Generates predicate based on join part search params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @param joined provided joined class
     * @param joining provided joining
     * @return {@code Predicate} generated predicate
     */
    public Predicate fetchJoinPartParams(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb, Class<?> joined,
                                         Joining joining) {
        List<Predicate> predicates = new ArrayList<>();
        String prefix = fah.getClassMap().get(joined).getPrefix();
        String joinName = fah.getClassMap().get(joined).getJoinName();

        for (Map.Entry<String, List<String>> me : spReq.getPartParams().entrySet()) {
            if (me.getKey().startsWith(prefix)) {
                String replacement = me.getKey().replace(prefix, "");
                me.getValue().forEach(
                        x -> predicates.add(
                                cb.like(root.join(joinName, JoinType.LEFT).get(replacement).as(String.class),
                                        "%" + x + "%")
                        )
                );
            }
        }

        return joining.equals(Joining.AND)
               ? cb.and(predicates.toArray(new Predicate[0]))
               : cb.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Generates orders list based on root sort params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @return {@code List<Order>} generated orders
     */
    public List<Order> fetchRootOrderBy(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb) {
        List<Order> orders = new ArrayList<>();

        for (Map.Entry<String, List<String>> me: spReq.getSortParams().entrySet()) {
            if (me.getKey().startsWith(fah.getPrefix())) {
                String replacement = me.getKey().replace(fah.getPrefix(), "");
                for (String s: me.getValue()) {
                    if (s.equalsIgnoreCase("asc")) {
                        orders.add(cb.asc(root.get(replacement)));
                    } else {
                        orders.add(cb.desc(root.get(replacement)));
                    }
                }
            }
        }

        return orders;
    }

    /**
     * Generates orders list based on join sort params
     *
     * @param spReq provided search param request
     * @param root provided root
     * @param cb provided criteria builder
     * @param joined provided joined class
     * @return {@code List<Order>} generated orders
     */
    public List<Order> fetchJoinOrderBy(SearchParamRequest spReq, Root<?> root, CriteriaBuilder cb, Class<?> joined) {
        List<Order> orders = new ArrayList<>();
        String prefix = fah.getClassMap().get(joined).getPrefix();

        for (Map.Entry<String, List<String>> me: spReq.getSortParams().entrySet()) {
            if (me.getKey().startsWith(prefix)) {
                String replacement = me.getKey().replace(prefix, "");
                for (String s: me.getValue()) {
                    if (s.equalsIgnoreCase("asc")) {
                       orders.add(cb.asc(
                               root.join(fah.getClassMap().get(joined).getJoinName(), JoinType.LEFT).get(replacement))
                       );
                    } else {
                       orders.add(cb.desc(
                               root.join(fah.getClassMap().get(joined).getJoinName(), JoinType.LEFT).get(replacement))
                       );
                    }
                }
            }
        }

        return orders;
    }
}