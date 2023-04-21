package com.epam.esm.service.interfaces.entity;

import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.general.CrdService;

import java.util.Set;

public interface TagService extends CrdService<Tag, Long> {

    Set<GiftCertificate> findGiftCertificatesByTag(Long tagId);

    Boolean addGiftCertificate(Long tagId, Long giftCertificateId);

    Boolean deleteGiftCertificate(Long tagId, Long giftCertificateId);

    Set<Tag> findAllCertificated();
}