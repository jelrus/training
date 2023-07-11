package epam.com.esm.model.dao.impl.products;

import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.request.builders.FilterBuilder;
import epam.com.esm.utils.search.request.components.type.Joining;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * GiftCertificateDaoImpl class is the service class and implementor of GiftCertificateDao interface
 * Used to execute JPA commands on datasource via Entity Manager
 */
@Repository
@Transactional
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    /**
     * Holds EntityManager object, which used to interact with the persistence context
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates gift certificate
     *
     * @param giftCertificate requested object, holds gift certificate values for create
     * @return {@code Long} id of created gift certificate
     */
    @Override
    public Long create(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate).getId();
    }

    /**
     * Finds gift certificate by id
     *
     * @param id requested parameter for search, holds gift certificate id value
     * @return {@code GiftCertificate} found gift certificate
     */
    @Override
    public GiftCertificate findById(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    /**
     * Updates gift certificate
     *
     * @param giftCertificate requested object, holds gift certificate values for update
     * @return {@code true} if gift certificate was updated
     */
    @Override
    public Boolean update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate) != null;
    }

    /**
     * Deletes gift certificate
     *
     * @param id requested parameter for delete, holds gift certificate id value
     * @return {@code true} if gift certificate was deleted
     */
    @Override
    public Boolean delete(Long id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
        return entityManager.find(GiftCertificate.class, id) == null;
    }

    /**
     * Finds all gift certificates by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and set of found
     * gift certificates
     */
    @Override
    public SearchParamResponse<GiftCertificate> findAll(SearchParamRequest spReq) {
        FilterBuilder<GiftCertificate> fb = new FilterBuilder<>(entityManager, GiftCertificate.class, Tag.class);
        fb.applyRequest(spReq).addSearchPredicates(Joining.AND).buildPredicates(Joining.AND).addOrders();
        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Checks if requested by id gift certificate exists
     *
     * @param id requested parameter for existence check, holds gift certificate id value
     * @return {@code true} if gift certificate exists by id
     */
    @Override
    public Boolean existsById(Long id) {
        String exists = "SELECT count(gc.id) FROM GiftCertificate gc WHERE gc.id = :id";
        return (Long) entityManager.createQuery(exists)
                                   .setParameter("id", id)
                                   .getSingleResult() == 1;
    }

    /**
     * Checks if requested by name gift certificate exists
     *
     * @param name requested parameter for existence check, holds gift certificate name value
     * @return {@code true} if gift certificate exists by name
     */
    @Override
    public Boolean existsByName(String name) {
        String exists = "SELECT count(gc.name) FROM GiftCertificate gc WHERE gc.name = :name";
        return (Long) entityManager.createQuery(exists)
                                   .setParameter("name", name)
                                   .getSingleResult() == 1;
    }

    /**
     * Finds gift certificate by name
     *
     * @param name requested parameter for search, holds gift certificate name value
     * @return {@code GiftCertificate} found gift certificate
     */
    @Override
    public GiftCertificate findByName(String name) {
        String findByName = "SELECT gc FROM GiftCertificate gc WHERE gc.name = :name";
        return (GiftCertificate) entityManager.createQuery(findByName)
                                              .setParameter("name", name)
                                              .getSingleResult();
    }

    /**
     * Finds all tagged gift certificates by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * tagged gift certificates
     */
    @Override
    public SearchParamResponse<GiftCertificate> findAllTagged(SearchParamRequest spReq) {
        FilterBuilder<GiftCertificate> fb = new FilterBuilder<>(entityManager, GiftCertificate.class, Tag.class);

        Expression<Long> tagId = fb.getRoot().join("tags", JoinType.LEFT).get("id");
        Predicate c = fb.getBuilder().isNotNull(tagId);

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(c)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds all not tagged gift certificates by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of found
     * not tagged gift certificates
     */
    @Override
    public SearchParamResponse<GiftCertificate> findAllNotTagged(SearchParamRequest spReq) {
        FilterBuilder<GiftCertificate> fb = new FilterBuilder<>(entityManager, GiftCertificate.class, Tag.class);

        Predicate c = fb.getBuilder().isNull(fb.getRoot().join("tags", JoinType.LEFT).get("id"));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(c)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds tags by specified gift certificate id
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @return {@code Set<Tag>} found tags
     */
    @Override
    public List<Tag> findTags(Long gCertId) {
        return entityManager.find(GiftCertificate.class, gCertId).getTags();
    }

    /**
     * Finds tags by specified gift certificate id and search param request
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found tags
     */
    @Override
    public SearchParamResponse<Tag> findTags(Long gCertId, SearchParamRequest spReq) {
        FilterBuilder<Tag> fb = new FilterBuilder<>(entityManager, Tag.class, GiftCertificate.class);

        Expression<Long> id = fb.getRoot().join("giftCertificates").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, gCertId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Adds tag to gift certificate
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param tagId requested parameter, hold tag id value
     * @return {@code true} if tag was added
     */
    @Override
    public Boolean addTag(Long gCertId, Long tagId) {
        GiftCertificate gc = entityManager.find(GiftCertificate.class, gCertId);
        Tag t = entityManager.find(Tag.class, tagId);
        return gc.getTags().add(t);
    }

    /**
     * Deletes tag from gift certificate
     *
     * @param gCertId requested parameter, holds gift certificate id value
     * @param tagId requested parameter, hold tag id value
     * @return {@code true} if tag was deleted
     */
    @Override
    public Boolean deleteTag(Long gCertId, Long tagId) {
        GiftCertificate gc = entityManager.find(GiftCertificate.class, gCertId);
        Tag t = entityManager.find(Tag.class, tagId);
        return gc.getTags().remove(t);
    }
}