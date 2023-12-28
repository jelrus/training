package epam.com.esm.model.facade.impl.action;

import epam.com.esm.model.facade.interfaces.entity.action.OrderFacade;
import epam.com.esm.model.service.interfaces.entity.action.OrderService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.action.OrderUserDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.verifiers.action.OrderDtoVerifier;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import static epam.com.esm.utils.converters.dto.action.OrderUserDtoConverter.toDto;

/**
 * OrderFacadeImpl is the service class and implementor of OrderFacade interface
 * Validates and adjusts order dto request before forwarding it to service and converts to dto response after
 * receiving answer from service
 */
@Service
public class OrderFacadeImpl implements OrderFacade {

    /**
     * Holds OrderService object
     */
    private final OrderService orderService;

    /**
     * Holds OrderDtoVerifier object
     */
    private final OrderDtoVerifier orderDtoVerifier;

    /**
     * Constructs OrderFacadeImpl with OrderService and OrderDtoVerifier objects
     *
     * @param orderService service, provides logic operations for orders
     * @param orderDtoVerifier service, provides validations operations for orders
     */
    @Autowired
    public OrderFacadeImpl(OrderService orderService, OrderDtoVerifier orderDtoVerifier) {
        this.orderService = orderService;
        this.orderDtoVerifier = orderDtoVerifier;
    }

    /**
     * Consumes dto request, validates it and produces dto response as the result of creation
     *
     * @param dto requested object, holds requested values for order
     * @return {@code OrderUserDtoResponse} created order
     */
    @Override
    public OrderUserDtoResponse create(OrderDtoRequest dto) {
        Order order = orderService.create(orderDtoVerifier.verifyCreate(dto));
        return toDto(order, true);
    }

    /**
     * Consumes id parameter value, finds order and produces dto response if order was found
     *
     * @param id requested parameter value, holds order id value
     * @return {@code OrderUserDtoResponse} found order
     */
    @Override
    public OrderUserDtoResponse findById(Long id) {
        return toDto(orderService.findById(id), true);
    }

    /**
     * Consumes id value, deletes order and produces dto response if order was deleted
     *
     * @param id requested parameter value, holds order id value
     * @return {@code OrderUserDtoResponse} deleted order
     */
    @Override
    public OrderUserDtoResponse delete(Long id) {
        return toDto(orderService.delete(id), true);
    }

    /**
     * Consumes web request, finds by its URL request params orders and produces page data response as
     * the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<OrderUserDtoResponse>} object, holds found orders and search
     * response params
     */
    @Override
    public PageDataResponse<OrderUserDtoResponse> findAll(WebRequest webRequest) {
        AbstractDataHandler<Order, OrderUserDtoResponse> adh = new OrderUserDataHandler();
        return adh.processOutput(orderService.findAll(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request and order id parameter value, finds by its URL request params gift certificates of
     * significant order and produces page data response as the result of search
     *
     * @param orderId requested parameter value, holds order id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, holds found gift certificates and search
     * response params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> findGiftCertificates(Long orderId, WebRequest webRequest) {
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        return adh.processOutput(orderService.findGiftCertificates(orderId, adh.processSearch(webRequest)));
    }
}