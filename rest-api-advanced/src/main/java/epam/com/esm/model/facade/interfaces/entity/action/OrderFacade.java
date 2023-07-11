package epam.com.esm.model.facade.interfaces.entity.action;

import epam.com.esm.model.facade.interfaces.base.CrudFacade;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.audit.OrderDtoRequest;
import epam.com.esm.view.dto.response.impl.audit.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * OrderFacade is the interface that delegates CRUD contracts from ancestors and specific operations for
 * order logic contracts to implementor
 */
public interface OrderFacade extends CrudFacade<OrderDtoRequest, OrderUserDtoResponse, Long> {

    /**
     * Contract for producing PageDataResponse object by requested search params
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, contains gift certificate dto responses and
     * response params
     */
    PageDataResponse<GiftCertificateDtoResponse> findGiftCertificates(Long orderId, WebRequest webRequest);
}