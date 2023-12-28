package epam.com.esm.utils.converters.dto.user;

import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * UserDtoConverter is the utility class, converts fields from user object to user dto response
 */
public final class UserDtoConverter {

    /**
     * Default constructor
     */
    private UserDtoConverter() {}

    /**
     * Converts user object to user dto response
     * If full equals true will convert user with orders, otherwise only user will be converted
     *
     * @param u requested user
     * @param full requested view
     * @return {@code UserDtoResponse} converted user dto response
     */
    public static UserDtoResponse toDto(User u, boolean full) {
        UserDtoResponse dtoResp = new UserDtoResponse(u);

        if (full) {
            collectionToDto(u, dtoResp);
        }

        return dtoResp;
    }

    /**
     * Converts orders for user dto response from user object
     *
     * @param u requested user
     * @param r requested user dto response
     */
    public static void collectionToDto(User u, UserDtoResponse r) {
        if (u.getOrders() != null && !u.getOrders().isEmpty()) {
            r.setOrders(
                    u.getOrders().stream().map(OrderDtoResponse::new)
                                          .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }
}