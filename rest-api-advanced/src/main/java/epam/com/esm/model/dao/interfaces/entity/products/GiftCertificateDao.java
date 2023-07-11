package epam.com.esm.model.dao.interfaces.entity.products;

import epam.com.esm.model.dao.interfaces.base.CrudDao;
import epam.com.esm.model.dao.interfaces.supplementary.Existent;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;

import java.util.List;

/**
 * GiftCertificateDao is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateDao extends CrudDao<GiftCertificate, Long>, Existent<Long> {

    /**
     * Contract for gift certificate existence check by requested gift certificate name value
     *
     * @param name requested parameter, holds gift certificate name value
     * @return {@code true} if entity exists
     */
    Boolean existsByName(String name);

    /**
     * Contract for finding gift certificate by requested name value
     *
     * @param name requested parameter, holds gift certificate name value
     * @return {@code GiftCertificate} found gift certificate
     */
    GiftCertificate findByName(String name);

    /**
     * Contract for finding gift certificates with tags only
     *
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * tagged gift certificates
     */
    SearchParamResponse<GiftCertificate> findAllTagged(SearchParamRequest searchParamRequest);

    /**
     * Contract for finding  gift certificates with no tags only
     *
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * not tagged gift certificates
     */
    SearchParamResponse<GiftCertificate> findAllNotTagged(SearchParamRequest searchParamRequest);

    /**
     * Contract for finding tags by specified gift certificate id and search param request
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param searchParamRequest requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found tags
     */
    SearchParamResponse<Tag> findTags(Long gCertId, SearchParamRequest searchParamRequest);

    /**
     * Contract for finding tags by specified gift certificate id
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @return {@code List<Tag>} found tags
     */
    List<Tag> findTags(Long gCertId);

    /**
     * Contract for adding tag to gift certificate
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param tagId requested parameter, hold tag id value
     * @return {@code true} if tag was added
     */
    Boolean addTag(Long gCertId, Long tagId);

    /**
     * Contract for deleting tag from gift certificate
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param tagId requested parameter, hold tag id value
     * @return {@code true} if tag was deleted
     */
    Boolean deleteTag(Long gCertId, Long tagId);
}