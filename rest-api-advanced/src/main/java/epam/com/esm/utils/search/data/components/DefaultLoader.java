package epam.com.esm.utils.search.data.components;

import java.util.Objects;

/**
 * DefaultLoader is the data class, serves as object which holds default pagination data
 */
public class DefaultLoader {

    /**
     * Holds sort value
     */
    private String sort;

    /**
     * Holds order value
     */
    private String order;

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
     * Affects on view: if fold mode is on - will show object with collection, otherwise will show only object
     */
    private boolean fold;

    /**
     * Constructs DefaultLoader with provided sort, order, page, size and fold values
     *
     * @param sort initial sort value
     * @param order initial order value
     * @param page initial page value
     * @param size  initial size value
     * @param fold initial fold view mode
     */
    public DefaultLoader(String sort, String order, int page, int size, boolean fold) {
        this.sort = sort;
        this.order = order;
        this.page = page;
        this.size = size;
        this.fold = fold;
    }

    /**
     * Gets value from sort field
     *
     * @return {@code String} sort value
     */
    public String getSort() {
        return sort;
    }

    /**
     * Sets new value to sort field
     *
     * @param sort value for setting
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Gets value from order field
     *
     * @return {@code String} order value
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets new value to order field
     *
     * @param order value for setting
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Gets value from page field
     *
     * @return {@code int} page value
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
     * Gets value from fold field
     *
     * @return {@code true} fold value
     */
    public boolean isFold() {
        return fold;
    }

    /**
     * Sets new value to fold field
     *
     * @param fold value for setting
     */
    public void setFold(boolean fold) {
        this.fold = fold;
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
        DefaultLoader that = (DefaultLoader) o;
        return page == that.page
               && size == that.size
               && fold == that.fold
               && Objects.equals(sort, that.sort)
               && Objects.equals(order, that.order);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sort, order, page, size, fold);
    }
}