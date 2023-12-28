package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
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

import static epam.com.esm.model.suppliers.service.products.GiftCertificateServiceSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository gcRepo;

    @Mock
    private TagRepository tRepo;

    @Mock
    private BaseCrudRepository<GiftCertificate, BaseRepository<GiftCertificate>> baseRepo;

    @InjectMocks
    private GiftCertificateServiceImpl gcService;

    @Test
    public void willCreateWithExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcRepo.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
        }

        //create
        when(baseRepo.create(gcRepo, gc)).thenReturn(gc);

        //generate response
        GiftCertificate created = gcService.create(gc);
        Assertions.assertEquals(gc, created);
    }

    @Test
    public void willCreateWithNotExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcRepo.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(false);
        }

        //create
        when(baseRepo.create(gcRepo, gc)).thenReturn(gc);

        //generate response
        GiftCertificate created = gcService.create(gc);
        Assertions.assertEquals(gc, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name due duplicate gift certificate name
        when(gcRepo.existsByName(gc.getName())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> gcService.create(gc));
    }

    @Test
    public void willFindById() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //assemble gift certificate by name
        when(baseRepo.findById(gcRepo, gc.getId())).thenReturn(Optional.of(gc));

        //generate response
        GiftCertificate found = gcService.findById(gc.getId());
        Assertions.assertEquals(gc, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnFindById() {
        //null case
        when(baseRepo.findById(gcRepo, null)).thenReturn(Optional.empty());

        //gift certificate existence check by id fails due not existing gift certificate
        Assertions.assertThrows(NotFoundException.class, () -> gcService.findById(null));
    }

    @Test
    public void willUpdateWithExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing
        when(gcRepo.existsByName(gc.getName())).thenReturn(true);
        when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(gc));

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
        }

        //update
        when(baseRepo.update(gcRepo, gc)).thenReturn(gc);

        //generate response
        GiftCertificate updated = gcService.update(gc);
        Assertions.assertEquals(gc, updated);
    }

    @Test
    public void willUpdateWithNotExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing
        when(gcRepo.existsByName(gc.getName())).thenReturn(true);
        when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(gc));

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(false);
        }

        //update
        when(baseRepo.update(gcRepo, gc)).thenReturn(gc);

        //generate response
        GiftCertificate updated = gcService.update(gc);
        Assertions.assertEquals(gc, updated);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateIdOnUpdate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.update(gc));
    }

    @Test
    public void willThrowInputExceptionWithExistingGiftCertificateDifferentIdExistingNameOnUpdate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing fails due different ids
        when(gcRepo.existsByName(gc.getName())).thenReturn(true);
        when(gcRepo.findByName(gc.getName())).thenReturn(Optional.of(getAnotherProperGiftCertificate()));
        Assertions.assertThrows(InputException.class, () -> gcService.update(gc));
    }

    @Test
    public void willDelete() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //delete
        when(baseRepo.delete(gcRepo, gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate deleted = gcService.delete(gc.getId());
        Assertions.assertEquals(gc, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDelete() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.delete(gc.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find all
        when(baseRepo.findAll(gcRepo, spReq, GiftCertificate.class, Tag.class)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = gcService.findAll(spReq);
        Assertions.assertEquals(spResp, oResp);
    }

    @Test
    public void willFindAllTagged() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();
        Page<GiftCertificate> p = new PageImpl<>(Collections.emptyList());

        //find all
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any())).thenReturn(p.getContent());
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any(),
                            ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = gcService.findAllTagged(spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willFindAllNotTagged() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();
        Page<GiftCertificate> p = new PageImpl<>(Collections.emptyList());

        //find all
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any())).thenReturn(p.getContent());
        when(gcRepo.findAll(ArgumentMatchers.<Specification<GiftCertificate>>any(),
                ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<GiftCertificate> oResp = gcService.findAllNotTagged(spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willFindTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setPage(0);
        spReq.setSize(10);
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();
        Page<Tag> p = new PageImpl<>(Collections.emptyList());

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //find tags by id
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any())).thenReturn(p.getContent());
        when(tRepo.findAll(ArgumentMatchers.<Specification<Tag>>any(),
                ArgumentMatchers.<PageRequest>any())).thenReturn(p);

        //generate response
        SearchParamResponse<Tag> oResp = gcService.findTags(gc.getId(), spReq);
        Assertions.assertEquals(spResp.getItems(), oResp.getItems());
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnFindTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        SearchParamRequest spReq = new SearchParamRequest();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.findTags(gc.getId(), spReq));
    }

    @Test
    public void willAddTagsIfExist() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificate gc2 = getProperGiftCertificateAnotherTags();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc2.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
        }

        for (Tag t: gc2.getTags()) {
            when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));
            when(tRepo.existsByName(t.getName())).thenReturn(true);
        }

        //assemble gift certificate
        when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));

        //generate response
        GiftCertificate withAddedTags = gcService.addTags(gc2);
        Assertions.assertEquals(gc, withAddedTags);
    }

    @Test
    public void willAddTagsIfNotExist() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificate gc2 = getProperGiftCertificateAnotherTags();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc2.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
        }

        for (Tag t: gc2.getTags()) {
            when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));
            when(tRepo.existsByName(t.getName())).thenReturn(true);
        }

        //assemble gift certificate
        when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));

        //generate response
        GiftCertificate withAddedTags = gcService.addTags(gc2);
        Assertions.assertEquals(gc, withAddedTags);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnAddTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check fails due not existing gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.addTags(gc));
    }

    @Test
    public void willDeleteTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        GiftCertificate gc2 = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcRepo.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
        }

        for (Tag t: gc.getTags()) {
            when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));
            when(tRepo.existsByName(t.getName())).thenReturn(true);
            when(tRepo.findByName(t.getName())).thenReturn(Optional.of(t));
            when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));
        }

        //assemble gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(true);
        when(gcRepo.findById(gc.getId())).thenReturn(Optional.of(gc));

        //generate response
        GiftCertificate withDeletedTags = gcService.deleteTags(gc2);
        Assertions.assertEquals(gc, withDeletedTags);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDeleteTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcRepo.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.deleteTags(gc));
    }
}