package epam.com.esm.model.dao.impl.products;

import epam.com.esm.model.dao.interfaces.entity.products.TagDao;
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
 * TagDaoImpl class is the service class and implementor of TagDao interface
 * Used to execute JPA commands on datasource via Entity Manager
 */
@Repository
@Transactional
public class TagDaoImpl implements TagDao {

    /**
     * Holds EntityManager object, which used to interact with the persistence context
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates tag
     *
     * @param tag requested object, holds tag values for create
     * @return {@code Long} id of created tag
     */
    @Override
    public Long create(Tag tag) {
        return entityManager.merge(tag).getId();
    }

    /**
     * Finds tag by id
     *
     * @param id requested parameter for search, holds tag id value
     * @return {@code Tag} found tag
     */
    @Override
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    /**
     * Deletes tag
     *
     * @param id requested parameter for delete, holds tag id value
     * @return {@code true} if tag was deleted
     */
    @Override
    public Boolean delete(Long id) {
        entityManager.remove(entityManager.find(Tag.class, id));
        return entityManager.find(Tag.class, id) == null;
    }

    /**
     * Finds all tags by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found tags
     */
    @Override
    public SearchParamResponse<Tag> findAll(SearchParamRequest spReq) {
        FilterBuilder<Tag> fb = new FilterBuilder<>(entityManager, Tag.class, GiftCertificate.class);
        fb.applyRequest(spReq).addSearchPredicates(Joining.AND).buildPredicates(Joining.AND).addOrders();
        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Checks if requested by id tag exists
     *
     * @param id requested parameter for existence check, holds tag id value
     * @return {@code true} if tag exists by id
     */
    @Override
    public Boolean existsById(Long id) {
        String existsById = "SELECT count(t.id) FROM Tag t WHERE t.id = :id";
        return (Long) entityManager.createQuery(existsById)
                                   .setParameter("id", id)
                                   .getSingleResult() == 1;
    }

    /**
     * Checks if requested by name tag exists
     *
     * @param name requested parameter for existence check, holds tag name value
     * @return {@code true} if tag exists by name
     */
    @Override
    public Boolean existsByName(String name) {
        String existsByName = "SELECT count(t.name) FROM Tag t WHERE t.name = :name";
        return (Long) entityManager.createQuery(existsByName)
                                   .setParameter("name", name)
                                   .getSingleResult() == 1;
    }

    /**
     * Finds tag by name
     *
     * @param name requested parameter for search, holds tag name value
     * @return {@code Tag} found tag
     */
    @Override
    public Tag findByName(String name) {
        String findByName = "SELECT t FROM Tag t WHERE t.name = :name";
        return (Tag) entityManager.createQuery(findByName)
                                  .setParameter("name", name)
                                  .getSingleResult();
    }

    /**
     * Finds all certificated tags by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found
     * certificated tags
     */
    @Override
    public SearchParamResponse<Tag> findAllCertificated(SearchParamRequest spReq) {
        FilterBuilder<Tag> fb = new FilterBuilder<>(entityManager, Tag.class, GiftCertificate.class);

        Expression<Long> gCertId = fb.getRoot().join("giftCertificates", JoinType.LEFT).get("id");
        Predicate c = fb.getBuilder().isNotNull(gCertId);

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                .addPredicate(c)
                .buildPredicates(Joining.AND)
                .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds all not certificated tags by search param request
     *
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<Tag>} object, which contains response params and list of found not
     * certificated tags
     */
    @Override
    public SearchParamResponse<Tag> findAllNotCertificated(SearchParamRequest spReq) {
        FilterBuilder<Tag> fb = new FilterBuilder<>(entityManager, Tag.class, GiftCertificate.class);

        Expression<Long> gCertId = fb.getRoot().join("giftCertificates", JoinType.LEFT).get("id");
        Predicate c = fb.getBuilder().isNull(gCertId);

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                .addPredicate(c)
                .buildPredicates(Joining.AND)
                .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Finds gift certificates by specified tag id
     *
     * @param tagId requested parameter, holds tag id value
     * @return {@code Set<GiftCertificate>} found gift certificates
     */
    @Override
    public List<GiftCertificate> findGiftCertificates(Long tagId) {
        return entityManager.find(Tag.class, tagId).getGiftCertificates();
    }

    /**
     * Finds gift certificates by specified tag id and search param request
     *
     * @param tagId requested parameter, holds tag id value
     * @param spReq requested object, contains params required for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains response params and list of
     * found gift certificates
     */
    @Override
    public SearchParamResponse<GiftCertificate> findGiftCertificates(Long tagId, SearchParamRequest spReq) {
        FilterBuilder<GiftCertificate> fb = new FilterBuilder<>(entityManager, GiftCertificate.class, Tag.class);

        Expression<Long> id = fb.getRoot().join("tags").get("id").as(Long.class);
        Predicate p = fb.getBuilder().and(fb.getBuilder().equal(id, tagId));

        fb.applyRequest(spReq).addSearchPredicates(Joining.AND)
                              .addPredicate(p)
                              .buildPredicates(Joining.AND)
                              .addOrders();

        return initResponse(spReq, fb.count(true), new ArrayList<>(fb.runQuery(true)));
    }

    /**
     * Adds gift certificate to tag
     *
     * @param tagId requested parameter, hold tag id
     * @param gCertId requested parameter, holds gift certificate id
     * @return {@code true} if tag was added
     */
    @Override
    public Boolean addGiftCertificate(Long tagId, Long gCertId) {
        Tag t = entityManager.find(Tag.class, tagId);
        GiftCertificate gc = entityManager.find(GiftCertificate.class, gCertId);
        return t.getGiftCertificates().add(gc);
    }

    /**
     * Deletes gift certificate from tag
     *
     * @param tagId requested parameter, hold tag id
     * @param gCertId requested parameter, holds gift certificate id
     * @return {@code true} if tag was deleted
     */
    @Override
    public Boolean deleteGiftCertificate(Long tagId, Long gCertId) {
        Tag t = entityManager.find(Tag.class, tagId);
        GiftCertificate gc = entityManager.find(GiftCertificate.class, gCertId);
        return t.getGiftCertificates().remove(gc);
    }
}