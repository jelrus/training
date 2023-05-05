package com.epam.esm.model.dao.impl;

import com.epam.esm.ConnectionConfigTest;
import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;
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
import java.util.*;

import static com.epam.esm.model.dao.suppliers.DaoSupplier.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConnectionConfigTest.class)
public class GiftCertificateDaoImplTest {

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
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        Assertions.assertTrue(giftCertificateDao.existById(id));
    }

    @Test
    public void willFindById() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        Assertions.assertTrue(giftCertificateDao.existById(id));
        Assertions.assertEquals(giftCertificate, giftCertificateDao.findById(id));
    }

    @Test
    public void wilUpdate() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        GiftCertificate giftCertificateUpdate = generateGiftCertificateUpdate();
        giftCertificateUpdate.setId(id);
        giftCertificateDao.update(giftCertificateUpdate);
        Assertions.assertTrue(giftCertificateDao.existById(id));
        Assertions.assertEquals(giftCertificateUpdate, giftCertificateDao.findById(id));
        Assertions.assertNotEquals(giftCertificate, giftCertificateDao.findById(id));
    }

    @Test
    public void wilDelete() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        giftCertificateDao.delete(id);
        Assertions.assertFalse(giftCertificateDao.existById(id));
    }

    @Test
    public void willFindAll() {
        Set<GiftCertificate> giftCertificates = generateGiftCertificates(20, 2);
        addGiftCertsWithTagsToDb(giftCertificates, giftCertificateDao, tagDao);
        Assertions.assertFalse(giftCertificateDao.findAll().isEmpty());
        Assertions.assertEquals(giftCertificates.size(), giftCertificateDao.findAll().size());
    }

    @Test
    public void willExistById() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        Assertions.assertTrue(giftCertificateDao.existById(id));
    }

    @Test
    public void willExistByName() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        giftCertificateDao.create(giftCertificate);
        Assertions.assertTrue(giftCertificateDao.existByName(giftCertificate.getName()));
    }

    @Test
    public void willFindByName() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        Assertions.assertTrue(giftCertificateDao.existByName(giftCertificate.getName()));
        Assertions.assertEquals(giftCertificate, giftCertificateDao.findByName(giftCertificate.getName()));
    }

    @Test
    public void willFindAllTagged() {
        Set<GiftCertificate> giftCertificates = generateGiftCertificates(20, 2);
        addGiftCertsWithTagsToDb(giftCertificates, giftCertificateDao, tagDao);
        Set<GiftCertificate> found = giftCertificateDao.findAllTagged();
        found.forEach(x -> x.setTags(giftCertificateDao.findTags(x.getId())));
        Assertions.assertEquals(giftCertificateDao.findAll().size(), giftCertificateDao.findAllTagged().size()*2);
    }

    @Test
    public void willFindAllSearch() {
        Set<GiftCertificate> giftCertificates = generateGiftCertificates(20, 2);
        addGiftCertsWithTagsToDb(giftCertificates, giftCertificateDao, tagDao);
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setFullSearch(new LinkedHashMap<String, String>(){{put("t.name", "'name1'");}});
        spReq.setPartSearch(new LinkedHashMap<String, String>(){{put("gc.name","'Test'");}});
        spReq.setSortParams(new LinkedHashMap<String, String>(){{put("gc.create_date","asc");}});
        SearchParamResponse<GiftCertificate> spResp = giftCertificateDao.findAllSearch(spReq);
        Assertions.assertFalse(spResp.getItems().isEmpty());
    }

    @Test
    public void willFindTags() {
        Set<Tag> tags = generateTagsForCertificates(30);
        GiftCertificate giftCertificate = generateGiftCertificate();
        giftCertificate.setTags(tags);
        Long id = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(id);
        giftCertificate.getTags().forEach(t -> {tagDao.create(t);
                                                giftCertificateDao.addTag(giftCertificate.getId(), t.getId());});
        Assertions.assertEquals(30, giftCertificateDao.findTags(giftCertificate.getId()).size());
    }

    @Test
    public void willAddTag() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long gcId = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(gcId);

        Tag tag = generateTag();
        tag.setName("Test tag");
        Long tId = tagDao.create(tag);
        tag.setId(tId);

        giftCertificateDao.addTag(gcId, tId);

        Assertions.assertTrue(giftCertificateDao.findTags(gcId).contains(tag));
    }

    @Test
    public void willDeleteTag() {
        GiftCertificate giftCertificate = generateGiftCertificate();
        Long gcId = giftCertificateDao.create(giftCertificate);
        giftCertificate.setId(gcId);

        Tag tag = generateTag();
        tag.setName("Test tag");
        Long tId = tagDao.create(tag);
        tag.setId(tId);

        giftCertificateDao.addTag(gcId, tId);
        Assertions.assertTrue(giftCertificateDao.findTags(gcId).contains(tag));

        giftCertificateDao.deleteTag(gcId, tId);
        Assertions.assertFalse(giftCertificateDao.findTags(gcId).contains(tag));
    }
}