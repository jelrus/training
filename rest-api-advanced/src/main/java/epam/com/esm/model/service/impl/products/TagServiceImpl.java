package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.products.TagDao;
import epam.com.esm.model.service.interfaces.entity.products.TagService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TagServiceImpl class is the service class and implementor of TagService interface.
 * Provides business logic operations for tag
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * Holds TagDao object
     */
    private final TagDao tagDao;

    /**
     * Holds GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Constructs TagServiceImpl with TagDao and GiftCertificateDao objects
     *
     * @param tagDao service, provides jpa operations for tag
     * @param gcDao service, provides jpa operations for gift certificate
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateDao gcDao) {
        this.tagDao = tagDao;
        this.gcDao = gcDao;
    }

    /**
     * Creates tag
     * Consumes requested tag object, adjusts it and produces created tag object as the response
     *
     * @param tag provided tag object for creation
     * @return {@code Tag} created tag
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Tag create(Tag tag) {
        checkNameExistence(tag);
        refineTag(tag);
        return getTagOnCreate(tagDao.create(tag));
    }

    /**
     * Finds tag
     * Consumes tag id parameter value and produces found tag object as the response
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code Tag} found tag
     */
    @Override
    @Transactional(readOnly = true)
    public Tag findById(Long id) {
        return getTag(id);
    }

    /**
     * Deletes tag
     * Consumes tag id parameter value and produces deleted tag object as the response
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code Tag} deleted tag
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Tag delete(Long id) {
        checkIdExistence(id);
        return getTagOnDelete(id);

    }

    /**
     * Finds all tags
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findAll(SearchParamRequest searchParamRequest) {
        return tagDao.findAll(searchParamRequest);
    }

    /**
     * Finds all tags with gift certificates
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     * with gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findAllCertificated(SearchParamRequest spReq) {
        return tagDao.findAllCertificated(spReq);
    }

    /**
     * Finds all tags with no gift certificates
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     * with no gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findAllNotCertificated(SearchParamRequest spReq) {
        return tagDao.findAllNotCertificated(spReq);
    }

    /**
     * Finds gift certificates by specified tag id
     *
     * @param tagId requested parameter value, holds tag id value
     * @param spReq requested object, holds search params values and found items
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and
     * found gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findGiftCertificates(Long tagId, SearchParamRequest spReq) {
        checkIdExistence(tagId);
        return tagDao.findGiftCertificates(tagId, spReq);

    }

    /**
     * Adds gift certificates to tag
     *
     * @param tag object, holds requested values for tag
     * @return {@code Tag} updated tag
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Tag addGiftCertificates(Tag tag) {
        checkIdExistence(tag.getId());
        refineTag(tag);
        tag.getGiftCertificates().forEach(addGiftCertificate(tag));
        return getTag(tag.getId());
    }

    /**
     * Deletes gift certificates from tag
     *
     * @param tag object, holds requested values for tag
     * @return {@code Tag} updated tag
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Tag deleteGiftCertificates(Tag tag) {
        checkIdExistence(tag.getId());
        refineTag(tag);
        tag.getGiftCertificates().forEach(deleteGiftCertificate(tag));
        return getTag(tag.getId());
    }

    /**
     * Supplementary method, refines gift certificates' missing values
     *
     * @param tag requested tag
     */
    private void refineTag(Tag tag) {
        if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
            tag.setGiftCertificates(tag.getGiftCertificates().stream()
                                                             .map(refineGiftCertificate())
                                                             .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    /**
     * Function for refining gift certificate's missing values
     *
     * @return {@code Function<GiftCertificate, GiftCertificate>} function, takes gift certificate with missing values
     * and return refined gift certificate
     */
    private Function<GiftCertificate, GiftCertificate> refineGiftCertificate() {
        return gc -> {
            if (gcDao.existsByName(gc.getName())) {
                return gcDao.findByName(gc.getName());
            } else {
                throw new NotFoundException("Resource wasn't found (name = " + gc.getName() + ")");
            }
        };
    }

    /**
     * Function for adding gift certificate to tag
     * Will add if gift certificate wasn't applied to tag and exists by id
     *
     * @param tag requested gift certificate
     * @return {@code Consumer<GiftCertificate>} function, adds gift certificate to tag
     */
    private Consumer<GiftCertificate> addGiftCertificate(Tag tag) {
        return gc -> {
            if (gcDao.existsById(gc.getId()) && !tagDao.findGiftCertificates(tag.getId()).contains(gc)) {
                tagDao.addGiftCertificate(tag.getId(), gc.getId());
            }
        };
    }

    /**
     * Function for deleting gift certificate from tag
     * Will add if gift certificate applied to tag and exists by id
     *
     * @param tag requested gift certificate
     * @return {@code Consumer<GiftCertificate>} function, deletes gift certificate from tag
     */
    private Consumer<GiftCertificate> deleteGiftCertificate(Tag tag) {
        return gc -> {
            if (gcDao.existsById(gc.getId()) && tagDao.findGiftCertificates(tag.getId()).contains(gc)) {
                tagDao.deleteGiftCertificate(tag.getId(), gc.getId());
            }
        };
    }

    /**
     * Supplementary method, returns tag on create
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code Tag} created tag
     */
    private Tag getTagOnCreate(Long id) {
        checkCreate(id);
        return getTag(id);
    }

    /**
     * Supplementary method, returns tag on delete
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code Tag} deleted tag
     */
    private Tag getTagOnDelete(Long id) {
        Tag deleted = getTag(id);
        checkDelete(id);
        return deleted;
    }

    /**
     * Supplementary method, returns tag if exists by id
     *
     * @param id requested parameter value, holds tag
     * @return {@code Tag} requested tag
     */
    private Tag getTag(Long id) {
        checkIdExistence(id);
        return tagDao.findById(id);
    }

    /**
     * Supplementary method, checks tag existence by name
     *
     * @param tag requested tag
     */
    private void checkNameExistence(Tag tag) {
        if (tagDao.existsByName(tag.getName())) {
            throw new AlreadyExistsException("Resource already exists (name = " + tag.getName() + ")");
        }
    }

    /**
     * Supplementary method, checks tag existence by id
     *
     * @param id requested parameter value, holds tag id value
     */
    private void checkIdExistence(Long id) {
        if (!tagDao.existsById(id)) {
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Supplementary method, checks if create was successful
     *
     * @param id requested parameter value, holds tag id value
     */
    private void checkCreate(Long id) {
        if (id <= 0) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if delete was successful
     *
     * @param id requested parameter value, holds tag id value
     */
    private void checkDelete(Long id) {
        if (!tagDao.delete(id)) {
            throw new OperationFailedException("Bad request");
        }
    }
}