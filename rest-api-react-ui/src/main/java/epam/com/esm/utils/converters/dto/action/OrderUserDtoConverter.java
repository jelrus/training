package epam.com.esm.utils.converters.dto.action;

import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * OrderUserDtoConverter is the utility class, converts fields from order object to order user dto response
 */
public final class OrderUserDtoConverter {

    /**
     * Default constructor
     */
    private OrderUserDtoConverter() {}

    /**
     * Converts order object to order user dto response
     * If full equals true will convert order with gift certificates and user, otherwise only order will be converted
     *
     * @param o requested order
     * @param full requested view
     * @return {@code OrderUserDtoResponse} converted order user dto response
     */
    public static OrderUserDtoResponse toDto(Order o, boolean full) {
        OrderUserDtoResponse dtoResp = new OrderUserDtoResponse(o);
        dtoResp.setPurchaseDate(o.getPurchaseDate());

        if (full) {
            dtoResp.setUser(userToReducedDto(o));
            collectionToDto(o, dtoResp);
        }

        return dtoResp;
    }

    /**
     * Converts user from order object to user dto response without applied orders
     *
     * @param o requested order
     * @return {@code UserDtoResponse} converted user dto response
     */
    private static UserDtoResponse userToReducedDto(Order o) {
        if (o.getUser() != null) {
            UserDtoResponse dto = new UserDtoResponse(o.getUser());
            dto.setOrders(Collections.emptyList());
            return dto;
        } else {
            throw new NotFoundException("User for order (id = " + o.getId() + ") wasn't found");
        }
    }

    /**
     * Converts gift certificates for order dto response from order object
     *
     * @param o requested order
     * @param r requested order dto response
     */
    private static void collectionToDto(Order o, OrderDtoResponse r) {
        if (o.getGiftCertificates() != null && !o.getGiftCertificates().isEmpty()) {
            r.setGiftCertificates(
                    o.getGiftCertificates().stream()
                                           .map(GiftCertificateDtoResponse::new)
                                           .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }
}