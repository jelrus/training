package epam.com.esm.model.facade.impl.action;

import epam.com.esm.model.service.interfaces.entity.action.OrderService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.action.OrderUserDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.verifiers.action.OrderDtoVerifier;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static epam.com.esm.model.suppliers.facade.action.OrderFacadeSupplier.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderFacadeImplTest {

    @Mock
    public OrderService orderService;

    @Mock
    public OrderDtoVerifier orderDtoVerifier;

    @InjectMocks
    public OrderFacadeImpl orderFacade;

    @Test
    public void willCreate() {
        //initial data
        OrderDtoRequest oReq = getProperOrderDtoRequest();
        Order o = getProperOrder();
        OrderUserDtoResponse ouResp = getProperOrderUserDtoResponse(o);

        //create
        when(orderDtoVerifier.verifyCreate(oReq)).thenReturn(o);
        when(orderService.create(o)).thenReturn(o);

        //generate response
        OrderUserDtoResponse created =  orderFacade.create(oReq);
        Assertions.assertEquals(ouResp, created);
    }

    @Test
    public void willFindById() {
        //initial data
        Order o = getProperOrder();
        OrderUserDtoResponse ouResp = getProperOrderUserDtoResponse(o);

        //find by id
        when(orderService.findById(o.getId())).thenReturn(o);

        //generate response
        OrderUserDtoResponse found = orderFacade.findById(o.getId());
        Assertions.assertEquals(ouResp, found);
    }

    @Test
    public void willDelete() {
        //initial data
        Order o = getProperOrder();
        OrderUserDtoResponse ouResp = getProperOrderUserDtoResponse(o);

        //delete
        when(orderService.delete(o.getId())).thenReturn(o);

        //generate response
        OrderUserDtoResponse deleted = orderFacade.delete(o.getId());
        Assertions.assertEquals(ouResp, deleted);
    }

    @Test
    public void willFindAll() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        AbstractDataHandler<Order, OrderUserDtoResponse> adh = new OrderUserDataHandler();
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();

        //find all
        when(orderService.findAll(adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<OrderUserDtoResponse> pdr = orderFacade.findAll(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(OrderUserDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindGiftCertificates() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        Order o = getProperOrder();
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find gift certificates
        when(orderService.findGiftCertificates(o.getId(), adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<GiftCertificateDtoResponse> pdr = orderFacade.findGiftCertificates(o.getId(), webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(GiftCertificateDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }
}