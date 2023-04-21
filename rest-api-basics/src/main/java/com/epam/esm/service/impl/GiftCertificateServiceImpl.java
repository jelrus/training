package com.epam.esm.service.impl;

import com.epam.esm.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.dao.interfaces.entity.TagDao;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import com.epam.esm.utils.search.dao.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao gcDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao gcDao, TagDao tagDao) {
        this.gcDao = gcDao;
        this.tagDao = tagDao;
    }

    @Override
    public Boolean create(GiftCertificate gCert) {
        Long gCertId = -1L;

        if (!gcDao.existByName(gCert.getName())) {
            gCertId = gcDao.create(gCert);

            if (gCert.getTags() != null && !gCert.getTags().isEmpty()) {
                gCert.getTags().forEach(addTags(gcDao.findById(gCertId)));
            }
        }

        return gCertId > 0;
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate gCert = new GiftCertificate();

        if (gcDao.existById(id)) {
            gCert = gcDao.findById(id);
            gCert.setTags(gcDao.findTagsByGiftCertificate(id));
        }

        return gCert;
    }

    @Override
    public Boolean update(GiftCertificate update) {
        if (gcDao.existById(update.getId()) && update.getTags() != null) {
            GiftCertificate origin = getReplica(update);
            GiftCertificate refined = refineUpdate(update);

            if (update.getTags().isEmpty()) {
                origin.getTags().forEach(t -> gcDao.deleteTag(update.getId(), t.getId()));
            } else {
                refined.getTags().stream().filter(t -> !origin.getTags().contains(t)).forEach(addTags(refined));
                origin.getTags().stream().filter(t -> !refined.getTags().contains(t)).forEach(deleteTags(refined));
            }
        }

        return gcDao.update(update);
    }

    @Override
    public Boolean delete(Long id) {
        if (gcDao.existById(id)) {
            gcDao.delete(id);
        }
        return !gcDao.existById(id);
    }

    @Override
    public Set<GiftCertificate> findAll() {
        Set<GiftCertificate> giftCerts = gcDao.findAll();
        giftCerts.forEach(gc -> gc.setTags(gcDao.findTagsByGiftCertificate(gc.getId())));
        return giftCerts;
    }

    @Override
    public Set<GiftCertificate> findAllTagged() {
        Set<GiftCertificate> giftCerts = gcDao.findAllTagged();
        giftCerts.forEach(gc -> gc.setTags(gcDao.findTagsByGiftCertificate(gc.getId())));
        return giftCerts;
    }

    @Override
    public SearchParamResponse<GiftCertificate> findAllParamSearch(SearchParamRequest req) {
        SearchParamResponse<GiftCertificate> giftCerts = gcDao.findAllParamSearch(req);
        giftCerts.getItems().forEach(gc -> gc.setTags(gcDao.findTagsByGiftCertificate(gc.getId())));
        return giftCerts;
    }

    @Override
    public Set<Tag> findTagsByGiftCertificate(Long giftCertificateId) {
        return gcDao.existById(giftCertificateId) ?
               gcDao.findTagsByGiftCertificate(giftCertificateId) :
               Collections.emptySet();
    }

    @Override
    public Boolean addTag(Long giftCertificateId, Long tagId) {
        return gcDao.addTag(giftCertificateId, tagId);
    }

    @Override
    public Boolean deleteTag(Long giftCertificateId, Long tagId) {
        return gcDao.deleteTag(giftCertificateId, tagId);
    }

    private GiftCertificate getReplica(GiftCertificate gCert) {
        GiftCertificate replica = gcDao.findById(gCert.getId());
        replica.setTags(gcDao.findTagsByGiftCertificate(gCert.getId()));
        return replica;
    }

    private GiftCertificate refineUpdate(GiftCertificate gCert) {
        Set<Tag> refinedTags = new LinkedHashSet<>();
        gCert.getTags().forEach(refineTags(refinedTags));
        gCert.setTags(refinedTags);
        return gCert;
    }

    private Consumer<Tag> addTags(GiftCertificate gCert) {
        return t -> {
            if (t != null && tagDao.existByName(t.getName())) {
                gcDao.addTag(gCert.getId(), tagDao.findByName(t.getName()).getId());
            } else {
                gcDao.addTag(gCert.getId(), tagDao.create(t));
            }
        };
    }

    private Consumer<Tag> deleteTags(GiftCertificate gCert) {
        return t -> gcDao.deleteTag(gCert.getId(), t.getId());
    }

    private Consumer<Tag> refineTags(Set<Tag> refined) {
        return t -> {
            if (tagDao.existByName(t.getName())) {
                refined.add(tagDao.findByName(t.getName()));
            } else {
                refined.add(t);
            }
        };
    }
}