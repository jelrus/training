package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.products.TagDao;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static epam.com.esm.model.suppliers.service.products.GiftCertificateServiceSupplier.getAnotherProperGiftCertificate;
import static epam.com.esm.model.suppliers.service.products.GiftCertificateServiceSupplier.getProperGiftCertificate;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao gcDao;

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private GiftCertificateServiceImpl gcService;

    @Test
    public void willCreateWithExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcDao.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //create
        when(gcDao.create(gc)).thenReturn(gc.getId());

        //assemble gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(true);
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate created = gcService.create(gc);
        Assertions.assertEquals(gc, created);
    }

    @Test
    public void willCreateWithNotExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcDao.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(false);
        }

        //create
        when(gcDao.create(gc)).thenReturn(gc.getId());

        //assemble gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(true);
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate created = gcService.create(gc);
        Assertions.assertEquals(gc, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name due duplicate gift certificate name
        when(gcDao.existsByName(gc.getName())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> gcService.create(gc));
    }

    @Test
    public void willThrowOperationFailedExceptionWithCorruptedIdOnCreate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcDao.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //create operation fails due corrupted id
        when(gcDao.create(gc)).thenReturn(-1L);
        Assertions.assertThrows(OperationFailedException.class, () -> gcService.create(gc));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnCreate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by name
        when(gcDao.existsByName(gc.getName())).thenReturn(false);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //create
        when(gcDao.create(gc)).thenReturn(gc.getId());

        //response generation fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.create(gc));
    }

    @Test
    public void willFindById() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //assemble gift certificate by name
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate found = gcService.findById(gc.getId());
        Assertions.assertEquals(gc, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnFindById() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.findById(gc.getId()));
    }

    @Test
    public void willUpdateWithExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing
        when(gcDao.existsByName(gc.getName())).thenReturn(true);
        when(gcDao.findByName(gc.getName())).thenReturn(gc);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //update
        when(gcDao.update(gc)).thenReturn(true);

        //assemble gift certificate by id
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate updated = gcService.update(gc);
        Assertions.assertEquals(gc, updated);
    }

    @Test
    public void willUpdateWithNotExistingTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing
        when(gcDao.existsByName(gc.getName())).thenReturn(true);
        when(gcDao.findByName(gc.getName())).thenReturn(gc);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(false);
        }

        //update
        when(gcDao.update(gc)).thenReturn(true);

        //assemble gift certificate
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate updated = gcService.update(gc);
        Assertions.assertEquals(gc, updated);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateIdOnUpdate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.update(gc));
    }

    @Test
    public void willThrowInputExceptionWithExistingGiftCertificateDifferentIdExistingNameOnUpdate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing fails due different ids
        when(gcDao.existsByName(gc.getName())).thenReturn(true);
        when(gcDao.findByName(gc.getName())).thenReturn(getAnotherProperGiftCertificate());
        Assertions.assertThrows(InputException.class, () -> gcService.update(gc));
    }

    @Test
    public void willThrowOperationFailedExceptionIfUpdateFailsOnUpdate() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //gift certificate existence check by name and ids comparing fails due different ids
        when(gcDao.existsByName(gc.getName())).thenReturn(true);
        when(gcDao.findByName(gc.getName())).thenReturn(gc);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //update fails due unsuccessful dao update operation
        when(gcDao.update(gc)).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> gcService.update(gc));
    }

    @Test
    public void willDelete() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //assemble gift certificate
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //delete
        when(gcDao.delete(gc.getId())).thenReturn(true);

        //generate response
        GiftCertificate deleted = gcService.delete(gc.getId());
        Assertions.assertEquals(gc, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDelete() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.delete(gc.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionIfDeleteFailsOnDelete() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //assemble gift certificate
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //deleting fails due unsuccessful dao delete
        when(gcDao.delete(gc.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> gcService.delete(gc.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find all
        when(gcDao.findAll(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> gcResp = gcService.findAll(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindAllTagged() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find all tagged
        when(gcDao.findAllTagged(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> gcResp = gcService.findAllTagged(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindAllNotTagged() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //find all not tagged
        when(gcDao.findAllNotTagged(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> gcResp = gcService.findAllNotTagged(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //find tags
        when(gcDao.findTags(gc.getId(), spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> gcResp = gcService.findTags(gc.getId(), spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnFindTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();
        SearchParamRequest spReq = new SearchParamRequest();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.findTags(gc.getId(), spReq));
    }

    @Test
    public void willAddTagsIfExist() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //add tags
        for (Tag t: gc.getTags()) {
            when(tagDao.existsById(t.getId())).thenReturn(true);
            when(gcDao.addTag(gc.getId(), t.getId())).thenReturn(true);
        }

        //assemble gift certificate
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate withAddedTags = gcService.addTags(gc);
        Assertions.assertEquals(gc, withAddedTags);
    }

    @Test
    public void willAddTagsIfNotExist() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //add tags
        for (Tag t: gc.getTags()) {
            when(tagDao.existsById(t.getId())).thenReturn(false);
            when(tagDao.create(t)).thenReturn(t.getId());
            when(gcDao.addTag(gc.getId(), t.getId())).thenReturn(true);
        }

        //assemble gift certificate
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate withAddedTags = gcService.addTags(gc);
        Assertions.assertEquals(gc, withAddedTags);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnAddTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.addTags(gc));
    }

    @Test
    public void willDeleteTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id
        when(gcDao.existsById(gc.getId())).thenReturn(true);

        //tags existence check by name
        for (Tag t: gc.getTags()) {
            when(tagDao.existsByName(t.getName())).thenReturn(true);
            when(tagDao.findByName(t.getName())).thenReturn(t);
        }

        //delete tags
        for (Tag t: gc.getTags()) {
            when(tagDao.existsById(t.getId())).thenReturn(true);
            when(gcDao.findTags(gc.getId())).thenReturn(gc.getTags());
            when(gcDao.deleteTag(gc.getId(), t.getId())).thenReturn(true);
        }

        //assemble gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(true);
        when(gcDao.findById(gc.getId())).thenReturn(gc);

        //generate response
        GiftCertificate withDeletedTags = gcService.deleteTags(gc);
        Assertions.assertEquals(gc, withDeletedTags);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificateOnDeleteTags() {
        //initial data
        GiftCertificate gc = getProperGiftCertificate();

        //gift certificate existence check by id fails due not existing gift certificate
        when(gcDao.existsById(gc.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> gcService.deleteTags(gc));
    }
}