package epam.com.esm.utils.verifiers.action;

import epam.com.esm.exception.types.EmptyOrderException;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static epam.com.esm.utils.adjusters.action.OrderAdjuster.adjustUser;
import static epam.com.esm.utils.adjusters.products.GiftCertificateAdjuster.checkGiftCertificates;
import static epam.com.esm.utils.adjusters.products.GiftCertificateAdjuster.convertToGiftCertificates;

/**
 * OrderDtoVerifier is the service class, verifies order dto request and converts it to order
 */
@Component
public class OrderDtoVerifier {

    /**
     * Default constructor
     */
    @Autowired
    public OrderDtoVerifier() {}

    /**
     * Verifies order dto request on create operation
     *
     * @param dto provided order dto request
     * @return {@code Order} generated order
     */
    public Order verifyCreate(OrderDtoRequest dto) {
        return verify(dto);
    }

    /**
     * Verifies order dto request, user dto request and gift certificates dto requests
     *
     * @param dto provided order dto request
     * @return {@code Order} generated order
     */
    public Order verify(OrderDtoRequest dto) {
        Order order = new Order();

        adjustUser(order, dto);

        if (checkGiftCertificates(dto.getGiftCertificates())) {
            order.setGiftCertificates(convertToGiftCertificates(dto.getGiftCertificates()));
        } else {
            throw new EmptyOrderException("Resource cannot be created");
        }

        return order;
    }

    /**
     * Verifies order dto request with gift certificates and without user
     *
     * @param dto provided order dto request
     * @return {@code Order} generated order
     */
    public Order verifyWithoutUser(OrderDtoRequest dto) {
        Order order = new Order();

        if (checkGiftCertificates(dto.getGiftCertificates())) {
            order.setGiftCertificates(convertToGiftCertificates(dto.getGiftCertificates()));
        } else {
            throw new EmptyOrderException("Resource cannot be created");
        }

        return order;
    }
}