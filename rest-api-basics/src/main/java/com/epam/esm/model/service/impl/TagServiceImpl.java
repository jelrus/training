package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.exception.types.AlreadyExistsException;
import com.epam.esm.exception.types.NotFoundException;
import com.epam.esm.exception.types.OperationFailedException;
import com.epam.esm.model.service.interfaces.entity.TagService;
import com.epam.esm.utils.logger.LoggerService;
import com.epam.esm.utils.logger.impl.type.LoggerLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * TagServiceImpl class is the service class and implementor of TagService interface.
 * Used to form requests based on lesser dao requests and manipulate Tag objects
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * Field to hold LoggerService object
     */
    private final LoggerService loggerService;

    /**
     * Field to hold TagDao object
     */
    private final TagDao tagDao;

    /**
     * Field to hold GiftCertificateDao object
     */
    private final GiftCertificateDao gcDao;

    /**
     * Constructs TagServiceImpl with TagDao and GiftCertificateDao objects, and LoggerService object
     * NOTE: If tag service and gift certificate or logger service objects is null it will create and inject this
     * dependency according to @Autowired annotation
     *
     * @param loggerService logger service, which writes logs
     * @param tagDao tag dao, which provides methods to manipulate tags on database level
     * @param gcDao gift certificate dao, which provides methods to manipulate gift certificates on database level
     */
    @Autowired
    public TagServiceImpl(LoggerService loggerService, TagDao tagDao, GiftCertificateDao gcDao) {
        this.loggerService = loggerService;
        this.tagDao = tagDao;
        this.gcDao = gcDao;
    }

    /**
     * Creates tag
     * Checks if requested Tag candidate is not exists by name:
     * - if true creates tag and holds its id
     * - if false throws AlreadyExistsException exception
     * <p>
     * Then creates requested gift certificates contained in candidate:
     * - if gift certificate already exists ties this tag in result table
     * - if gift certificate does not exist creates it in relevant table within database and
     * ties this gift certificate in result table
     * <p>
     * Checks whether GiftCertificate was created by id value check:
     * - if id value > 0 - returns created GiftCertificate
     * - if id value < 0 - throws OperationFailedException exception
     *
     * @param tag candidate for creation
     * @return {@code Tag} created object
     */
    @Override
    public Tag create(Tag tag) {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Create] Operation started");
        if (!tagDao.existByName(tag.getName())) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Create] Existence check passed");
            Long tagId = tagDao.create(tag);
            loggerService.post(LoggerLevel.INFO, "[Tag] [Create] Created. Key obtained.");
            createGiftCertificates(tag, tagId);
            loggerService.post(LoggerLevel.INFO, "[Tag] [Create] Gift certificates created");
            return getTagOnCreate(tagId);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Gift Certificate] [Create] Existence check not passed");
            throw new AlreadyExistsException("Resource already exists (id = " +
                                             tagDao.findByName(tag.getName()).getId() + ")");
        }
    }

    /**
     * Finds tag by id
     * Checks if tag exists:
     * - if true - finds tag by requested id and set gift certificate to it
     * - if false - throws NotFoundException exception
     *
     * @param id requested parameter
     * @return {@code Tag} found object
     */
    @Override
    public Tag findById(Long id) {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Find] Operation started");
        if (tagDao.existById(id)) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Find] Existence check passed");
            Tag tag = tagDao.findById(id);
            loggerService.post(LoggerLevel.INFO, "[Tag] [Find] Object obtained");
            tag.setGiftCertificates(tagDao.findGiftCertificates(id));
            loggerService.post(LoggerLevel.INFO, "[Tag] [Find] Gift certificates set. Object ready");
            return tag;
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Tag] [Find] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Deletes tag
     * Checks if tag exists by id:
     * - if true - finds tag, stores it within object, deletes from database
     * - if false - throws NotFoundException exception
     * <p>
     * Then checks if object was deleted from database:
     * - if true - return previously stored deleted entry
     * - if false - throws OperationFailedException exception
     *
     * @param id requested parameter
     * @return {@code Tag} deleted object
     */
    @Override
    public Tag delete(Long id) {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Delete] Operation started");
        if (tagDao.existById(id)) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Delete] Existence check passed");
            Tag tag = findById(id);
            loggerService.post(LoggerLevel.INFO, "[Tag] [Delete] Candidate object obtained");
            return getTagOnDelete(tag, id);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Tag] [Delete] Existence check not passed");
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Finds all tags, adds gift certificates for each
     *
     * @return {@code Set<Tag>} set of tags
     */
    @Override
    public Set<Tag> findAll() {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Find All] Operation started");
        return tagDao.findAll().stream().peek(t -> t.setGiftCertificates(tagDao.findGiftCertificates(t.getId())))
                                        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Finds all tags which have at least one gift certificate, adds gift certificates for each
     *
     * @return {@code Set<Tag>} set of tags
     */
    @Override
    public Set<Tag> findAllCertificated() {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Find All Certificated] Operation started");
        return tagDao.findAllCertificated().stream().peek(t -> t.setGiftCertificates(tagDao.findGiftCertificates(t.getId())))
                                           .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * If tagId on creation wasn't corrupted it will be greater than 0.
     * Otherwise, it will be less or zero this method will produce OperationFailedException
     *
     * @param tagId requested tag id
     * @return {@code Tag} result of create
     */
    private Tag getTagOnCreate(Long tagId) {
        if (tagId > 0) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Create] Object Created");
            return findById(tagId);
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Tag] [Create] Operation Failed");
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Creates gift certificates for tag.
     * If gift certificates for tag not null and if they are not empty will try to create every gift certificate from
     * source tag's gift certificates set, else will do nothing
     *
     * @param tag requested tag
     * @param tagId requested tag id
     */
    private void createGiftCertificates(Tag tag, Long tagId) {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Gift Certificates Creation] Started");
        if (tag.getGiftCertificates() != null && !tag.getGiftCertificates().isEmpty()) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Gift Certificates Creation] Passed checks");
            tag.getGiftCertificates().forEach(addGiftCertificates(tagDao.findById(tagId)));
            loggerService.post(LoggerLevel.INFO, "[Tag] [Gift Certificates Creation] Gift certificates added");
        }
    }

    /**
     * If delete operation was successful will return deleted tag otherwise will produce OperationFailedException
     *
     * @param tag requested tag
     * @param id requested id
     * @return {@code Tag} result of delete
     */
    private Tag getTagOnDelete(Tag tag, Long id) {
        loggerService.post(LoggerLevel.WARN, "[Tag] [Delete] Operation confirmed");
        if (tagDao.delete(id)) {
            loggerService.post(LoggerLevel.INFO, "[Tag] [Delete] Object deleted");
            return tag;
        } else {
            loggerService.post(LoggerLevel.ERROR, "[Tag] [Delete] Operation Failed");
            throw new OperationFailedException("Bad request");
        }
    }

    /**
     * Creates gift certificate for tag
     * If gift certificate is not null and exists by name will tie tag and gift certificate in result table
     * Otherwise, it will create gift certificate and then tie it in result table
     *
     * @param tag requested tag
     * @return {@code Consumer<GiftCertificate>} functional interface
     */
    private Consumer<GiftCertificate> addGiftCertificates(Tag tag) {
        return gc -> {
            if (gc != null && gcDao.existByName(gc.getName())) {
                gcDao.addTag(gcDao.findByName(gc.getName()).getId(), tag.getId());
            } else {
                gcDao.addTag(gcDao.create(gc), tag.getId());
            }
        };
    }
}