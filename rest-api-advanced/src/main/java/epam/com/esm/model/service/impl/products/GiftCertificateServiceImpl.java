package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.model.dao.interfaces.entity.products.GiftCertificateDao;
import epam.com.esm.model.dao.interfaces.entity.products.TagDao;
import epam.com.esm.model.service.interfaces.entity.products.GiftCertificateService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GiftCertificateServiceImpl class is the service class and implementor of GiftCertificateService interface.
 * Provides business logic operations for gift certificate
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Holds GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Holds TagDao object
     */
    private final TagDao tagDao;

    /**
     * Constructs GiftCertificateServiceImpl with GiftCertificateDao and TagDao objects
     *
     * @param gcDao service, provides jpa operations for gift certificate
     * @param tagDao service, provides jpa operations for tag
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao gcDao, TagDao tagDao) {
        this.gcDao = gcDao;
        this.tagDao = tagDao;
    }

    /**
     * Creates gift certificate
     * Consumes requested gift certificate object, adjusts it and produces created gift certificate object as
     * the response
     *
     * @param giftCertificate provided gift certificate object for creation
     * @return {@code GiftCertificate} created gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GiftCertificate create(GiftCertificate giftCertificate) {
        checkNameExistence(giftCertificate);
        refineGiftCertificate(giftCertificate);
        return getGiftCertificateOnCreate(gcDao.create(giftCertificate));
    }

    /**
     * Finds gift certificate
     * Consumes gift certificate id parameter value and produces found gift certificate object as the response
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} found gift certificate
     */
    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {
        return getGiftCertificate(id);
    }

    /**
     * Updates gift certificate
     * Consumes requested gift certificate object, adjusts it and produces updated gift certificate object as
     * the response
     *
     * @param giftCertificate provided gift certificate object for update
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate update(GiftCertificate giftCertificate) {
        checkIdExistence(giftCertificate.getId());
        checkNameOnUpdate(giftCertificate);
        refineGiftCertificate(giftCertificate);
        return getGiftCertificateOnUpdate(giftCertificate);
    }

    /**
     * Deletes gift certificate
     * Consumes gift certificate id parameter value and produces deleted gift certificate object as the response
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} deleted gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate delete(Long id) {
        checkIdExistence(id);
        return getGiftCertificateOnDelete(id);
    }

    /**
     * Finds all gift certificates
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAll(SearchParamRequest searchParamRequest) {
        return gcDao.findAll(searchParamRequest);
    }

    /**
     * Finds all gift certificates with tags
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates with tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAllTagged(SearchParamRequest searchParamRequest) {
        return gcDao.findAllTagged(searchParamRequest);
    }

    /**
     * Finds all gift certificates with no tags
     *
     * @param searchParamRequest object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates with no tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAllNotTagged(SearchParamRequest searchParamRequest) {
        return gcDao.findAllNotTagged(searchParamRequest);
    }

    /**
     * Finds tags by specified gift certificate id
     *
     * @param gcId requested parameter value, holds gift certificate id value
     * @param searchParamRequest requested object, holds search params values and found items
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findTags(Long gcId, SearchParamRequest searchParamRequest) {
        checkIdExistence(gcId);
        return gcDao.findTags(gcId, searchParamRequest);
    }

    /**
     * Adds tags to gift certificate
     *
     * @param giftCertificate object, holds requested values for gift certificate
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate addTags(GiftCertificate giftCertificate) {
        checkIdExistence(giftCertificate.getId());
        adjustUpdateTime(giftCertificate);
        refineGiftCertificate(giftCertificate);
        giftCertificate.getTags().forEach(addTag(giftCertificate));
        return getGiftCertificate(giftCertificate.getId());
    }

    /**
     * Deletes tags from gift certificate
     *
     * @param giftCertificate object, holds requested values for gift certificate
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate deleteTags(GiftCertificate giftCertificate) {
        checkIdExistence(giftCertificate.getId());
        adjustUpdateTime(giftCertificate);
        refineGiftCertificate(giftCertificate);
        giftCertificate.getTags().forEach(deleteTag(giftCertificate));
        return getGiftCertificate(giftCertificate.getId());
    }

    /**
     * Supplementary method, adjusts update time of updated gift certificate
     *
     * @param preUpdate gift certificate with request values
     */
    private void adjustUpdateTime(GiftCertificate preUpdate) {
        GiftCertificate gc = gcDao.findById(preUpdate.getId());
        gc.setUpdate(preUpdate.getUpdate());
        gcDao.update(gc);
    }

    /**
     * Supplementary method, refines tags' missing values
     *
     * @param giftCertificate requested gift certificate
     */
    private void refineGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate.getTags() != null && !giftCertificate.getTags().isEmpty()) {
            giftCertificate.setTags(
                    giftCertificate.getTags().stream().map(refineTag()).collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }

    /**
     * Function for refining tag's missing values
     *
     * @return {@code Function<Tag, Tag>} function, takes tag with missing values and returns refined tag
     */
    private Function<Tag, Tag> refineTag() {
        return t -> {
            if (tagDao.existsByName(t.getName())) {
                return tagDao.findByName(t.getName());
            }

            return t;
        };
    }

    /**
     * Function for adding tag to gift certificate
     * Will add if tag wasn't applied to gift certificate and exists by id
     * If tag doesn't exist by id will create it in datasource and apply to gift certificate
     *
     * @param gCert requested gift certificate
     * @return {@code Consumer<Tag>} function, adds tag to gift certificate
     */
    private Consumer<Tag> addTag(GiftCertificate gCert) {
        return t -> {
            if (tagDao.existsById(t.getId()) && !gcDao.findTags(gCert.getId()).contains(t)) {
                gcDao.addTag(gCert.getId(), t.getId());
            } else {
                gcDao.addTag(gCert.getId(), tagDao.create(t));
            }
        };
    }

    /**
     * Function for deleting tag from gift certificate
     * Will delete tag if tag applied to gift certificate and exists by id
     *
     * @param gCert requested gift certificate
     * @return {@code Consumer<Tag>} function, deletes tag from gift certificate
     */
    private Consumer<Tag> deleteTag(GiftCertificate gCert) {
        return t -> {
            if (tagDao.existsById(t.getId()) && gcDao.findTags(gCert.getId()).contains(t)) {
                gcDao.deleteTag(gCert.getId(), t.getId());
            }
        };
    }

    /**
     * Supplementary method, returns gift certificate on create
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} created gift certificate
     */
    private GiftCertificate getGiftCertificateOnCreate(Long id) {
        checkCreate(id);
        return getGiftCertificate(id);
    }

    /**
     * Supplementary method, returns gift certificate on update
     *
     * @param giftCertificate requested pre update gift certificate
     * @return {@code GiftCertificate} updated gift certificate
     */
    private GiftCertificate getGiftCertificateOnUpdate(GiftCertificate giftCertificate) {
        checkUpdate(giftCertificate);
        return getGiftCertificate(giftCertificate.getId());
    }

    /**
     * Supplementary method, returns gift certificate on delete
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} deleted gift certificate
     */
    private GiftCertificate getGiftCertificateOnDelete(Long id) {
        GiftCertificate deleted = getGiftCertificate(id);
        checkDelete(id);
        return deleted;
    }

    /**
     * Supplementary method, returns gift certificate if exists by id
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} requested gift certificate
     */
    private GiftCertificate getGiftCertificate(Long id) {
        checkIdExistence(id);
        return gcDao.findById(id);
    }

    /**
     * Supplementary method, checks gift certificate existence by name
     *
     * @param giftCertificate requested gift certificate
     */
    private void checkNameExistence(GiftCertificate giftCertificate) {
        if (gcDao.existsByName(giftCertificate.getName())) {
            throw new AlreadyExistsException("Resource already exists (name = " + giftCertificate.getName() + ")");
        }
    }

    /**
     * Supplementary method, checks gift certificate existence by id
     *
     * @param id requested parameter value, holds gift certificate id value
     */
    private void checkIdExistence(Long id) {
        if (!gcDao.existsById(id)) {
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Supplementary method, checks if gift certificate's name suitable for update
     *
     * @param giftCertificate requested gift certificate
     */
    private void checkNameOnUpdate(GiftCertificate giftCertificate) {
        if (checkNameAndCompareIds(giftCertificate)) {
            throw new InputException("Requested resource with that name already exists (name = " +
                                     giftCertificate.getName() + ")");
        }
    }

    /**
     * Supplementary method, checks if new name for gift certificate doesn't exist or same and
     * compares ids if name already exists
     *
     * @param giftCertificate requested gift certificate
     * @return {@code true} if check was successful
     */
    private boolean checkNameAndCompareIds(GiftCertificate giftCertificate) {
        return gcDao.existsByName(giftCertificate.getName())
               && !Objects.equals(gcDao.findByName(giftCertificate.getName()).getId(), giftCertificate.getId());
    }

    /**
     * Supplementary method, checks if create was successful
     *
     * @param id requested parameter value, holds gift certificate id value
     */
    private void checkCreate(Long id) {
        if (id <= 0) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if update was successful
     *
     * @param giftCertificate requested gift certificate
     */
    private void checkUpdate(GiftCertificate giftCertificate) {
        if (!gcDao.update(giftCertificate)) {
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Supplementary method, checks if delete was successful
     *
     * @param id requested parameter value, holds gift certificate id value
     */
    private void checkDelete(Long id) {
        if (!gcDao.delete(id)) {
            throw new OperationFailedException("Bad request");
        }
    }
}