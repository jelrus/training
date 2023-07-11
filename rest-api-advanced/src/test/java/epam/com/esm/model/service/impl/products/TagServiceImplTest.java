package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
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

import static epam.com.esm.model.suppliers.service.products.TagServiceSupplier.getProperTag;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @Mock
    private GiftCertificateDao gcDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void willCreateWithExistingGiftCertificates() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tagDao.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create
        when(tagDao.create(t)).thenReturn(t.getId());

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);

        //generate response
        Tag created = tagService.create(t);
        Assertions.assertEquals(t, created);
    }

    @Test
    public void willThrowAlreadyExistsExceptionWithDuplicateNameOnCreate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name fails due duplicate tag name
        when(tagDao.existsByName(t.getName())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistsException.class, () -> tagService.create(t));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingGiftCertificate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tagDao.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name fails due not existing gift certificate
        when(gcDao.existsByName(t.getGiftCertificates().stream().findFirst().get().getName())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.create(t));
    }

    @Test
    public void willThrowOperationFailedExceptionWithCorruptedIdOnCreate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tagDao.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create fails due corrupted id
        when(tagDao.create(t)).thenReturn(-1L);
        Assertions.assertThrows(OperationFailedException.class, () -> tagService.create(t));
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnCreate() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by name
        when(tagDao.existsByName(t.getName())).thenReturn(false);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //create
        when(tagDao.create(t)).thenReturn(t.getId());

        //response generation fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.create(t));
    }

    @Test
    public void willFindById() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);

        //generate response
        Tag found = tagService.findById(t.getId());
        Assertions.assertEquals(t, found);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnFindById() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.findById(t.getId()));
    }

    @Test
    public void willDelete() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);

        //delete
        when(tagDao.delete(t.getId())).thenReturn(true);

        //generate response
        Tag deleted = tagService.delete(t.getId());
        Assertions.assertEquals(t, deleted);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnDelete() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.delete(t.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionIfDeleteFailsOnDelete() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);

        //delete fails due unsuccessful dao delete operation
        when(tagDao.delete(t.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> tagService.delete(t.getId()));
    }

    @Test
    public void willFindAll() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all
        when(tagDao.findAll(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> gcResp = tagService.findAll(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindAllCertificated() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all certificated
        when(tagDao.findAllCertificated(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> gcResp = tagService.findAllCertificated(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindAllNotCertificated() {
        //initial data
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<Tag> spResp = new SearchParamResponse<>();

        //find all not certificated
        when(tagDao.findAllNotCertificated(spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<Tag> gcResp = tagService.findAllNotCertificated(spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willFindGiftCertificates() {
        //initial data
        Tag t = getProperTag();
        SearchParamRequest spReq = new SearchParamRequest();
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //find gift certificates by name
        when(tagDao.findGiftCertificates(t.getId(), spReq)).thenReturn(spResp);

        //generate response
        SearchParamResponse<GiftCertificate> gcResp = tagService.findGiftCertificates(t.getId(), spReq);
        Assertions.assertEquals(spResp, gcResp);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnFindGiftCertificates() {
        //initial data
        Tag t = getProperTag();
        SearchParamRequest spReq = new SearchParamRequest();

        //tag existence check by id fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.findGiftCertificates(t.getId(), spReq));
    }

    @Test
    public void willAddGiftCertificates() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //add gift certificates
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsById(gc.getId())).thenReturn(true);
            when(tagDao.addGiftCertificate(t.getId(), gc.getId())).thenReturn(true);
        }

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);

        //generate response
        Tag withAddedGiftCertificates = tagService.addGiftCertificates(t);
        Assertions.assertEquals(t, withAddedGiftCertificates);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnAddTags() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.addGiftCertificates(t));
    }

    @Test
    public void willDeleteTags() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id
        when(tagDao.existsById(t.getId())).thenReturn(true);

        //gift certificates existence check by name
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsByName(gc.getName())).thenReturn(true);
            when(gcDao.findByName(gc.getName())).thenReturn(gc);
        }

        //delete gift certificates
        for (GiftCertificate gc: t.getGiftCertificates()) {
            when(gcDao.existsById(gc.getId())).thenReturn(true);
            when(tagDao.deleteGiftCertificate(t.getId(), gc.getId())).thenReturn(true);
        }

        //assemble tag
        when(tagDao.findById(t.getId())).thenReturn(t);
        when(tagDao.findGiftCertificates(t.getId())).thenReturn(t.getGiftCertificates());

        //generate response
        Tag withDeletedGiftCertificates = tagService.deleteGiftCertificates(t);
        Assertions.assertEquals(t, withDeletedGiftCertificates);
    }

    @Test
    public void willThrowNotFoundExceptionWithNotExistingTagOnDeleteGiftCertificates() {
        //initial data
        Tag t = getProperTag();

        //tag existence check by id fails due not existing tag
        when(tagDao.existsById(t.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.deleteGiftCertificates(t));
    }
}