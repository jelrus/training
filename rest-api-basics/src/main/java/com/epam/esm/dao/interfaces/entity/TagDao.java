package com.epam.esm.dao.interfaces.entity;

import com.epam.esm.dao.interfaces.supplementary.Countable;
import com.epam.esm.dao.interfaces.general.CrdDao;
import com.epam.esm.dao.interfaces.supplementary.Existent;
import com.epam.esm.dao.interfaces.supplementary.GenerateKeys;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;

import java.util.Set;

public interface TagDao extends CrdDao<Tag, Long>, Existent<Long>, Countable, GenerateKeys<Long> {

    Boolean existByName(String name);

    Tag findByName(String name);

    Long countGiftCertificatesByTag(Long tagId);

    Set<GiftCertificate> findGiftCertificatesByTag(Long tagId);

    Boolean addGiftCertificate(Long tagId, Long giftCertificateId);

    Boolean deleteGiftCertificate(Long tagId, Long giftCertificateId);

    Set<Tag> findAllCertificated();
}