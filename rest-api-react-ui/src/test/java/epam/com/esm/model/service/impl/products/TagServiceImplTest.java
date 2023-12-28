package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.repository.BaseRepository;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.products.GiftCertificateRepository;
import epam.com.esm.persistence.repository.impl.products.TagRepository;
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

import static epam.com.esm.model.suppliers.service.products.TagServiceSupplier.getProperTag;
import static epam.com.esm.model.suppliers.service.products.TagServiceSupplier.getProperTagAnotherGiftCertificates;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagRepository tRepo;

    @Mock
    private GiftCertificateRepository gcRepo;

    @Mock
    private BaseCrudRepository<Tag, BaseRepository<Tag>> baseRepo;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void willCreateWithExistingGiftCertificates() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tRepo.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcRepo.existsByName(gc.getName())).thenReturn(true);
            when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(gc));
        }

        //create
        when(baseRepo.create(tRepo, t)).thenReturn(t);

        //generate response
        Tag created = tagService.create(t);
        Assertions.assertEquals(t, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name fails due duplicate tag name
        when(tRepo.existsByName(t.getName())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> tagService.create(t));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tRepo.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name fails due not existing gift certificate
        when(gcRepo.existsByName(t.getGiftCertificates().get(0).getName())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.create(t));
    }

    @Test
    public void willFindById() {
        //initial data
        Tag t = getProperTag();

        //assemble tag
        when(baseRepo.findById(tRepo, t.getId())).thenReturn(Optional.of(t));

        //generate response
        Tag found = tagService.findById(t.getId());
        Assertions.assertEquals(t, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnFindById() {
        //null case
        when(baseRepo.findById(tRepo, null)).thenReturn(Optional.empty());

        //tag existence check by id fails due not existing tag
        Assertions.assertThrows(NotFoundException.class, () -> tagService.findById(null));
    }

    @Test
    public void willDelete() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tRepo.existsById(t.getId())).thenReturn(true);

        //delete
        when(baseRepo.delete(tRepo, t.getId())).thenReturn(t);

        //generate response
        Tag deleted = tagService.delete(t.getId());
        Assertions.assertEquals(t, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnDelete() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tRepo.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.delete(t.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all
        when(baseRepo.findAll(tRepo, spReq, Tag.class, GiftCertificate.class)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> oResp = tagService.findAll(spReq);
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willFindAllCertificated() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();
        Page<Tag> p = new PageImpl<>(Collections.emptyList());

        //find all certificated
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any())).thenReturn(p.getContent());
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any(),
                           ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<Tag> tResp = tagService.findAllCertificated(spReq);
        Assertions.assertEquals(spResp.getItems(), tResp.getItems());
    }

    @Test
    public void willFindAllNotCertificated() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();
        Page<Tag> p = new PageImpl<>(Collections.emptyList());

        //find all not certificated
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any())).thenReturn(p.getContent());
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any(),
                ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<Tag> tResp = tagService.findAllNotCertificated(spReq);
        Assertions.assertEquals(spResp.getItems(), tResp.getItems());
    }

    @Test
    public void willFindGiftCertificates() {
        //initial data
        Tag tag = getProperTag();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();
        Page<GiftCertificate> p = new PageImpl<>(Collections.emptyList());

        //tag existence check by id
        when(tRepo.existsById(tag.getId())).thenReturn(true);

        //find gift certificate by id
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any())).thenReturn(p.getContent());
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any(),
                            ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = tagService.findGiftCertificates(tag.getId(), spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnFindGiftCertificates() {
        //initial data
        Tag t = getProperTag();
        SearchParamRequest spReq = new SearchParamRequest();

        //tag existence check by id fails due not existing tag
        when(tRepo.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.findGiftCertificates(t.getId(), spReq));
    }

    @Test
    public void willAddGiftCertificates() {
        //initial data
        Tag t = getProperTag();
        Tag t2 = getProperTagAnotherGiftCertificates();

        //tag existence check by id
        when(tRepo.existsById(t.getId())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: t2.getGiftCertificates()) {
            when(gcRepo.existsByName(gc.getName())).thenReturn(true);
            when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(gc));
        }

        //add gift certificates
        for (GiftCertificate gc: t2.getGiftCertificates()) {
            when(tRepo.findById(t.getId())).thenReturn(Optional.of(t));
            when(gcRepo.existsById(gc.getId())).thenReturn(true);
        }

        //assemble tag
        when(tRepo.findById(t.getId())).thenReturn(Optional.of(t));

        //generate response
        Tag withAddedGiftCertificates = tagService.addGiftCertificates(t2);
        Assertions.assertEquals(t, withAddedGiftCertificates);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnAddTags() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tRepo.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.addGiftCertificates(t));
    }

    @Test
    public void willDeleteTags() {
        //initial data
        Tag t = getProperTag();
        Tag t2 = getProperTag();

        //tag existence check by id
        when(tRepo.existsById(t.getId())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcRepo.existsByName(gc.getName())).thenReturn(true);
            when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(gc));
        }

        //delete gift certificates
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(tRepo.findById(t.getId())).thenReturn(Optional.of(t));
            when(gcRepo.existsById(gc.getId())).thenReturn(true);
            when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));
            when(tRepo.findById(t.getId())).thenReturn(Optional.of(t));
        }

        //assemble tag
        when(tRepo.existsById(t.getId())).thenReturn(true);
        when(tRepo.findById(t.getId())).thenReturn(Optional.of(t));

        //generate response
        Tag withRemovedGiftCertificates = tagService.deleteGiftCertificates(t2);
        Assertions.assertEquals(t, withRemovedGiftCertificates);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnDeleteGiftCertificates() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tRepo.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.deleteGiftCertificates(t));
    }
}