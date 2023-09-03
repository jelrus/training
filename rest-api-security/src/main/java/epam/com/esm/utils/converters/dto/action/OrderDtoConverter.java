package epam.com.esm.utils.converters.dto.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * OrderDtoConverter is the utility class, converts fields from order object to order dto response
 */
public final class OrderDtoConverter {

    /**
     * Default constructor
     */
    private OrderDtoConverter() {}

    /**
     * Converts order object to order dto response
     * If full equals true will convert order with gift certificates, otherwise only order will be converted
     *
     * @param o requested order
     * @param full requested view
     * @return {@code OrderDtoResponse} converted order dto response
     */
    public static OrderDtoResponse toDto(Order o, boolean full) {
        OrderDtoResponse dtoResp = new OrderDtoResponse(o);

        if (full) {
            collectionToDto(o, dtoResp);
        }

        return dtoResp;
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