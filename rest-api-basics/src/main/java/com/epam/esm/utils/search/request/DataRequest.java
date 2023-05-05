package com.epam.esm.utils.search.request;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * DataRequest is the data class, which holds parameter map for search request
 */
public class DataRequest {

    /**
     * Object, which holds parameter map from web request
     */
    private final Map<String, String[]> fullParams;

    /**
     * Constructs DataRequest object
     * Copies parameter map from WebRequest object
     *
     * @param webRequest object, which hold parameter map for search request
     */
    public DataRequest(WebRequest webRequest) {
        fullParams = webRequest.getParameterMap();
    }

    /**
     * Gets full parameters map
     *
     * @return {@code Map<String, String[]>} parameters map
     */
    public Map<String, String[]> getFullParams() {
        return fullParams;
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
        DataRequest that = (DataRequest) o;
        return Objects.equals(fullParams, that.fullParams);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(fullParams);
    }
}