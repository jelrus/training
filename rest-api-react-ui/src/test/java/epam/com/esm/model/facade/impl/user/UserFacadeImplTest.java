package epam.com.esm.model.facade.impl.user;

import epam.com.esm.model.service.interfaces.entity.user.UserService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.converters.dto.custom.DtoDataTagConverter;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.action.OrderDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.data.impl.purchase.PurchaseDataHandler;
import epam.com.esm.utils.search.data.impl.user.UserDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.utils.verifiers.action.OrderDtoVerifier;
import epam.com.esm.utils.verifiers.user.UserDtoVerifier;
import epam.com.esm.view.dto.request.impl.action.OrderDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static epam.com.esm.model.suppliers.facade.action.OrderFacadeSupplier.getProperOrder;
import static epam.com.esm.model.suppliers.facade.user.UserFacadeSupplier.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserFacadeImplTest {

    @Mock
    public UserService userService;

    @Mock
    public UserDtoVerifier userDtoVerifier;

    @Mock
    public OrderDtoVerifier orderDtoVerifier;

    @InjectMocks
    public UserFacadeImpl userFacade;

    @Test
    public void willCreate() {
        //initial data
        User user = getProperUser();
        UserDtoRequest uReq = getProperUserDtoRequest();
        UserDtoResponse uResp = getProperUserDtoResponse(user);

        //create
        when(userDtoVerifier.verifyOnCreate(uReq)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);

        //generate response
        UserDtoResponse created = userFacade.create(uReq);
        Assertions.assertEquals(uResp, created);
    }

    @Test
    public void willFindById() {
        //initial data
        User user = getProperUser();
        UserDtoResponse uResp = getProperUserDtoResponse(user);

        //find by id
        when(userService.findById(user.getId())).thenReturn(user);

        //generate response
        UserDtoResponse found = userFacade.findById(user.getId());
        Assertions.assertEquals(uResp, found);
    }

    @Test
    public void willUpdate() {
        //initial data
        User user = getProperUser();
        UserDtoRequest uReq = getProperUserDtoRequest();
        UserDtoResponse uResp = getProperUserDtoResponse(user);

        //update
        when(userService.findById(user.getId())).thenReturn(user);
        when(userDtoVerifier.verifyOnUpdate(uReq, user)).thenReturn(user);
        when(userService.update(user)).thenReturn(user);

        //generate response
        UserDtoResponse updated = userFacade.update(uReq, user.getId());
        Assertions.assertEquals(uResp, updated);
    }

    @Test
    public void willDelete() {
        //initial data
        User user = getProperUser();
        UserDtoResponse uResp = getProperUserDtoResponse(user);

        //delete
        when(userService.delete(user.getId())).thenReturn(user);

        //generate response
        UserDtoResponse deleted = userFacade.delete(user.getId());
        Assertions.assertEquals(uResp, deleted);
    }

    @Test
    public void willFindAll() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        AbstractDataHandler<User, UserDtoResponse> adh = new UserDataHandler();
        SearchParamResponse<User> spResp = new SearchParamResponse<>();

        //find all
        when(userService.findAll(adh.processSearch(webRequest))).thenReturn(spResp);

        //generate response
        PageDataResponse<UserDtoResponse> pdr = userFacade.findAll(webRequest);
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(UserDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                               pdr.getItems());
    }

    @Test
    public void willMakeOrder() {
        //initial data
        OrderDtoRequest oReq = getProperOrderDtoRequest();
        Order order = getProperOrder();
        User user = getProperUser();
        UserDtoResponse uResp = getProperUserDtoResponse(user);

        //make order
        when(orderDtoVerifier.verifyWithoutUser(oReq)).thenReturn(order);
        when(userService.makeOrder(user.getId(), order)).thenReturn(user);

        //generate response
        UserDtoResponse withOrder = userFacade.makeOrder(user.getId(), oReq);
        Assertions.assertEquals(uResp, withOrder);
    }

    @Test
    public void willFindOrders() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        User user = getProperUser();
        AbstractDataHandler<Order, OrderDtoResponse> adh = new OrderDataHandler();
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();

        //find orders
        when(userService.findOrders(adh.processSearch(webRequest), user.getId())).thenReturn(spResp);

        //generate response
        PageDataResponse<OrderDtoResponse> pdr = userFacade.findOrders(webRequest, user.getId());
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(OrderDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindPurchases() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        User user = getProperUser();
        AbstractDataHandler<PurchaseData, PurchaseDataDtoResponse> adh = new PurchaseDataHandler();
        SearchParamResponse<PurchaseData> spResp = new SearchParamResponse<>();

        //find gift certificates
        when(userService.findPurchases(adh.processSearch(webRequest), user.getId())).thenReturn(spResp);

        //generate response
        PageDataResponse<PurchaseDataDtoResponse> pdr = userFacade.findPurchases(webRequest, user.getId());
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(p -> new PurchaseDataDtoResponse(p, false))
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindTags() {
        //mock web request
        WebRequest webRequest = mock(WebRequest.class);

        //initial data
        User user = getProperUser();
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find tags
        when(userService.findTags(adh.processSearch(webRequest), user.getId())).thenReturn(spResp);

        //generate response
        PageDataResponse<TagDtoResponse> pdr = userFacade.findTags(webRequest, user.getId());
        Assertions.assertEquals(spResp.getFullParams(), pdr.getFullParams());
        Assertions.assertEquals(spResp.getItems()
                                      .stream()
                                      .map(TagDtoResponse::new)
                                      .collect(Collectors.toCollection(ArrayList::new)),
                                pdr.getItems());
    }

    @Test
    public void willFindTagsByPopularity() {
        //initial data
        User user = getProperUser();
        ArrayList<DtoDataTag> resp = new ArrayList<>();

        //find tags by popularity
        when(userService.findTagsByPopularity(user.getId())
                        .stream()
                        .map(DtoDataTagConverter::toDto)
                        .collect(Collectors.toCollection(ArrayList::new))).thenReturn(resp);

        //generate response
        WrappedCollection<DtoDataTag> wc = userFacade.findTagsByPopularity(user.getId());
        Assertions.assertEquals(resp, wc.getItems());
    }

    @Test
    public void willFindTagsWithMaxCount() {
        //initial data
        User user = getProperUser();
        ArrayList<DtoDataTag> resp = new ArrayList<>();

        //find tags with max count
        when(userService.findTagsWithMaxCount(user.getId())
                        .stream()
                        .map(DtoDataTagConverter::toDto)
                        .collect(Collectors.toCollection(ArrayList::new))).thenReturn(resp);

        //generate response
        WrappedCollection<DtoDataTag> wc = userFacade.findTagsWithMaxCount(user.getId());
        Assertions.assertEquals(resp, wc.getItems());
    }
}