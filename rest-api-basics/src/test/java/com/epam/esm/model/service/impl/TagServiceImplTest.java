package com.epam.esm.model.service.impl;

import com.epam.esm.exception.types.AlreadyExistsException;
import com.epam.esm.exception.types.NotFoundException;
import com.epam.esm.exception.types.OperationFailedException;
import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.logger.LoggerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.model.service.suppliers.ServiceObjectsSupplier.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;

    @Mock
    private LoggerService loggerService;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void willCreateWithNullGiftCertificates() {
        Tag tag = generateTagNullGiftCertificates();
        when(!tagDao.existByName(tag.getName())).thenReturn(false);
        when(tagDao.create(tag)).thenReturn(tag.getId());
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        Tag t = tagService.create(tag);
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willCreateWithEmptyGiftCertificates() {
        Tag tag = generateTagEmptySet();
        when(tagDao.create(tag)).thenReturn(tag.getId());
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        Tag t = tagService.create(tag);
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willCreateWithGiftCertificatesContainsExisting() {
        Tag tag = generateTagWithGiftCertificates();
        GiftCertificate giftCertificate = generateGiftCertificate();
        when(tagDao.create(tag)).thenReturn(tag.getId());
        when(giftCertificateDao.existByName(giftCertificate.getName())).thenReturn(true);
        when(giftCertificateDao.findByName(giftCertificate.getName())).thenReturn(giftCertificate);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        Tag t = tagService.create(tag);
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willCreateWithGiftCertificatesContainsNotExisting() {
        Tag tag = generateTagWithGiftCertificates();
        GiftCertificate giftCertificate = generateGiftCertificate();
        when(tagDao.create(tag)).thenReturn(tag.getId());
        when(giftCertificateDao.existByName(giftCertificate.getName())).thenReturn(false);
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        Tag t = tagService.create(tag);
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willThrowAlreadyExistsExceptionOnCreate() {
        Tag tag = generateTagNullGiftCertificates();
        when(!tagDao.existByName(tag.getName())).thenReturn(true);
        when(tagDao.findByName(tag.getName())).thenReturn(tag);
        Assertions.assertThrows(AlreadyExistsException.class, () -> tagService.create(tag));
    }

    @Test
    public void willThrowOperationFailedExceptionIfIdCorruptedCreate() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.create(tag)).thenReturn(0L);
        Assertions.assertThrows(OperationFailedException.class,
                () -> tagService.create(tag));
    }

    @Test
    public void willFindById() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        Tag t = tagService.findById(tag.getId());
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willThrowNotFoundExceptionFindById() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.existById(tag.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> tagService.findById(tag.getId()));
    }

    @Test
    public void willDelete() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        when(tagDao.delete(tag.getId())).thenReturn(true);
        Tag t = tagService.delete(tag.getId());
        Assertions.assertEquals(tag, t);
    }

    @Test
    public void willThrowNotFoundExceptionDelete() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.existById(tag.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.delete(tag.getId()));
    }

    @Test
    public void willThrowOperationFailedExceptionDelete() {
        Tag tag = generateTagNullGiftCertificates();
        when(tagDao.existById(tag.getId())).thenReturn(true);
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        when(tagDao.delete(tag.getId())).thenReturn(false);
        Assertions.assertThrows(OperationFailedException.class,
                                () -> tagService.delete(tag.getId()));
    }

    @Test
    public void willFindAll() {
        Tag tag = generateTag();
        Set<Tag> tags = new LinkedHashSet<>(Collections.singletonList(tag));
        when(tagDao.findAll()).thenReturn(tags);
        Set<Tag> test = tagService.findAll();
        Assertions.assertEquals(tags, test);
    }

    @Test
    public void willFindAllCertificated() {
        Set<Tag> tags = new LinkedHashSet<>();
        when(tagDao.findAllCertificated()).thenReturn(tags);
        Set<Tag> test = tagService.findAllCertificated();
        Assertions.assertEquals(tags, test);
    }
}