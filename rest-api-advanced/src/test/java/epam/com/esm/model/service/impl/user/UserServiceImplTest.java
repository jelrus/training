package epam.com.esm.model.service.impl.user;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.action.OrderDao;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.user.UserDao;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.dao.ResultTag;
import epam.com.esm.utils.statistics.service.ObjectDataTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static epam.com.esm.model.suppliers.service.user.UserServiceSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private GiftCertificateDao gcDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void willCreate() {
        //initial data
        User u = getProperUser();

        //user existence check by username
        when(userDao.existsByUsername(u.getUsername())).thenReturn(false);

        //creating
        when(userDao.create(u)).thenReturn(u.getId());

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //generate response
        User created = userService.create(u);
        Assertions.assertEquals(u, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        User u = getProperUser();

        //user existence check by username fails due duplicate username
        when(userDao.existsByUsername(u.getUsername())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> userService.create(u));
    }

    @Test
    public void willThrowOperationFailedExceptionWithCorruptedIdOnCreate() {
        //initial data
        User u = getProperUser();

        //user existence check by username
        when(userDao.existsByUsername(u.getUsername())).thenReturn(false);

        //creating fails due dao create unsuccessful operation
        when(userDao.create(u)).thenReturn(-1L);
        Assertions.assertThrows(OperationFailedException.class, () -> userService.create(u));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnCreate() {
        //initial data
        User u = getProperUser();

        //user existence check by username
        when(userDao.existsByUsername(u.getUsername())).thenReturn(false);

        //creating
        when(userDao.create(u)).thenReturn(u.getId());

        //check user for existence by id fails due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.create(u));
    }

    @Test
    public void willFindById() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //generate response
        User found = userService.findById(u.getId());
        Assertions.assertEquals(u, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindById() {
        //initial data
        User u = getProperUser();

        //user existence check by id fails due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(u.getId()));
    }

    @Test
    public void willUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //user existence check by username and comparing ids
        when(userDao.existsByUsername(u.getUsername())).thenReturn(true);
        when(userDao.findByUsername(u.getUsername())).thenReturn(u);

        //update
        when(userDao.update(u)).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //generate response
        User updated = userService.update(u);
        Assertions.assertEquals(u, updated);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserIdOnUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.update(u));
    }

    @Test
    public void willThrowInputExceptionWithExistingUserDifferentIdExistingNameOnUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //user existence check by username and ids comparing fails due different ids
        when(userDao.existsByUsername(u.getUsername())).thenReturn(true);
        when(userDao.findByUsername(u.getUsername())).thenReturn(getAnotherUser());
        Assertions.assertThrows(InputException.class, () -> userService.update(u));
    }

    @Test
    public void willThrowOperationFailedExceptionIfUpdateFailsOnUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //user existence check by username and ids comparing fails due different ids
        when(userDao.existsByUsername(u.getUsername())).thenReturn(true);
        when(userDao.findByUsername(u.getUsername())).thenReturn(u);

        //update fails due unsuccessful dao update operation
        when(userDao.update(u)).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> userService.update(u));
    }

    @Test
    public void willDelete() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //delete
        when(userDao.delete(u.getId())).thenReturn(true);

        //generate response
        User deleted = userService.delete(u.getId());
        Assertions.assertEquals(u, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDelete() {
        //initial data
        User u = getProperUser();

        //user existence check by username fails due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.delete(u.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionIfDeleteFailsOnDelete() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //delete fails due unsuccessful dao delete operation
        when(userDao.delete(u.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> userService.delete(u.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<User> spResp = new SearchParamResponse<>();

        //find all
        when(userDao.findAll(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<User> uResp = userService.findAll(spReq);
        Assertions.assertEquals(spResp, uResp);
    }

    @Test
    public void willMakeOrder() {
        //initial data
        User u = getProperUser();
        Order o = getOrder();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //order's gift certificates existence check by name
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create order
        when(userDao.makeOrder(u.getId(), orderDao.create(o))).thenReturn(true);

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //assemble user
        when(userDao.findById(u.getId())).thenReturn(u);

        //generate response
        User userWithCreateOrder = userService.makeOrder(u.getId(), o);
        Assertions.assertEquals(u, userWithCreateOrder);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnMakeOrder() {
        //initial data
        User u = getProperUser();
        Order o = getOrder();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.makeOrder(u.getId(), o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnMakeOrder() {
        //initial data
        User u = getProperUser();
        Order o = getOrder();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //order's gift certificates existence check by name fails due not existing gift certificate
        when(gcDao.existsByName(o.getGiftCertificates().stream().findFirst().get().getName())).thenReturn(true);
        Assertions.assertThrows(NotFoundException.class, () -> userService.makeOrder(u.getId(), o));
    }

    @Test
    public void ThrowOperationFailedExceptionIfMakeOrderFails() {
        //initial data
        User u = getProperUser();
        Order o = getOrder();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //order's gift certificates existence check by name
        for (GiftCertificate gc : o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create order fails due unsuccessful dao make order operation
        when(userDao.makeOrder(u.getId(), orderDao.create(o))).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> userService.makeOrder(u.getId(), o));
    }

    @Test
    public void willFindOrders() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //find orders
        when(userDao.findOrders(spReq, u.getId())).thenReturn(spResp);

        //generate response
        SearchParamResponse<Order> oResp = userService.findOrders(spReq, u.getId());
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindOrders() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id fails due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findOrders(spReq, u.getId()));
    }

    @Test
    public void willFindGiftCertificates() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<PurchaseData> spResp = new SearchParamResponse<>();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //find gift certificates
        when(userDao.findPurchases(spReq, u.getId())).thenReturn(spResp);

        //generate response
        SearchParamResponse<PurchaseData> gcResp = userService.findPurchases(spReq, u.getId());
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindGiftCertificates() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findPurchases(spReq, u.getId()));
    }

    @Test
    public void willFindTags() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //find tags
        when(userDao.findTags(spReq, u.getId())).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> tResp = userService.findTags(spReq, u.getId());
        Assertions.assertEquals(spResp, tResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTags() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTags(spReq, u.getId()));
    }

    @Test
    public void willFindTagsByPopularity() {
        //initial data
        User u = getProperUser();
        List<ResultTag> tags = new ArrayList<>();
        List<ObjectDataTag> expected = new ArrayList<>();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //find tags by popularity
        when(userDao.findTagsByPopularity(u.getId())).thenReturn(tags);

        //generate response
        List<ObjectDataTag> res = userService.findTagsByPopularity(u.getId());
        Assertions.assertEquals(expected, res);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTagsByPopularity() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTagsByPopularity(u.getId()));
    }

    @Test
    public void willFindTagsByMaxPopularity() {
        //initial data
        User u = getProperUser();
        List<ResultTag> tags = new ArrayList<>();
        List<ObjectDataTag> expected = new ArrayList<>();

        //user existence check by id
        when(userDao.existsById(u.getId())).thenReturn(true);

        //find tags with max count
        when(userDao.findTagsWithMaxCount(u.getId())).thenReturn(tags);

        //generate response
        List<ObjectDataTag> res = userService.findTagsWithMaxCount(u.getId());
        Assertions.assertEquals(expected, res);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTagsByMaxPopularity() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(userDao.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTagsWithMaxCount(u.getId()));
    }
}