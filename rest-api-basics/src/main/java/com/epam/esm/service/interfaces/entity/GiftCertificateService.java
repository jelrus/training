package com.epam.esm.service.interfaces.entity;

import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.general.CrudService;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import com.epam.esm.utils.search.dao.SearchParamResponse;

import java.util.Set;

public interface GiftCertificateService extends CrudService<GiftCertificate, Long> {

    Set<Tag> findTagsByGiftCertificate(Long giftCertificateId);

    Boolean addTag(Long giftCertificateId, Long tagId);

    Boolean deleteTag(Long giftCertificateId, Long tagId);

    Set<GiftCertificate> findAllTagged();

    SearchParamResponse<GiftCertificate> findAllParamSearch(SearchParamRequest req);
}