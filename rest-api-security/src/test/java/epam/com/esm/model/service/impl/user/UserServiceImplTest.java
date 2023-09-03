package epam.com.esm.model.service.impl.user;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.impl.purchase.PurchaseService;
import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.entity.impl.user.component.Role;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.action.OrderRepository;
import epam.com.esm.persistence.repository.impl.products.TagRepository;
import epam.com.esm.persistence.repository.impl.purchase.PurchaseDataRepository;
import epam.com.esm.persistence.repository.impl.user.RoleRepository;
import epam.com.esm.persistence.repository.impl.user.UserRepository;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.utils.statistics.repository.ResultTag;
import epam.com.esm.utils.statistics.service.ObjectDataTag;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static epam.com.esm.model.suppliers.service.user.UserServiceSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository uRepo;

    @Mock
    private OrderRepository oRepo;

    @Mock
    private TagRepository tRepo;

    @Mock
    private PurchaseDataRepository pdRepo;

    @Mock
    private RoleRepository rRepo;

    @Mock
    private BaseCrudRepository<User, UserRepository> baseRepo;

    @Mock
    private PurchaseService pService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void willCreate() {
        //initial data
        User u = getProperUser();
        Role role = new Role("ROLE_USER");

        //user existence check by username
        when(uRepo.existsByUsername(u.getUsername())).thenReturn(false);

        //set roles
        when(rRepo.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        //creating
        when(baseRepo.create(uRepo, u)).thenReturn(u);

        //generate response
        User created = userService.create(u);
        Assertions.assertEquals(u, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        User u = getProperUser();

        //user existence check by username fails due duplicate username
        when(uRepo.existsByUsername(u.getUsername())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> userService.create(u));
    }

    @Test
    public void willThrowNotFoundExceptionWithNullRoleOnCreate() {
        //initial data
        User u = getProperUser();
        Role role = new Role("ROLE_USER");

        //user existence check by username
        when(uRepo.existsByUsername(u.getUsername())).thenReturn(false);

        //set roles
        when(rRepo.findByName(role.getName())).thenReturn(Optional.empty());

        //role existence check by name fails due absence
        Assertions.assertThrows(NotFoundException.class, () -> userService.create(u));
    }

    @Test
    public void willFindById() {
        //initial data
        User u = getProperUser();

        //assemble user
        when(baseRepo.findById(uRepo, u.getId())).thenReturn(Optional.of(u));

        //generate response
        User found = userService.findById(u.getId());
        Assertions.assertEquals(u, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindById() {
        //null case
        when(baseRepo.findById(uRepo, null)).thenReturn(Optional.empty());

        //throws when id is null
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(null));
    }

    @Test
    public void willUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //user existence check by username and comparing ids
        when(uRepo.existsByUsername(u.getUsername())).thenReturn(true);
        when(uRepo.findByUsername(u.getUsername())).thenReturn(Optional.of(u));

        //update
        when(baseRepo.update(uRepo, u)).thenReturn(u);

        //generate response
        User updated = userService.update(u);
        Assertions.assertEquals(u, updated);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserIdOnUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.update(u));
    }

    @Test
    public void willThrowInputExceptionWithExistingUserDifferentIdExistingNameOnUpdate() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //user existence check by username and ids comparing fails due different ids
        when(uRepo.existsByUsername(u.getUsername())).thenReturn(true);
        when(uRepo.findByUsername(u.getUsername())).thenReturn(Optional.of(getAnotherUser()));
        Assertions.assertThrows(InputException.class, () -> userService.update(u));
    }

    @Test
    public void willDelete() {
        //initial data
        User u = getProperUser();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //delete
        when(baseRepo.delete(uRepo, u.getId())).thenReturn(u);

        //generate response
        User deleted = userService.delete(u.getId());
        Assertions.assertEquals(u, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDelete() {
        //initial data
        User u = getProperUser();

        //user existence check by username fails due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.delete(u.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<User> spResp = new SearchParamResponse<>();

        //find all
        when(baseRepo.findAll(uRepo, spReq, User.class, Order.class)).thenReturn(spResp);

        //generate response
        SearchParamResponse<User> oResp = userService.findAll(spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willMakeOrder() {
        //initial data
        User u = getProperUser();
        Order o = getOrder();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find user
        when(uRepo.findById(u.getId())).thenReturn(Optional.of(u));
        when(uRepo.save(u)).thenReturn(u);

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
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.makeOrder(u.getId(), o));
    }

    @Test
    public void willFindOrders() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<Order> spResp = new SearchParamResponse<>();
        Page<Order> p = new PageImpl<>(Collections.emptyList());

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find all by id
        when(oRepo.findAll(ArgumentMatchers.<Specification<Order>>any())).thenReturn(p.getContent());
        when(oRepo.findAll(ArgumentMatchers.<Specification<Order>>any(),
                ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<Order> oResp = userService.findOrders(spReq, u.getId());
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindOrders() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id fails due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findOrders(spReq, u.getId()));
    }

    @Test
    public void willFindPurchases() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<PurchaseData> spResp = new SearchParamResponse<>();
        Page<PurchaseData> p = new PageImpl<>(Collections.emptyList());

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find all by id
        when(pdRepo.findAll(ArgumentMatchers.<Specification<PurchaseData>>any())).thenReturn(p.getContent());
        when(pdRepo.findAll(ArgumentMatchers.<Specification<PurchaseData>>any(),
                ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<PurchaseData> pResp = userService.findPurchases(spReq, u.getId());
        Assertions.assertEquals(spResp.getItems(), pResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindGiftCertificates() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findPurchases(spReq, u.getId()));
    }

    @Test
    public void willFindTags() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();
        Page<Tag> p = new PageImpl<>(Collections.emptyList());

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find all by id
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any())).thenReturn(p.getContent());
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any(),
                           ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<Tag> tResp = userService.findTags(spReq, u.getId());
        Assertions.assertEquals(spResp.getItems(), tResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTags() {
        //initial data
        User u = getProperUser();
        SearchParamRequest spReq = new SearchParamRequest();

        //user existence check by id due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTags(spReq, u.getId()));
    }

    @Test
    public void willFindTagsByPopularity() {
        //initial data
        User u = getProperUser();
        List<ResultTag> tags = new ArrayList<>();
        List<ObjectDataTag> expected = new ArrayList<>();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find tags by popularity
        when(uRepo.findTagsByPopularityAndMaxCost(u.getId())).thenReturn(tags);

        //generate response
        List<ObjectDataTag> res = userService.findTagsByPopularity(u.getId());
        Assertions.assertEquals(expected, res);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTagsByPopularity() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTagsByPopularity(u.getId()));
    }

    @Test
    public void willFindTagsByMaxPopularity() {
        //initial data
        User u = getProperUser();
        List<ResultTag> tags = new ArrayList<>();
        List<ObjectDataTag> expected = new ArrayList<>();

        //user existence check by id
        when(uRepo.existsById(u.getId())).thenReturn(true);

        //find tags with max count
        when(uRepo.findTagsByPopularityAndMaxCost(u.getId())).thenReturn(tags);

        //generate response
        List<ObjectDataTag> res = userService.findTagsWithMaxCount(u.getId());
        Assertions.assertEquals(expected, res);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingUserOnFindTagsByMaxPopularity() {
        //initial data
        User u = getProperUser();

        //user existence check by id due not existing user
        when(uRepo.existsById(u.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findTagsWithMaxCount(u.getId()));
    }
}