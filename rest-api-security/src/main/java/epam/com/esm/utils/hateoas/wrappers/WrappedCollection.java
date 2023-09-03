package epam.com.esm.utils.hateoas.wrappers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import epam.com.esm.utils.hateoas.wrappers.components.MenuDto;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;
import java.util.Objects;

/**
 * WrappedCollection is the data class, serves as the representation model for collections
 *
 * @param <E> describes class for collection
 */
@JsonPropertyOrder(alphabetic = true)
public class WrappedCollection<E> extends RepresentationModel<WrappedCollection<E>> {

    /**
     * Holds items applied to collection
     */
    private Collection<E> items;

    /**
     * Holds mainMenu object
     */
    private MenuDto mainMenu;

    /**
     * Holds paginationMenu object for easier link distribution
     */
    private MenuDto paginationMenu;

    /**
     * Constructs WrappedCollection without applied collection
     */
    public WrappedCollection() {
        this.mainMenu = new MenuDto();
        this.paginationMenu = new MenuDto();
    }

    /**
     * Constructs WrappedCollection with applied collection
     */
    public WrappedCollection(Collection<E> items) {
        this();
        this.items = items;
    }

    /**
     * Gets value from items field
     *
     * @return {@code Collection<E>} items collection
     */
    public Collection<E> getItems() {
        return items;
    }

    /**
     * Sets new collection to items field
     *
     * @param items for setting
     */
    public void setItems(Collection<E> items) {
        this.items = items;
    }

    /**
     * Gets object from mainMenu field
     *
     * @return {@code MenuDto} mainMenu object
     */
    public MenuDto getMainMenu() {
        return mainMenu;
    }

    /**
     * Sets new object to mainMenu field
     *
     * @param mainMenu object for setting
     */
    public void setMainMenu(MenuDto mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Gets object from paginationMenu field
     *
     * @return {@code MenuDto} paginationMenu object
     */
    public MenuDto getPaginationMenu() {
        return paginationMenu;
    }

    /**
     * Sets new object to paginationMenu field
     *
     * @param paginationMenu object for setting
     */
    public void setPaginationMenu(MenuDto paginationMenu) {
        this.paginationMenu = paginationMenu;
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
        WrappedCollection<?> that = (WrappedCollection<?>) o;
        return Objects.equals(mainMenu, that.mainMenu) && Objects.equals(paginationMenu, that.paginationMenu);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mainMenu, paginationMenu);
    }
}