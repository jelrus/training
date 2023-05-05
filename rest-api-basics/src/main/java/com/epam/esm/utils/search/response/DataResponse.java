package com.epam.esm.utils.search.response;

import com.epam.esm.view.dto.response.DtoResponse;

import java.util.*;

/**
 * DataResponse is the utility class, which holds response data of the DataRequest result
 *
 * @param <E> describes child of DtoResponse
 */
public class DataResponse<E extends DtoResponse> {

    /**
     * Object, which holds request params
     */
    private Map<String, String[]> fullParams;

    /**
     * Collection, which holds dto items from request result
     */
    private Set<E> items;

    /**
     * Constructs DataResponse object with initialized full params map and result dto items set
     */
    public DataResponse() {
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
     * Sets full params map for the response
     *
     * @param fullParams requested params
     */
    public void setFullParams(Map<String, String[]> fullParams) {
        this.fullParams = fullParams;
    }

    /**
     * Gets result dto items
     *
     * @return {@code Set<E>} result dto items
     */
    public Set<E> getItems() {
        return items;
    }

    /**
     * Sets result dto items
     *
     * @param items requested dto items
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
        DataResponse<?> that = (DataResponse<?>) o;
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