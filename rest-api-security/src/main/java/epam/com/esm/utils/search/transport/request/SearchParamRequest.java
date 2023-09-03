package epam.com.esm.utils.search.transport.request;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SearchParamRequest is the data class, holds params for query building
 */
public class SearchParamRequest {

    /**
     * Holds page value
     */
    private int page;

    /**
     * Holds size value
     */
    private int size;

    /**
     * Holds fold value
     */
    private boolean fold;

    /**
     * Holds fullParams
     */
    private Map<String, List<String>> fullParams;

    /**
     * Holds partParams
     */
    private Map<String, List<String>> partParams;

    /**
     * Holds sortParams
     */
    private Map<String, List<String>> sortParams;

    /**
     * Default constructor
     */
    public SearchParamRequest() {
        this.fullParams = new LinkedHashMap<>();
        this.partParams = new LinkedHashMap<>();
        this.sortParams = new LinkedHashMap<>();
    }

    /**
     * Gets value from page field
     *
     * @return {@code String} page value
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets new value to page field
     *
     * @param page value for setting
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets value from size field
     *
     * @return {@code int} size value
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets new value to size field
     *
     * @param size value for setting
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets fold mode value
     *
     * @return {@code boolean} fold value
     */
    public boolean isFold() {
        return fold;
    }

    /**
     * Sets fold mode
     *
     * @param fold value for setting
     */
    public void setFold(boolean fold) {
        this.fold = fold;
    }

    /**
     * Gets full params map
     *
     * @return {@code Map<String, List<String>>} full params map
     */
    public Map<String, List<String>> getFullParams() {
        return fullParams;
    }

    /**
     * Sets new full params map
     *
     * @param fullParams provided full params map
     */
    public void setFullParams(Map<String, List<String>> fullParams) {
        this.fullParams = fullParams;
    }

    /**
     * Gets part params map
     *
     * @return {@code Map<String, List<String>>} part params map
     */
    public Map<String, List<String>> getPartParams() {
        return partParams;
    }

    /**
     * Sets new part params map
     *
     * @param partParams provided part params map
     */
    public void setPartParams(Map<String, List<String>> partParams) {
        this.partParams = partParams;
    }

    /**
     * Gets sort params map
     *
     * @return {@code Map<String, List<String>>} sort params map
     */
    public Map<String, List<String>> getSortParams() {
        return sortParams;
    }

    /**
     * Sets new sort params map
     *
     * @param sortParams provided sort params map
     */
    public void setSortParams(Map<String, List<String>> sortParams) {
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
        return page == that.page && size == that.size
               && fold == that.fold && Objects.equals(fullParams, that.fullParams)
               && Objects.equals(partParams, that.partParams)
               && Objects.equals(sortParams, that.sortParams);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(page, size, fold, fullParams, partParams, sortParams);
    }
}