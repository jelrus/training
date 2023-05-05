package com.epam.esm.model.service.impl;

import com.epam.esm.ConnectionConfigTest;
import com.epam.esm.exception.types.AlreadyExistsException;
import com.epam.esm.exception.types.NotFoundException;
import com.epam.esm.exception.types.OperationFailedException;
import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.logger.LoggerService;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.model.service.suppliers.ServiceObjectsSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ConnectionConfigTest.class})
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;

    @Mock
    private LoggerService loggerService;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    public void willCreateWithNullTags() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(!giftCertificateDao.existByName(giftCertificate.getName())).thenReturn(false);
        when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate.getId());
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.create(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willCreateWithEmptyTags() {
        GiftCertificate giftCertificate = generateGiftCertificateEmptySet();
        when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate.getId());
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.create(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willCreateWithTagsContainsExistingTag() {
        GiftCertificate giftCertificate = generateGiftCertificateWithTags();
        Tag tag = generateTag();
        when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate.getId());
        when(tagDao.existByName(tag.getName())).thenReturn(true);
        when(tagDao.findByName(tag.getName())).thenReturn(tag);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.create(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willCreateWithTagsContainsNotExistingTag() {
        GiftCertificate giftCertificate = generateGiftCertificateWithTags();
        Tag tag = generateTag();
        when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate.getId());
        when(tagDao.existByName(tag.getName())).thenReturn(false);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.create(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowAlreadyExistsExceptionOnCreate() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(!giftCertificateDao.existByName(giftCertificate.getName())).thenReturn(true);
        when(giftCertificateDao.findByName(giftCertificate.getName())).thenReturn(giftCertificate);
        Assertions.assertThrows(AlreadyExistsException.class, () -> giftCertificateService.create(giftCertificate));
    }

    @Test
    public void willThrowOperationFailedExceptionIfIdCorruptedCreate() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.create(giftCertificate)).thenReturn(0L);
        Assertions.assertThrows(OperationFailedException.class,
                                () -> giftCertificateService.create(giftCertificate));
    }

    @Test
    public void willFindById() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.findById(giftCertificate.getId());
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowNotFoundExceptionFindById() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> giftCertificateService.findById(giftCertificate.getId()));
    }

    @Test
    public void willUpdateWithNullTags() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.update(giftCertificate)).thenReturn(true);
        GiftCertificate gc = giftCertificateService.update(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willUpdateWithEmptyTags() {
        GiftCertificate giftCertificate = generateGiftCertificateEmptySet();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.update(giftCertificate)).thenReturn(true);
        GiftCertificate gc = giftCertificateService.update(giftCertificate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willUpdateWithTagsContainsExistingTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        GiftCertificate giftCertificateUpdate = generateGiftCertificateWithTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existByName(tag.getName())).thenReturn(true);
        when(tagDao.findByName(tag.getName())).thenReturn(tag);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.update(giftCertificateUpdate)).thenReturn(true);
        GiftCertificate gc = giftCertificateService.update(giftCertificateUpdate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willUpdateWithTagsContainsNotExistingTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        GiftCertificate giftCertificateUpdate = generateGiftCertificateWithTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existByName(tag.getName())).thenReturn(false);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.update(giftCertificateUpdate)).thenReturn(true);
        GiftCertificate gc = giftCertificateService.update(giftCertificateUpdate);
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowNotFoundExceptionUpdate() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    public void willThrowOperationFailedExceptionUpdate() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.update(giftCertificate)).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class, () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    public void willDelete() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.delete(giftCertificate.getId())).thenReturn(true);
        GiftCertificate gc = giftCertificateService.delete(giftCertificate.getId());
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowNotFoundExceptionDelete() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                                () -> giftCertificateService.delete(giftCertificate.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionDelete() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(giftCertificateDao.delete(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class,
                                () -> giftCertificateService.delete(giftCertificate.getId()));
    }

    @Test
    public void willFindAll() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Set<GiftCertificate> certs = new LinkedHashSet<>(Collections.singletonList(giftCertificate));
        when(giftCertificateDao.findAll()).thenReturn(certs);
        Set<GiftCertificate> test = giftCertificateService.findAll();
        Assertions.assertEquals(certs, test);
    }

    @Test
    public void willFindAllTagged() {
        Set<GiftCertificate> certs = new LinkedHashSet<>();
        when(giftCertificateDao.findAllTagged()).thenReturn(certs);
        Set<GiftCertificate> test = giftCertificateService.findAllTagged();
        Assertions.assertEquals(certs, test);
    }

    @Test
    public void willFindAllSearch() {
        SearchParamRequest searchParamRequest = generateSearchParamRequest();
        SearchParamResponse<GiftCertificate> searchParamResponse = generateSearchParamResponse();
        when(giftCertificateDao.findAllSearch(searchParamRequest)).thenReturn(searchParamResponse);
        SearchParamResponse<GiftCertificate> gcResp = giftCertificateService.findAllSearch(searchParamRequest);
        Assertions.assertEquals(searchParamResponse, gcResp);
    }

    @Test
    public void willAddTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(giftCertificateDao.addTag(giftCertificate.getId(), tag.getId())).thenReturn(true);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.addTag(giftCertificate.getId(), tag.getId());
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowNotFoundExceptionIfGiftCertificateNotExistsAddTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                                () -> giftCertificateService.addTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    public void willThrowNotFoundExceptionIfTagNotExistsAddTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                                () -> giftCertificateService.addTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionAddTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(giftCertificateDao.addTag(giftCertificate.getId(), tag.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class,
                                () -> giftCertificateService.addTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    public void willDeleteTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(giftCertificateDao.deleteTag(giftCertificate.getId(), tag.getId())).thenReturn(true);
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        GiftCertificate gc = giftCertificateService.deleteTag(giftCertificate.getId(), tag.getId());
        Assertions.assertEquals(giftCertificate, gc);
    }

    @Test
    public void willThrowNotFoundExceptionIfGiftCertificateNotExistsDeleteTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> giftCertificateService.deleteTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    public void willThrowNotFoundExceptionIfTagNotExistsDeleteTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> giftCertificateService.deleteTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionDeleteTag() {
        GiftCertificate giftCertificate = generateGiftCertificateNullTags();
        Tag tag = generateTag();
        when(giftCertificateDao.existById(giftCertificate.getId())).thenReturn(true);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(giftCertificateDao.deleteTag(giftCertificate.getId(), tag.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class,
                () -> giftCertificateService.deleteTag(giftCertificate.getId(), tag.getId()));
    }
}