package epam.com.esm.model.service.impl.action;

import epam.com.esm.exception.types.EmptyOrderException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.action.OrderDao;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.user.UserDao;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static epam.com.esm.model.suppliers.service.action.OrderServiceSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private UserDao userDao;

    @Mock
    private GiftCertificateDao gcDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void willCreate() {
        //initial data
        Order o = getProperOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create
        when(orderDao.create(o)).thenReturn(o.getId());

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //assemble order
        when(orderDao.findById(o.getId())).thenReturn(o);

        //generate response
        Order created = orderService.create(o);
        Assertions.assertEquals(o, created);
    }

    @Test
    public void willThrowInputExceptionWithNullUserOnCreate() {
        //initial data
        Order o = getOrderNullUser();

        //user existence check by username fails due null user
        Assertions.assertThrows(InputException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnCreate() {
        //initial data
        Order o = getProperOrder();

        //user existence check by username fails due not existing user
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowEmptyOrderExceptionWithNullGiftCertificatesOnCreate() {
        //initial data
        Order o = getNullGiftCertificatesOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name fails due null gift certificates
        Assertions.assertThrows(EmptyOrderException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowEmptyOrderExceptionWithEmptyGiftCertificatesOnCreate() {
        //initial data
        Order o = getEmptyGiftCertificatesOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name fails due empty gift certificates
        Assertions.assertThrows(EmptyOrderException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowInputExceptionWithNullGiftCertificateOnCreate() {
        //initial data
        Order o = getNullGiftCertificateOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificate existence check by name fails due null gift certificate
        Assertions.assertThrows(InputException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnCreate() {
        //initial data
        Order o = getProperOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name fails due not existing gift certificate
        when(gcDao.existsByName(o.getGiftCertificates().stream().findFirst().get().getName())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowOperationFailedExceptionWithCorruptedIdOnCreate() {
        //initial data
        Order o = getProperOrder();

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create operation fails due corrupted id
        when(orderDao.create(o)).thenReturn(-1L);
        Assertions.assertThrows(OperationFailedException.class, () -> orderService.create(o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnCreate() {
        //initial data
        Order o = getProperOrder();

        //user existence check by name
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create
        when(orderDao.create(o)).thenReturn(o.getId());

        //find order operation fails due not existing order
        when(orderDao.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.create(o));
    }

    @Test
    public void willFindById() {
        //initial data
        Order o = getProperOrder();

        //existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //assemble order
        when(orderDao.findById(o.getId())).thenReturn(o);

        //generate response
        Order found = orderService.findById(o.getId());
        Assertions.assertEquals(o, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnFindById() {
        //initial data
        Order o = getProperOrder();

        //find by id operation fails due not existing order
        when(orderDao.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.findById(o.getId()));
    }

    @Test
    public void willUpdate() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by name
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //update
        when(orderDao.update(o)).thenReturn(true);

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //assemble order
        when(orderDao.findById(o.getId())).thenReturn(o);

        //generate response
        Order updated = orderService.update(o);
        Assertions.assertEquals(o, updated);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnUpdate() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id fails due not existing order
        when(orderDao.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.findById(o.getId()));
    }

    @Test
    public void willThrowInputExceptionWithNullUserOnUpdate() {
        //initial data
        Order o = getOrderNullUser();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username fails due null user
        Assertions.assertThrows(InputException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnUpdate() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username fails due not existing user
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowEmptyOrderExceptionWithNullGiftCertificatesOnUpdate() {
        //initial data
        Order o = getNullGiftCertificatesOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name due null gift certificates
        Assertions.assertThrows(EmptyOrderException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowEmptyOrderExceptionWithEmptyGiftCertificatesOnUpdate() {
        //initial data
        Order o = getEmptyGiftCertificatesOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name due empty gift certificates
        Assertions.assertThrows(EmptyOrderException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowInputExceptionWithNullGiftCertificateOnUpdate() {
        //initial data
        Order o = getNullGiftCertificateOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name due null gift certificate
        Assertions.assertThrows(InputException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnUpdate() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name due not existing gift certificate
        when(gcDao.existsByName(o.getGiftCertificates().stream().findFirst().get().getName())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.update(o));
    }

    @Test
    public void willThrowOperationFailedExceptionIfUpdateFailsOnUpdate() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //user existence check by username
        when(userDao.existsByUsername(o.getUser().getUsername())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: o.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //update fails due unsuccessful dao update operation
        when(orderDao.update(o)).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> orderService.update(o));
    }

    @Test
    public void willDelete() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //assemble order
        when(orderDao.findById(o.getId())).thenReturn(o);

        //delete
        when(orderDao.delete(o.getId())).thenReturn(true);

        //generate response
        Order deleted = orderService.delete(o.getId());
        Assertions.assertEquals(o, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnDelete() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id fails due not existing order
        when(orderDao.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.delete(o.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionIfDeleteFailsOnDelete() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //assemble order
        when(orderDao.findById(o.getId())).thenReturn(o);

        //delete fails due unsuccessful dao delete operation
        when(orderDao.delete(o.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> orderService.delete(o.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();

        //find all
        when(orderDao.findAll(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Order> oResp = orderService.findAll(spReq);
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willFindGiftCertificates() {
        //initial data
        Order o = getProperOrder();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //order existence check by id
        when(orderDao.existsById(o.getId())).thenReturn(true);

        //find gift certificates
        when(orderDao.findGiftCertificates(o.getId(), spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = orderService.findGiftCertificates(o.getId(), spReq);
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnFindGiftCertificates() {
        //initial data
        Order o = getProperOrder();
        SearchParamRequest spReq = new SearchParamRequest();

        //order existence check by id fails due not existing order
        when(orderDao.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.findGiftCertificates(o.getId(), spReq));
    }
}