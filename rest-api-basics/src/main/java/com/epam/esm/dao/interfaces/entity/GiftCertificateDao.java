package com.epam.esm.dao.interfaces.entity;

import com.epam.esm.dao.interfaces.general.Countable;
import com.epam.esm.dao.interfaces.general.CrudDao;
import com.epam.esm.dao.interfaces.general.Existent;
import com.epam.esm.dao.interfaces.general.GenerateKeys;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import com.epam.esm.utils.search.dao.SearchParamResponse;

import java.util.Set;

public interface GiftCertificateDao extends CrudDao<GiftCertificate, Long>,
        Existent<Long>, Countable, GenerateKeys<Long> {

    Long countTagsByGiftCertificate(Long giftCertificateId);

    Set<Tag> findTagsByGiftCertificate(Long giftCertificateId);

    Boolean addTag(Long giftCertificateId, Long tagId);

    Boolean deleteTag(Long giftCertificateId, Long tagId);

    Set<GiftCertificate> findAllTagged();

    SearchParamResponse<GiftCertificate> findAllParamSearch(SearchParamRequest req);
}