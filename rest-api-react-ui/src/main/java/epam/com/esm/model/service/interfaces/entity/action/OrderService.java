package epam.com.esm.model.service.interfaces.entity.action;

import epam.com.esm.model.service.interfaces.base.CrdService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * OrderService is the interface that delegates CRUD contracts from ancestors and specific operations for
 * order logic contracts to implementor
 */
public interface OrderService extends CrdService<Order, Long> {

    /**
     * Contract for producing SearchParamResponse object with found gift certificates by requested search params
     * and provided id parameter value
     *
     * @param orderId requested parameter value, holds id value
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found gift certificates
     */
    SearchParamResponse<GiftCertificate> findGiftCertificates(Long orderId, SearchParamRequest searchParamRequest);
}