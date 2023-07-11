package epam.com.esm.model.dao.interfaces.entity.action;

import epam.com.esm.model.dao.interfaces.base.CrudDao;
import epam.com.esm.model.dao.interfaces.supplementary.Existent;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * OrderDao is the interface that delegates CRUD contracts from ancestors and specific operations for
 * order logic contracts to implementor
 */
public interface OrderDao extends CrudDao<Order, Long>, Existent<Long> {

    /**
     * Contract for finding gift certificates by specified order id and search param request
     *
     * @param orderId requested parameter, holds order id value
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * gift certificates
     */
    SearchParamResponse<GiftCertificate> findGiftCertificates(Long orderId, SearchParamRequest searchParamRequest);
}