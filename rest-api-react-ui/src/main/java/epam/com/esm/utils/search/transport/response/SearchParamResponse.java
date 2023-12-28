package epam.com.esm.utils.search.transport.response;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.hateoas.wrappers.type.PageType;

import java.util.*;

/**
 * SearchParamResponse is the data class, holds result of query search and params from search
 *
 * @param <E> describes erasure type of search param response
 */
public class SearchParamResponse<E extends BaseEntity> {

    /**
     * Holds shown items value
     */
    private int shownItems;

    /**
     * Holds found items value
     */
    private int foundItems;

    /**
     * Holds current page value
     */
    private int currentPage;

    /**
     * Holds total pages value
     */
    private int totalPages;

    /**
     * Holds page size value
     */
    private int pageSize;

    /**
     * Holds fold value
     */
    private boolean fold;

    /**
     * Holds full params
     */
    private final Map<String, String[]> fullParams;

    /**
     * Holds pages
     */
    private Map<PageType, String> pages;

    /**
     * Holds items
     */
    private List<E> items;

    /**
     * Default constructor
     */
    public SearchParamResponse() {
        this.fullParams = new LinkedHashMap<>();
        this.items = Collections.emptyList();
        this.shownItems = 0;
        this.foundItems = 0;
        this.currentPage = 0;
        this.totalPages = 0;
        this.pageSize = 0;
    }

    /**
     * Gets value from shownItems field
     *
     * @return {@code int} shownItems value
     */
    public int getShownItems() {
        return shownItems;
    }

    /**
     * Sets new value to shownItems field
     *
     * @param shownItems value for setting
     */
    public void setShownItems(int shownItems) {
        this.shownItems = shownItems;
    }

    /**
     * Gets value from foundItems field
     *
     * @return {@code int} foundItems value
     */
    public int getFoundItems() {
        return foundItems;
    }

    /**
     * Sets new value to foundItems field
     *
     * @param foundItems value for setting
     */
    public void setFoundItems(int foundItems) {
        this.foundItems = foundItems;
    }

    /**
     * Gets value from currentPage field
     *
     * @return {@code int} currentPage value
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets new value to currentPage field
     *
     * @param currentPage value for setting
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets value from totalPages field
     *
     * @return {@code int} totalPages value
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets new value to totalPages field
     *
     * @param totalPages value for setting
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Gets value from pageSize field
     *
     * @return {@code int} pageSize value
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets new value to pageSize field
     *
     * @param pageSize value for setting
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
     * @return {@code Map<String, String[]>} full params map
     */
    public Map<String, String[]> getFullParams() {
        return fullParams;
    }

    /**
     * Gets pages map
     *
     * @return {@code Map<PageType, String>} pages map
     */
    public Map<PageType, String> getPages() {
        return pages;
    }

    /**
     * Sets new pages map
     *
     * @param pages provided pages map
     */
    public void setPages(Map<PageType, String> pages) {
        this.pages = pages;
    }

    /**
     * Gets found items
     *
     * @return {@code List<E>} found items
     */
    public List<E> getItems() {
        return items;
    }

    /**
     * Sets new items
     *
     * @param items found items
     */
    public void setItems(List<E> items) {
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
        return shownItems == that.shownItems
               && foundItems == that.foundItems
               && currentPage == that.currentPage
               && totalPages == that.totalPages
               && pageSize == that.pageSize
               && fold == that.fold
               && Objects.equals(fullParams, that.fullParams)
               && Objects.equals(pages, that.pages)
               && Objects.equals(items, that.items);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(shownItems, foundItems, currentPage, totalPages, pageSize, fold, fullParams, pages, items);
    }
}