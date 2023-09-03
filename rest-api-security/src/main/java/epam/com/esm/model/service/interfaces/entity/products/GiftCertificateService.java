package epam.com.esm.model.service.interfaces.entity.products;

import epam.com.esm.model.service.interfaces.base.CrudService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

/**
 * GiftCertificateService is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateService  extends CrudService<GiftCertificate, Long> {

    /**
     * Contract for producing SearchParamResponse object with found gift certificates with tags by requested
     * search params
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found gift certificates with tags
     */
    SearchParamResponse<GiftCertificate> findAllTagged(SearchParamRequest searchParamRequest);

    /**
     * Contract for producing SearchParamResponse object with found gift certificates with no tags by requested
     * search params
     *
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} found gift certificates with no tags
     */
    SearchParamResponse<GiftCertificate> findAllNotTagged(SearchParamRequest searchParamRequest);

    /**
     * Contract for producing SearchParamResponse object with found tags by requested search params
     * and provided id parameter value
     *
     * @param gCertId requested parameter value, holds id value
     * @param searchParamRequest requested object, hold search params values and found items
     * @return {@code SearchParamResponse<Tag>} found tags
     */
    SearchParamResponse<Tag> findTags(Long gCertId, SearchParamRequest searchParamRequest);

    /**
     * Contract for adding tags
     *
     * @param giftCertificate object, holds requested values for gift certificate
     * @return {@code GiftCertificate} gift certificate with added tags
     */
    GiftCertificate addTags(GiftCertificate giftCertificate);

    /**
     * Contract for deleting tags
     *
     * @param giftCertificate object, holds requested values for gift certificate
     * @return {@code GiftCertificate} gift certificate with deleted tags
     */
    GiftCertificate deleteTags(GiftCertificate giftCertificate);
}