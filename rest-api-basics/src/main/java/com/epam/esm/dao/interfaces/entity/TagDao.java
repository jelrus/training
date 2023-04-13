package com.epam.esm.dao.interfaces.entity;

import com.epam.esm.dao.interfaces.general.Countable;
import com.epam.esm.dao.interfaces.general.CrdDao;
import com.epam.esm.dao.interfaces.general.Existent;
import com.epam.esm.dao.interfaces.general.GenerateKeys;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;

import java.util.Set;

public interface TagDao extends CrdDao<Tag, Long>, Existent<Long>, Countable, GenerateKeys<Long> {

    Long countGiftCertificatesByTag(Long tagId);

    Set<GiftCertificate> findGiftCertificatesByTag(Long tagId);

    Boolean addGiftCertificate(Long tagId, Long giftCertificateId);

    Boolean deleteGiftCertificate(Long tagId, Long giftCertificateId);

    Set<Tag> findAllCertificated();
}