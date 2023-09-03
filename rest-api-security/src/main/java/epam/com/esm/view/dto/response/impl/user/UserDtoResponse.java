package epam.com.esm.view.dto.response.impl.user;

import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;

import java.util.List;
import java.util.Objects;

/**
 * UserDtoResponse is the data class, serves as user's values carrier in requests for select operations, represents
 * full object for view
 */
public class UserDtoResponse extends DtoResponse {

    /**
     * Holds username value
     */
    private String username;

    /**
     * Holds orders dto responses
     */
    private List<OrderDtoResponse> orders;

    /**
     * Constructs user dto response from provided user
     *
     * @param user provided user
     */
    public UserDtoResponse(User user) {
        super(user.getId());
        setUsername(user.getUsername());
    }

    /**
     * Gets value from username field
     *
     * @return {@code String} username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets new value to username field
     *
     * @param username value for setting
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets orders dto responses
     *
     * @return {@code List<OrderDtoResponse>} orders dto responses
     */
    public List<OrderDtoResponse> getOrders() {
        return orders;
    }

    /**
     * Sets new orders dto responses
     *
     * @param orders orders dto responses
     */
    public void setOrders(List<OrderDtoResponse> orders) {
        this.orders = orders;
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
        UserDtoResponse that = (UserDtoResponse) o;
        return Objects.equals(username, that.username)
               && Objects.equals(orders, that.orders);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, orders);
    }
}