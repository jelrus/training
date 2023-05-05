package com.epam.esm.model.dao.impl;

import com.epam.esm.ConnectionConfigTest;
import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static com.epam.esm.model.dao.suppliers.DaoSupplier.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConnectionConfigTest.class)
public class TagDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private Resource setup;

    @BeforeEach
    public void setUp() throws IOException {
        List<String> strings = Files.readAllLines(setup.getFile().toPath());
        StringBuilder sb = new StringBuilder();

        for (String s: strings) {
            sb.append(s);
        }

        jdbcTemplate.execute(sb.toString());
    }

    @Test
    public void willCreate() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        Long id = tagDao.create(tag);
        Assertions.assertTrue(tagDao.existById(id));
    }

    @Test
    public void willFindById() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        Long id = tagDao.create(tag);
        tag.setId(id);
        Assertions.assertTrue(tagDao.existById(id));
        Assertions.assertEquals(tag, tagDao.findById(id));
    }

    @Test
    public void wilDelete() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        Long id = tagDao.create(tag);
        tag.setId(id);
        tagDao.delete(id);
        Assertions.assertFalse(tagDao.existById(id));
    }

    @Test
    public void willFindAll() {
        Set<Tag> tags = generateTags(20, 2);
        addTagsWithGiftCertsToDb(tags, giftCertificateDao, tagDao);
        Assertions.assertFalse(tagDao.findAll().isEmpty());
        Assertions.assertEquals(tags.size(), tagDao.findAll().size());
    }

    @Test
    public void willExistById() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        Long id = tagDao.create(tag);
        tag.setId(id);
        Assertions.assertTrue(tagDao.existById(id));
    }

    @Test
    public void willExistByName() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        tagDao.create(tag);
        Assertions.assertTrue(tagDao.existByName(tag.getName()));
    }

    @Test
    public void willFindByName() {
        Tag tag = generateTag();
        tag.setName("Test Name");
        Long id = tagDao.create(tag);
        tag.setId(id);
        Assertions.assertTrue(tagDao.existByName(tag.getName()));
        Assertions.assertEquals(tag, tagDao.findByName(tag.getName()));
    }

    @Test
    public void willFindAllCertificated() {
        Set<Tag> tags = generateTags(20, 2);
        addTagsWithGiftCertsToDb(tags, giftCertificateDao, tagDao);
        Set<Tag> found = tagDao.findAllCertificated();
        found.forEach(x -> x.setGiftCertificates(tagDao.findGiftCertificates(x.getId())));
        Assertions.assertEquals(tagDao.findAll().size(), tagDao.findAllCertificated().size()*2);
    }

    @Test
    public void willFindGiftCertificates() {
        Set<GiftCertificate> gCerts = generateGiftCertificatesForTags(30);
        Tag tag = generateTag();
        tag.setName("Test name");
        tag.setGiftCertificates(gCerts);
        Long id = tagDao.create(tag);
        tag.setId(id);
        tag.getGiftCertificates().forEach(gc -> giftCertificateDao.addTag(giftCertificateDao.create(gc),
                                                                          tag.getId()));
        Assertions.assertEquals(30, tagDao.findGiftCertificates(tag.getId()).size());
    }
}