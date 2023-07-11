package epam.com.esm.utils.search.transport.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.hateoas.wrappers.type.PageType;
import epam.com.esm.view.dto.response.DtoResponse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PageDataResponse is the data class, holds result of search, params from search, and params required
 * for correct viewing
 *
 * @param <A> describes erasure type of page data response
 */
@JsonPropertyOrder({"currentPage", "totalPages", "itemsShown", "itemsFound", "maxShown", "fold", "fullParams",
                    "items", "mainMenu", "paginationMenu"})
public class PageDataResponse<A extends DtoResponse> extends WrappedCollection<A> {

    /**
     * Holds current page value
     */
    private int currentPage;

    /**
     * Holds total pages value
     */
    private int totalPages;

    /**
     * Holds items shown value
     */
    private int itemsShown;

    /**
     * Holds items found value
     */
    private long itemsFound;

    /**
     * Holds max shown value
     */
    private int maxShown;

    /**
     * Holds fold value
     */
    private boolean fold;

    /**
     * Holds full params
     */
    private Map<String, String[]> fullParams;

    /**
     * Holds pages
     */
    @JsonIgnore
    private Map<PageType, String> pages;


    /**
     * Default constructor
     */
    public PageDataResponse() {
        fullParams = new LinkedHashMap<>();
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
     * Gets value from itemsShown field
     *
     * @return {@code int} itemsShown value
     */
    public int getItemsShown() {
        return itemsShown;
    }

    /**
     * Sets new value to itemsShown field
     *
     * @param itemsShown value for setting
     */
    public void setItemsShown(int itemsShown) {
        this.itemsShown = itemsShown;
    }

    /**
     * Gets value from itemsFound field
     *
     * @return {@code long} itemsFound value
     */
    public long getItemsFound() {
        return itemsFound;
    }

    /**
     * Sets new value to itemsFound field
     *
     * @param itemsFound value for setting
     */
    public void setItemsFound(long itemsFound) {
        this.itemsFound = itemsFound;
    }

    /**
     * Gets value from maxShown field
     *
     * @return {@code int} maxShown value
     */
    public int getMaxShown() {
        return maxShown;
    }

    /**
     * Sets new value to maxShown field
     *
     * @param maxShown value for setting
     */
    public void setMaxShown(int maxShown) {
        this.maxShown = maxShown;
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
     * Sets new full params map
     *
     * @param fullParams provided full params map
     */
    public void setFullParams(Map<String, String[]> fullParams) {
        this.fullParams = fullParams;
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
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PageDataResponse<?> that = (PageDataResponse<?>) o;
        return currentPage == that.currentPage
               && totalPages == that.totalPages
               && itemsShown == that.itemsShown
               && itemsFound == that.itemsFound
               && maxShown == that.maxShown
               && fold == that.fold
               && Objects.equals(fullParams, that.fullParams)
               && Objects.equals(pages, that.pages);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), currentPage, totalPages, itemsShown, itemsFound, maxShown, fold, fullParams, pages
        );
    }
}