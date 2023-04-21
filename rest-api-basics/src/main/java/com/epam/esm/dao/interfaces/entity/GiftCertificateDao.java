package com.epam.esm.dao.interfaces.entity;

import com.epam.esm.dao.interfaces.supplementary.Countable;
import com.epam.esm.dao.interfaces.general.CrudDao;
import com.epam.esm.dao.interfaces.supplementary.Existent;
import com.epam.esm.dao.interfaces.supplementary.GenerateKeys;
import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import com.epam.esm.utils.search.dao.SearchParamResponse;

import java.util.Set;

public interface GiftCertificateDao extends CrudDao<GiftCertificate, Long>,
        Existent<Long>, Countable, GenerateKeys<Long> {

    Boolean existByName(String name);

    GiftCertificate findByName(String name);

    Long countTagsByGiftCertificate(Long giftCertificateId);

    Set<Tag> findTagsByGiftCertificate(Long giftCertificateId);

    Boolean addTag(Long giftCertificateId, Long tagId);

    Boolean deleteTag(Long giftCertificateId, Long tagId);

    Set<GiftCertificate> findAllTagged();

    SearchParamResponse<GiftCertificate> findAllParamSearch(SearchParamRequest req);
}