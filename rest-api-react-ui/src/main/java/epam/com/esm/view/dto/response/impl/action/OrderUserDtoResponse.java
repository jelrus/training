package epam.com.esm.view.dto.response.impl.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;

import java.util.Objects;

/**
 * OrderUserDtoResponse is the data class, serves as order's values carrier in requests for select operations,
 * represents full object with user for view
 */
public class OrderUserDtoResponse extends OrderDtoResponse {

    /**
     * Holds user dto response
     */
    private UserDtoResponse user;

    /**
     * Constructs order dto response from provided order
     *
     * @param order provided order
     */
    public OrderUserDtoResponse(Order order) {
        super(order);
        setPurchaseDate(order.getPurchaseDate());
    }

    /**
     * Gets user dto response
     *
     * @return {@code UserDtoResponse} user dto response
     */
    public UserDtoResponse getUser() {
        return user;
    }

    /**
     * Sets new user dto response
     *
     * @param user user dto response for setting
     */
    public void setUser(UserDtoResponse user) {
        this.user = user;
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
        OrderUserDtoResponse that = (OrderUserDtoResponse) o;
        return Objects.equals(user, that.user);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}