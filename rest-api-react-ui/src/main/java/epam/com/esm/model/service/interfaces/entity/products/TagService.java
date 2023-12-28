package epam.com.esm.model.service.interfaces.entity.products;

import epam.com.esm.model.service.interfaces.base.CrdService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * TagService is the interface that delegates CRUD contracts from ancestors and specific operations for
 * tag logic contracts to implementor
 */
public interface TagService extends CrdService<Tag, Long> {

    /**
     * Contract for producing SearchParamResponse object with found tags with gift certificates by requested
     * search params
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found tags with gift certificates
     */
    SearchParamResponse<Tag> findAllCertificated(SearchParamRequest searchParamRequest);

    /**
     * Contract for producing SearchParamResponse object with found tags with no gift certificates by requested
     * search params
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found tags with no gift certificates
     */
    SearchParamResponse<Tag> findAllNotCertificated(SearchParamRequest searchParamRequest);

    /**
     * Contract for producing SearchParamResponse object with found gift certificates by requested search params
     * and provided id parameter value
     *
     * @param tagId requested parameter value, holds id value
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found gift certificates
     */
    SearchParamResponse<GiftCertificate> findGiftCertificates(Long tagId, SearchParamRequest searchParamRequest);

    /**
     * Contract for adding gift certificates
     *
     * @param tag object, holds requested values for tag
     * @return {@code Tag} tag with added gift certificates
     */
    Tag addGiftCertificates(Tag tag);

    /**
     * Contract for deleting gift certificates
     *
     * @param tag object, holds requested values for tag
     * @return {@code Tag} tag with added gift certificates
     */
    Tag deleteGiftCertificates(Tag tag);
}