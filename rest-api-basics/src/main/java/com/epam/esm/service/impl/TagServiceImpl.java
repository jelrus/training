package com.epam.esm.service.impl;

import com.epam.esm.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.dao.interfaces.entity.TagDao;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.entity.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class TagServiceImpl implements TagService {

    private final GiftCertificateDao gcDao;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(GiftCertificateDao gcDao, TagDao tagDao) {
        this.gcDao = gcDao;
        this.tagDao = tagDao;
    }

    @Override
    public Boolean create(Tag tag) {
        Long tagId = -1L;

        if (!tagDao.existByName(tag.getName())) {
            tagId = tagDao.create(tag);

            if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
                tag.getGiftCertificates().forEach(addGiftCertificates(tagDao.findById(tagId)));
            }
        }

        return tagId > 0;
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = new Tag();

        if (tagDao.existById(id)) {
            tag = tagDao.findById(id);
            tag.setGiftCertificates(tagDao.findGiftCertificatesByTag(id));
        }

        return tag;
    }

    @Override
    public Boolean delete(Long id) {
        if (tagDao.existById(id)) {
            tagDao.delete(id);
        }
        return !tagDao.existById(id);
    }

    @Override
    public Set<Tag> findAll() {
        Set<Tag> tags = tagDao.findAll();
        tags.forEach(t -> t.setGiftCertificates(tagDao.findGiftCertificatesByTag(t.getId())));
        return tags;
    }

    @Override
    public Set<Tag> findAllCertificated() {
        Set<Tag> tags = tagDao.findAllCertificated();
        tags.forEach(t -> t.setGiftCertificates(tagDao.findGiftCertificatesByTag(t.getId())));
        return tags;
    }

    @Override
    public Set<GiftCertificate> findGiftCertificatesByTag(Long tagId) {
        return tagDao.existById(tagId) ?
               tagDao.findGiftCertificatesByTag(tagId) :
               Collections.emptySet();
    }

    @Override
    public Boolean addGiftCertificate(Long tagId, Long giftCertificateId) {
        return tagDao.addGiftCertificate(tagId, giftCertificateId);
    }

    @Override
    public Boolean deleteGiftCertificate(Long tagId, Long giftCertificateId) {
        return tagDao.deleteGiftCertificate(tagId, giftCertificateId);
    }

    private Consumer<GiftCertificate> addGiftCertificates(Tag tag) {
        return gc -> {
            if (gc != null && gcDao.existByName(gc.getName())) {
                tagDao.addGiftCertificate(tag.getId(), gcDao.findByName(gc.getName()).getId());
            } else {
                tagDao.addGiftCertificate(tag.getId(), gcDao.create(gc));
            }
        };
    }
}