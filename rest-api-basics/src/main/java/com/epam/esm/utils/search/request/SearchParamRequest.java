package com.epam.esm.utils.search.request;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * SearchParamRequest is the data class, which holds split parameter maps for each type of parameters for search request
 */
public class SearchParamRequest {

    /**
     * Object, which holds full search parameters
     */
    private Map<String, String> fullSearch;

    /**
     * Object, which holds part search parameters
     */
    private Map<String, String> partSearch;

    /**
     * Object, which holds sort parameters
     */
    private Map<String, String> sortParams;

    /**
     * Constructs SearchParamRequest object with initialized search params maps
     */
    public SearchParamRequest() {
        fullSearch = new LinkedHashMap<>();
        partSearch = new LinkedHashMap<>();
        sortParams = new LinkedHashMap<>();
    }

    /**
     * Gets full search params map
     *
     * @return {@code Map<String, String>} full search params map
     */
    public Map<String, String> getFullSearch() {
        return fullSearch;
    }

    /**
     * Sets new full search map
     *
     * @param fullSearch full search map for setting
     */
    public void setFullSearch(Map<String, String> fullSearch) {
        this.fullSearch = fullSearch;
    }

    /**
     * Gets part search params map
     *
     * @return {@code Map<String, String>} part search params map
     */
    public Map<String, String> getPartSearch() {
        return partSearch;
    }

    /**
     * Sets new part search map
     *
     * @param partSearch part search map for setting
     */
    public void setPartSearch(Map<String, String> partSearch) {
        this.partSearch = partSearch;
    }

    /**
     * Gets sort params map
     *
     * @return {@code Map<String, String>} sort params map
     */
    public Map<String, String> getSortParams() {
        return sortParams;
    }

    /**
     * Sets new sort map
     *
     * @param sortParams sort map for setting
     */
    public void setSortParams(Map<String, String> sortParams) {
        this.sortParams = sortParams;
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
        SearchParamRequest that = (SearchParamRequest) o;
        return Objects.equals(fullSearch, that.fullSearch) && Objects.equals(partSearch, that.partSearch) && Objects.equals(sortParams, that.sortParams);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(fullSearch, partSearch, sortParams);
    }
}