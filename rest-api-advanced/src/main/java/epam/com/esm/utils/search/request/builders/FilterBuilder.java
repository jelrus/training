package epam.com.esm.utils.search.request.builders;

import epam.com.esm.utils.search.request.components.CriteriaConfig;
import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.request.handlers.RequestParameterHandler;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FilterBuilder is the service class, builds query from provided search params
 *
 * @param <X> describes class for building criteria config
 */
public class FilterBuilder<X> {

    /**
     * Holds entity manager
     */
    private final EntityManager entityManager;

    /**
     * Holds criteria config
     */
    private final CriteriaConfig<X> config;

    /**
     * Holds joined classes
     */
    private final Class<?>[] joined;

    /**
     * Holds request parameter handler
     */
    private final RequestParameterHandler requestParameterHandler;

    /**
     * Holds search param request
     */
    private SearchParamRequest searchParamRequest;

    /**
     * Holds predicates
     */
    private final List<Predicate> predicates;

    /**
     * Holds orders
     */
    private final List<Order> orders;

    /**
     * Holds final predicate
     */
    private Predicate finalPredicate;

    /**
     * Constructs FilterBuilder with provided entity manager, root class and joined classes
     *
     * @param em provided entity manager
     * @param root provided root class
     * @param joined provided joineed classes
     */
    public FilterBuilder(EntityManager em, Class<X> root, Class<?> ... joined) {
        this.entityManager = em;
        this.joined = joined;
        this.config = new CriteriaConfig<>(em, root);
        this.requestParameterHandler = new RequestParameterHandler(root);
        this.predicates = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /**
     * Applies search param request to current filter builder
     *
     * @param sr provided search param request
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> applyRequest(SearchParamRequest sr) {
        this.searchParamRequest = sr;
        return this;
    }

    /**
     * Adds predicate to filter builder
     *
     * @param p provided predicate
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> addPredicate(Predicate p) {
        predicates.add(p);
        return this;
    }

    /**
     * Adds order to filter builder
     *
     * @param o provided order
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> addOrder(Order o) {
        orders.add(o);
        return this;
    }

    /**
     * Builds final predicate by merging added predicates with defined joining
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> buildPredicates(Joining j) {
        finalPredicate = mergePredicates(j);
        return this;
    }

    /**
     * Finalizes search by running query with defined predicate, orders and pagination data
     *
     * @param distinct provided distinction mode
     * @return {@code List<X>} found items
     */
    public List<X> runQuery(boolean distinct) {
        getQuery().select(getRoot()).distinct(distinct).where(finalPredicate).orderBy(orders);
        return getManager().createQuery(getQuery())
                           .setFirstResult(getRequest().getPage())
                           .setMaxResults(getRequest().getSize())
                           .getResultList();
    }

    /**
     * Counts total items
     *
     * @param distinct provided distinction mode
     * @return {@code int} total items size
     */
    public int count(boolean distinct) {
        getQuery().select(getRoot()).distinct(distinct).where(finalPredicate).orderBy(orders);
        return getManager().createQuery(getQuery()).getResultList().size();
    }

    /**
     * Adds search predicates to filter builder
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> addSearchPredicates(Joining j) {
        addRootFullPredicates(j);
        addRootPartPredicates(j);
        addJoinFullPredicates(j);
        addJoinPartPredicates(j);
        return this;
    }

    /**
     * Adds orders to filter builder
     *
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    public FilterBuilder<X> addOrders() {
        addRootOrders();
        addJoinsOrders();
        return this;
    }

    /**
     * Adds calculated root full predicates to filter builder
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addRootFullPredicates(Joining j) {
        predicates.add(getHandler().fetchRootFullParams(getRequest(), getRoot(), getBuilder(), j));
        return this;
    }

    /**
     * Adds calculated root part predicates to filter builder
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addRootPartPredicates(Joining j) {
        predicates.add(getHandler().fetchRootPartParams(getRequest(), getRoot(), getBuilder(), j));
        return this;
    }

    /**
     * Adds calculated join full predicates to filter builder
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addJoinFullPredicates(Joining j) {
        Arrays.stream(joined).forEach(
                c -> predicates.add(getHandler().fetchJoinFullParams(getRequest(), getRoot(), getBuilder(), c, j))
        );
        return this;
    }

    /**
     * Adds calculated join part predicates to filter builder
     *
     * @param j provided joining
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addJoinPartPredicates(Joining j) {
        Arrays.stream(joined).forEach(
                c -> predicates.add(getHandler().fetchJoinPartParams(getRequest(), getRoot(), getBuilder(), c, j))
        );
        return this;
    }

    /**
     * Adds root orders to filter builder
     *
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addRootOrders() {
        orders.addAll(getHandler().fetchRootOrderBy(getRequest(), getRoot(), getBuilder()));
        return this;
    }

    /**
     * Adds joins orders to filter builder
     *
     * @return {@code FilterBuilder<X>} current instance of filter builder
     */
    private FilterBuilder<X> addJoinsOrders() {
        Arrays.stream(joined).forEach(
                c -> orders.addAll(getHandler().fetchJoinOrderBy(getRequest(), getRoot(), getBuilder(), c))
        );
        return this;
    }

    /**
     * Merges predicates contained in filter builder
     *
     * @param j provided joining
     * @return {@code Predicate} merged predicate
     */
    private Predicate mergePredicates(Joining j) {
        Predicate p = null;

        if (!predicates.isEmpty()) {
            if (j.equals(Joining.AND)) {
                p = getBuilder().and(predicates.toArray(new Predicate[0]));
            }

            if (j.equals(Joining.OR)) {
                p =  getBuilder().or(predicates.toArray(new Predicate[0]));
            }
        }

        return p;
    }

    /**
     * Gets provided entity manager
     *
     * @return {@code String} provided entity manager
     */
    public EntityManager getManager() {
        return this.entityManager;
    }

    /**
     * Gets criteria builder from criteria config
     *
     * @return {@code CriteriaBuilder} criteria builder from criteria config
     */
    public CriteriaBuilder getBuilder() {
        return this.config.getCriteriaBuilder();
    }

    /**
     * Gets criteria query from criteria config
     *
     * @return {@code CriteriaQuery<X>} criteria query from criteria config
     */
    public CriteriaQuery<X> getQuery() {
        return this.config.getCriteriaQuery();
    }

    /**
     * Gets root from criteria config
     *
     * @return {@code Root<X>} root from criteria config
     */
    public Root<X> getRoot() {
        return this.config.getRoot();
    }

    /**
     * Gets applied request parameter handler
     *
     * @return {@code RequestParameterHandler} applied request parameter handler
     */
    public RequestParameterHandler getHandler() {
        return this.requestParameterHandler;
    }

    /**
     * Gets applied search param request
     *
     * @return {@code SearchParamRequest} applied search param request
     */
    public SearchParamRequest getRequest() {
        return this.searchParamRequest;
    }

    /**
     * Gets value from predicates
     *
     * @return {@code List<Predicate>} predicates contained in filter builder
     */
    public List<Predicate> getPredicates() {
        return this.predicates;
    }

    /**
     * Gets value from orders
     *
     * @return {@code List<Order>} orders contained in filter builder
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    /**
     * Gets value from finalPredicate field
     *
     * @return {@code Predicate} finalPredicate value
     */
    public Predicate getFinalPredicate() {
        return this.finalPredicate;
    }
}