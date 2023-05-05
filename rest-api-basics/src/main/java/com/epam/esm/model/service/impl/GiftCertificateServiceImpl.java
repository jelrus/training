package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.exception.types.AlreadyExistsException;
import com.epam.esm.exception.types.NotFoundException;
import com.epam.esm.exception.types.OperationFailedException;
import com.epam.esm.model.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.utils.logger.LoggerService;
import com.epam.esm.utils.logger.impl.type.LoggerLevel;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * GiftCertificateServiceImpl class is the service class and implementor of GiftCertificateService interface.
 * Used to form requests based on lesser dao requests and manipulate GiftCertificate objects
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Field to hold LoggerService object
     */
    private final LoggerService loggerService;

    /**
     * Field to hold GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Field to hold TagDao object
     */
    private final TagDao tagDao;

    /**
     * Constructs GiftCertificateServiceImpl with GiftCertificateDao and TagDao objects, and LoggerService object
     * NOTE: If gift certificate, tag services or logger service objects is null it will create and inject this
     * dependency according to @Autowired annotation
     *
     * @param loggerService logger service, which writes logs
     * @param gcDao gift certificate dao, which provides methods to manipulate gift certificates on database level
     * @param tagDao tag dao, which provides methods to manipulate tags on database level
     */
    @Autowired
    public GiftCertificateServiceImpl(LoggerService loggerService, GiftCertificateDao gcDao, TagDao tagDao) {
        this.loggerService = loggerService;
        this.gcDao = gcDao;
        this.tagDao = tagDao;
    }

    /**
     * Creates gift certificate
     * Checks if requested GiftCertificate candidate is not exists by name:
     * - if true creates gift certificate and holds its id
     * - if false throws AlreadyExistsException exception
     * <p>
     * Then creates requested tags contained in candidate:
     * - if tag already exists ties this tag in result table
     * - if tag does not exist creates it in relevant table within database and ties this tag in result table
     * <p>
     * Checks whether GiftCertificate was created by id value check:
     * - if id value > 0 - returns created GiftCertificate
     * - if id value < 0 - throws OperationFailedException exception
     *
     * @param gCert candidate for creation
     * @return {@code GiftCertificate} created object
     */
    @Override
    public GiftCertificate create(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Create] Operation started");
        if (!gcDao.existByName(gCert.getName())) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Create] Existence check passed");
            Long gCertId = gcDao.create(gCert);
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Create] Created. Key obtained.");
            createTags(gCert, gCertId);
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Create] Tags created");
            return getGiftCertificateOnCreate(gCertId);
        } else {
           loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Create] Existence check not passed");
           Long existing = gcDao.findByName(gCert.getName()).getId();
           throw new AlreadyExistsException("Resource already exists (id = " + existing + ")");
        }
    }

    /**
     * Updates gift certificate
     * Checks if requested GiftCertificate candidate is exists by name:
     * - if true proceed to next operation
     * - if false throws NotFoundException exception
     * <p>
     * Then checks if tags set is null:
     * - if true - proceed to update object straightforward
     * - if false - gets original object from database and refines tags for candidate (if exists)
     * <p>
     * Then checks if tags set is empty:
     * - if true wipes all tags relations from database
     * - if false - removes all relations from database which candidate doesn't contain, skips common relations,
     * creates new relations if they don't exist (this part works similar to create gift certificate tag section):
     *  - if tag exists ties it within relation table
     *  - if tag does not exist creates entry for it and ties within relation table
     * <p>
     *  Then proceed to update method:
     *  - if true - returns updated object
     *  - if false - throws OperationFailedException exception
     *
     * @param gCert candidate for update
     * @return {@code GiftCertificate} updated object
     */
    @Override
    public GiftCertificate update(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Update] Operation started");
        if (gcDao.existById(gCert.getId())) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Update] Existence check passed");
            updateTags(gCert);
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Update] Tags updated");
            return getGiftCertificateOnUpdate(gCert);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Create] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (id = " + gCert.getId() + ")");
        }
    }

    /**
     * Finds gift certificate by id
     * Checks if gift certificate exists:
     * - if true - finds gift certificate by requested id and set tags to it
     * - if false - throws NotFoundException exception
     *
     * @param id requested parameter
     * @return {@code GiftCertificate} found object
     */
    @Override
    public GiftCertificate findById(Long id) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Find] Operation started");
        if (gcDao.existById(id)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Find] Existence check passed");
            GiftCertificate gCert = gcDao.findById(id);
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Find] Object obtained");
            gCert.setTags(gcDao.findTags(id));
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Find] Tags set. Object ready");
            return gCert;
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Find] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Deletes gift certificate
     * Checks if gift certificate exists by id:
     * - if true - finds gift certificate, stores it within object, deletes from database
     * - if false - throws NotFoundException exception
     * <p>
     * Then checks if object was deleted from database:
     * - if true - return previously stored deleted entry
     * - if false - throws OperationFailedException exception
     *
     * @param id requested parameter
     * @return {@code GiftCertificate} deleted object
     */
    @Override
    public GiftCertificate delete(Long id) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Delete] Operation started");
        if (gcDao.existById(id)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Delete] Existence check passed");
            GiftCertificate gCert = findById(id);
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Delete] Candidate object obtained");
            return getGiftCertificateOnDelete(gCert, id);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Delete] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Finds all gift certificates, adds tags for each
     *
     * @return {@code Set<GiftCertificate>} set of gift certificates
     */
    @Override
    public Set<GiftCertificate> findAll() {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Find All] Operation started");
        return gcDao.findAll().stream().peek(gc -> gc.setTags(gcDao.findTags(gc.getId())))
                                       .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Finds all gift certificates which have at least one tag, adds tags for each
     *
     * @return {@code Set<GiftCertificate>} set of gift certificates
     */
    @Override
    public Set<GiftCertificate> findAllTagged() {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Find All Tagged] Operation started");
        return gcDao.findAllTagged().stream().peek(gc -> gc.setTags(gcDao.findTags(gc.getId())))
                                             .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Finds all gift certificates based on search param request, add tags for each one, and stores items
     * in search param response with search params respectively
     *
     * @param spReq object, which holds search params
     * @return {@code SearchParamResponse<GiftCertificate>} object, which holds search result and params
     */
    @Override
    public SearchParamResponse<GiftCertificate> findAllSearch(SearchParamRequest spReq) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Param Search] Operation started");
        SearchParamResponse<GiftCertificate> spResp = gcDao.findAllSearch(spReq);
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Param Search] Response obtained");
        spResp.getItems().forEach(gc -> gc.setTags(gcDao.findTags(gc.getId())));
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Param Search] Items obtained");
        return spResp;
    }

    /**
     * Checks if gift certificate and tag exists:
     * - if true proceed to add tag operation
     * - if false throws NotFoundException exception
     * <p>
     * Checks result of add tag operation:
     * - if true finds gift certificate with updated values
     * - if false throws OperationFailedException exception
     *
     * @param gCertId requested parameter, holder for gift certificate id
     * @param tagId requested parameter, holder for tag id
     * @return {@code GiftCertificate} updated object
     */
    @Override
    public GiftCertificate addTag(Long gCertId, Long tagId) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Add Tag] Operation started");
        if (gcDao.existById(gCertId) && tagDao.existById(tagId)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Add Tag] Passed existence check");
            if (gcDao.addTag(gCertId, tagId)) {
                loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Add Tag] Tag added");
                return findById(gCertId);
            } else {
                loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Add Tag] Operation failed");
                throw new OperationFailedException("Bad request");
            }
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Add Tag] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (gcCertId = " + gCertId + ", " +
                                        "tagId = " + tagId + ")");
        }
    }

    /**
     * Checks if gift certificate and tag exists:
     * - if true proceed to delete tag operation
     * - if false throws NotFoundException exception
     * <p>
     * Checks result of delete tag operation:
     * - if true finds gift certificate with updated values
     * - if false throws OperationFailedException exception
     *
     * @param gCertId requested parameter, holder for gift certificate id
     * @param tagId requested parameter, holder for tag id
     * @return {@code GiftCertificate} updated object
     */
    @Override
    public GiftCertificate deleteTag(Long gCertId, Long tagId) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Delete Tag] Operation started");
        if (gcDao.existById(gCertId) && tagDao.existById(tagId)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Delete Tag] Passed existence check");
            if (gcDao.deleteTag(gCertId, tagId)) {
                loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Delete Tag] Tag deleted");
                return findById(gCertId);
            } else {
                loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Delete Tag] Operation failed");
                throw new OperationFailedException("Bad request");
            }
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Delete Tag] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (gcCertId = " + gCertId + ", " +
                                        "tagId = " + tagId + ")");
        }
    }

    /**
     * If gCertId on creation wasn't corrupted it will be greater than 0.
     * Otherwise, it will be less or zero this method will produce OperationFailedException
     *
     * @param gCertId requested gift certificate id
     * @return {@code GiftCertificate} result of create
     */
    private GiftCertificate getGiftCertificateOnCreate(Long gCertId) {
        if (gCertId > 0) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Create] Object Created");
            return findById(gCertId);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Create] Operation Failed");
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Creates tags for gift certificate
     * If tags for gift certificate not null and if they are not empty will try to create every tag from
     * source gift certificates' tag set, else will do nothing
     *
     * @param gCert requested gift certificate
     * @param gCertId requested gift certificate id
     */
    private void createTags(GiftCertificate gCert, Long gCertId) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Tags Creation] Started");
        if (gCert.getTags() != null && !gCert.getTags().isEmpty()) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Creation] Passed checks");
            gCert.getTags().forEach(addTags(gcDao.findById(gCertId)));
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Creation] Tags added");
        }
    }

    /**
     * If update wasn't corrupted it will return updated gift certificate
     * Otherwise, it will produce OperationFailedException
     *
     * @param gCert requested gift certificate
     * @return {@code GiftCertificate} result of update
     */
    private GiftCertificate getGiftCertificateOnUpdate(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Update] Full object update");
        if (gcDao.update(gCert)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Update] Object updated");
            return findById(gCert.getId());
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Update] Operation Failed");
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * If delete wasn't corrupted it will return deleted gift certificate
     * Otherwise, it will produce OperationFailedException
     *
     * @param gCert requested gift certificate
     * @param id requested gift certificate id
     * @return {@code GiftCertificate} result of delete
     */
    private GiftCertificate getGiftCertificateOnDelete(GiftCertificate gCert, Long id) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Delete] Operation confirmed");
        if (gcDao.delete(id)) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Delete] Object deleted");
            return gCert;
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Delete] Operation Failed");
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Updates tags for gift certificate
     * If tags for gift certificate aren't null it will recreate pre update object and refine tags for to update
     * object (will find full copies for each tag if they exist)
     * Next, will check if tags for to update objects is empty, if so it will wipe from result tables any tied
     * tags, else will compare sets from pre update and to update objects, drop redundant tags (tags, which
     * to update object doesn't have and pre update have) and add missing ones (tags, which to update object have
     * and pre update doesn't)
     * It tags for gift certificate is null will do nothing
     *
     * @param gCert requested gift certificate
     */
    private void updateTags(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Tags Updating] Started");
        if (gCert.getTags() != null) {
            loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Updating] Null check passed");
            GiftCertificate origin = getReplica(gCert);
            loggerService.post(LoggerLevel.INFO,
                      "[Gift Certificate] [Tags Updating] Copy of pre update object obtained");
            GiftCertificate refined = refineUpdate(gCert);
            loggerService.post(LoggerLevel.INFO,
                      "[Gift Certificate] [Tags Updating] Tags refined for to update object");

            if (gCert.getTags().isEmpty()) {
                loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Updating] Tags is empty");
                origin.getTags().forEach(t -> gcDao.deleteTag(gCert.getId(), t.getId()));
                loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Updating] Tags removed");
            } else {
                loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Tags Updating] Tags is not empty");
                refined.getTags().stream().filter(t -> !origin.getTags().contains(t)).forEach(addTags(refined));
                loggerService.post(LoggerLevel.INFO,
                          "[Gift Certificate] [Tags Updating] Appropriate tags addition");
                origin.getTags().stream().filter(t -> !refined.getTags().contains(t)).forEach(deleteTags(refined));
                loggerService.post(LoggerLevel.INFO,
                          "[Gift Certificate] [Tags Updating] Redundant tags deletion");
            }
        }
    }

    /**
     * Recreates pre update object
     *
     * @param gCert requested gift certificate
     * @return {@code GiftCertificate} result of recreation
     */
    private GiftCertificate getReplica(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Replica Obtaining] Started");
        GiftCertificate replica = gcDao.findById(gCert.getId());
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Replica Obtaining] Object obtained");
        replica.setTags(gcDao.findTags(gCert.getId()));
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Replica Obtaining] Tags obtained");
        return replica;
    }

    /**
     * Reassembles to update object with full tags
     *
     * @param gCert requested gift certificate
     * @return {@code GiftCertificate} result of reassembling
     */
    private GiftCertificate refineUpdate(GiftCertificate gCert) {
        loggerService.post(LoggerLevel.WARN, "[Gift Certificate] [Update Construction] Started");
        Set<Tag> refinedTags = new LinkedHashSet<>();
        gCert.getTags().forEach(refineTags(refinedTags));
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Update Construction] Tags refined");
        gCert.setTags(refinedTags);
        loggerService.post(LoggerLevel.INFO, "[Gift Certificate] [Update Construction] Tags set");
        return gCert;
    }

    /**
     * Creates tag for gift certificate
     * If tag is not null and exists by name will tie gift certificate and tag in result table
     * Otherwise, it will create tag and then tie it in result table
     *
     * @param gCert requested gift certificate
     * @return {@code Consumer<Tag>} functional interface
     */
    private Consumer<Tag> addTags(GiftCertificate gCert) {
        return t -> {
            if (t != null && tagDao.existByName(t.getName())) {
                gcDao.addTag(gCert.getId(), tagDao.findByName(t.getName()).getId());
            } else {
                gcDao.addTag(gCert.getId(), tagDao.create(t));
            }
        };
    }

    /**
     * Removes tag from gift certificate
     *
     * @param gCert requested gift certificate
     * @return {@code Consumer<Tag>} functional interface
     */
    private Consumer<Tag> deleteTags(GiftCertificate gCert) {
        return t -> gcDao.deleteTag(gCert.getId(), t.getId());
    }

    /**
     * Refines tag for to update object
     * If it already exists, it will recreate tag and add to requested set
     * Otherwise, will add to requested set
     *
     * @param refined requested tag set
     * @return {@code Consumer<Tag>} functional interface
     */
    private Consumer<Tag> refineTags(Set<Tag> refined) {
        return t -> {
            if (tagDao.existByName(t.getName())) {
                refined.add(tagDao.findByName(t.getName()));
            } else {
                refined.add(t);
            }
        };
    }
}