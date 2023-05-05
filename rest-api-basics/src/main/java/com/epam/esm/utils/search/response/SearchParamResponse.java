package com.epam.esm.utils.search.response;

import com.epam.esm.persistence.entity.BaseEntity;

import java.util.*;

/**
 * SearchParamResponse is the utility class, which holds response data of the SearchParamRequest result
 *
 * @param <E> describes child of BaseEntity
 */
public class SearchParamResponse<E extends BaseEntity> {

    /**
     * Object, which holds request params
     */
    private final Map<String, String[]> fullParams;

    /**
     * Collection, which holds items from request result
     */
    private Set<E> items;

    /**
     * Constructs SearchParamResponse object with initialized full params map and result items set
     */
    public SearchParamResponse() {
        fullParams = new LinkedHashMap<>();
        items = Collections.emptySet();
    }

    /**
     * Gets full params map of the request
     *
     * @return {@code Map<String, String[]>} full params map
     */
    public Map<String, String[]> getFullParams() {
        return fullParams;
    }

    /**
     * Gets result items
     *
     * @return {@code Set<E>} result items
     */
    public Set<E> getItems() {
        return items;
    }

    /**
     * Sets result items
     *
     * @param items requested items
     */
    public void setItems(Set<E> items) {
        this.items = items;
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
        SearchParamResponse<?> that = (SearchParamResponse<?>) o;
        return Objects.equals(fullParams, that.fullParams) && Objects.equals(items, that.items);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(fullParams, items);
    }
}