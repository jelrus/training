package epam.com.esm.utils.search.transport.request;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * PageDataRequest is the data class, which holds web request URL params
 */
public class PageDataRequest {

    /**
     * Holds URL full param map
     */
    private final Map<String, String[]> fullParams;

    /**
     * Constructs page data request from provided web request
     *
     * @param webRequest provided web request
     */
    public PageDataRequest(WebRequest webRequest) {
        fullParams = webRequest.getParameterMap();
    }

    /**
     * Gets full URL params map
     *
     * @return {@code Map<String, String[]>} full URL params map
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
        PageDataRequest that = (PageDataRequest) o;
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