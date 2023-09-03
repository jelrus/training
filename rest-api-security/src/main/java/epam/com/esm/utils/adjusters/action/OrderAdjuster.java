package epam.com.esm.utils.adjusters.action;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * OrderAdjuster utility class, adjust fields on order create or update operations
 */
public final class OrderAdjuster {

    /**
     * Default constructor
     */
    private OrderAdjuster() {}

    /**
     * Adjusts order fields on update by copying fields
     *
     * @param preUpdate requested pre update order
     * @param updated requested order for update
     */
    public static void adjustFields(Order preUpdate, Order updated) {
        updated.setId(preUpdate.getId());
        updated.setUser(preUpdate.getUser());
        updated.setPurchaseDate(preUpdate.getPurchaseDate());
        updated.setCost(preUpdate.getCost());
    }

    /**
     * Adjusts user for order by converting user from dto request
     *
     * @param order requested order
     * @param dto requested order dto request
     */
    public static void adjustUser(Order order, OrderDtoRequest dto) {
        if (!StringUtils.isBlank(dto.getUsername())) {
            User user = new User();
            user.setUsername(dto.getUsername());
            order.setUser(user);
        } else {
            throw new InputException("User field in order was corrupted or malformed");
        }
    }
}