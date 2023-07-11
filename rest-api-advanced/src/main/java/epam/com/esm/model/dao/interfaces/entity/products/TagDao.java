package epam.com.esm.model.dao.interfaces.entity.products;

import epam.com.esm.model.dao.interfaces.base.CrdDao;
import epam.com.esm.model.dao.interfaces.supplementary.Existent;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

import java.util.List;

public interface TagDao extends CrdDao<Tag, Long>, Existent<Long> {

    /**
     * Contract for tag existence check by requested tag name value
     *
     * @param name requested parameter for existence check, holds tag name value
     * @return {@code true} if tag exists by name
     */
    Boolean existsByName(String name);

    /**
     * Contract for finding tag by name
     *
     * @param name requested parameter for search, holds tag name value
     * @return {@code Tag} found tag
     */
    Tag findByName(String name);

    /**
     * Contract for finding tags with gift certificates only
     *
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found
     * certificated tags
     */
    SearchParamResponse<Tag> findAllCertificated(SearchParamRequest searchParamRequest);

    /**
     * Contract for finding tags with no gift certificates only
     *
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found not
     * certificated tags
     */
    SearchParamResponse<Tag> findAllNotCertificated(SearchParamRequest searchParamRequest);

    /**
     *  Contract for finding gift certificates by specified tag id
     *
     * @param tagId requested parameter, holds tag id value
     * @return {@code List<GiftCertificate>} found gift certificates
     */
    List<GiftCertificate> findGiftCertificates(Long tagId);

    /**
     *  Contract for finding gift certificates by specified tag id and search param request
     *
     * @param tagId requested parameter, holds tag id value
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of
     * found gift certificates
     */
    SearchParamResponse<GiftCertificate> findGiftCertificates(Long tagId, SearchParamRequest searchParamRequest);

    /**
     * Contract for adding gift certificate to tag
     *
     * @param tagId requested parameter, hold tag id
     * @param gCertId requested parameter, holds gift certificate id
     * @return {@code true} if tag was added
     */
    Boolean addGiftCertificate(Long tagId, Long gCertId);

    /**
     * Contract for deleting gift certificate from tag
     *
     * @param tagId requested parameter, hold tag id
     * @param gCertId requested parameter, holds gift certificate id
     * @return {@code true} if tag was deleted
     */
    Boolean deleteGiftCertificate(Long tagId, Long gCertId);
}