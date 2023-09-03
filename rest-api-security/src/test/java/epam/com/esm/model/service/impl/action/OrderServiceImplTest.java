package epam.com.esm.model.service.impl.action;

import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.impl.purchase.PurchaseService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.action.OrderRepository;
import epam.com.esm.persistence.repository.impl.products.GiftCertificateRepository;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static epam.com.esm.model.suppliers.service.action.OrderServiceSupplier.getProperOrder;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository oRepo;

    @Mock
    private GiftCertificateRepository gcRepo;

    @Mock
    private BaseCrudRepository<Order, BaseRepository<Order>> baseRepo;

    @Mock
    private PurchaseService purchaseService;

    @InjectMocks
    private OrderServiceImpl oService;

    @Test
    public void willCreate() {
        //initial data
        Order o = getProperOrder();

        //generate purchase data
        when(baseRepo.create(oRepo, o)).thenReturn(o);

        //generate response
        Order created = oService.create(o);
        Assertions.assertEquals(o, created);
    }

    @Test
    public void willFindById() {
        //initial data
        Order o = getProperOrder();

        //assemble order
        when(baseRepo.findById(oRepo, o.getId())).thenReturn(Optional.of(o));

        //generate response
        Order found = oService.findById(o.getId());
        Assertions.assertEquals(o, found);
    }

    @Test
    public void willThrowNotFoundExceptionOnFindById() {
        //null case
        when(baseRepo.findById(oRepo, null)).thenReturn(Optional.empty());

        //throws when id is null
        Assertions.assertThrows(NotFoundException.class, () -> oService.findById(null));
    }

    @Test
    public void willDelete() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(oRepo.existsById(o.getId())).thenReturn(true);

        //delete
        when(baseRepo.delete(oRepo, o.getId())).thenReturn(o);

        //generate response
        Order deleted = oService.delete(o.getId());
        Assertions.assertEquals(o, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionOnDelete() {
        //initial data
        Order o = getProperOrder();

        //order existence check by id
        when(oRepo.existsById(o.getId())).thenReturn(false);

        //throws when not found
        Assertions.assertThrows(NotFoundException.class, () -> oService.delete(o.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();

        //find all
        when(baseRepo.findAll(oRepo, spReq, Order.class, GiftCertificate.class, User.class)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Order> oResp = oService.findAll(spReq);
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willFindGiftCertificates() {
        //initial data
        Order o = getProperOrder();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();
        Page<GiftCertificate> p = new PageImpl<>(Collections.emptyList());

        //order existence check by id
        when(oRepo.existsById(o.getId())).thenReturn(true);

        //find all by id
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any())).thenReturn(p.getContent());
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any(),
                            ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = oService.findGiftCertificates(o.getId(), spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingOrderOnFindGiftCertificates() {
        //initial data
        Order o = getProperOrder();
        SearchParamRequest spReq = new SearchParamRequest();

        //order existence check by id fails due not existing order
        when(oRepo.existsById(o.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> oService.findGiftCertificates(o.getId(), spReq));
    }
}