package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.InputException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.interfaces.entity.products.GiftCertificateService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.repository.crud.BaseCrudRepository;
import epam.com.esm.persistence.repository.impl.products.GiftCertificateRepository;
import epam.com.esm.persistence.repository.impl.products.TagRepository;
import epam.com.esm.utils.search.request.builders.SpecificationFilter;
import epam.com.esm.utils.search.request.builders.SpecificationUtil;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * GiftCertificateServiceImpl class is the service class and implementor of GiftCertificateService interface.
 * Provides business logic operations for gift certificate
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Holds GiftCertificateRepository object
     */
    private final GiftCertificateRepository gcRepo;

    /**
     * Holds TagRepository object
     */
    private final TagRepository tRepo;

    /**
     * Holds BaseCrudRepository object
     */
    private final BaseCrudRepository<GiftCertificate, GiftCertificateRepository> crudRepo;

    /**
     * Constructs GiftCertificateServiceImpl with GiftCertificateRepository, TagRepository and
     * BaseCrudRepository objects
     *
     * @param gcRepo   repository, provides jpa operations for gift certificate
     * @param tRepo    repository, provides jpa operations for tag
     * @param crudRepo service, provides jpa crud operations
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository gcRepo,
                                      TagRepository tRepo,
                                      BaseCrudRepository<GiftCertificate, GiftCertificateRepository> crudRepo) {
        this.gcRepo = gcRepo;
        this.tRepo = tRepo;
        this.crudRepo = crudRepo;
    }

    /**
     * Creates gift certificate
     * Consumes requested gift certificate object, adjusts it and produces created gift certificate object as
     * the response
     *
     * @param gCert provided gift certificate object for creation
     * @return {@code GiftCertificate} created gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GiftCertificate create(GiftCertificate gCert) {
        checkNameExistence(gCert);
        refineGiftCertificate(gCert);
        return crudRepo.create(gcRepo, gCert);
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
        return crudRepo.findById(gcRepo, id).orElseThrow(
                () -> new NotFoundException("Gift Certificate with (id = " + id + ") not found")
        );
    }

    /**
     * Updates gift certificate
     * Consumes requested gift certificate object, adjusts it and produces updated gift certificate object as
     * the response
     *
     * @param gCert provided gift certificate object for update
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate update(GiftCertificate gCert) {
        checkIdExistence(gCert.getId());
        checkNameOnUpdate(gCert);
        refineGiftCertificate(gCert);
        return crudRepo.update(gcRepo, gCert);
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
        return crudRepo.delete(gcRepo, id);
    }

    /**
     * Finds all gift certificates
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAll(SearchParamRequest spReq) {
        return crudRepo.findAll(gcRepo, spReq, GiftCertificate.class, Tag.class);
    }

    /**
     * Finds all gift certificates with tags
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates with tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAllTagged(SearchParamRequest spReq) {
        SpecificationFilter<GiftCertificate> filterSpecs = new SpecificationFilter<>(
                spReq, GiftCertificate.class, Tag.class
        );
        SpecificationUtil<GiftCertificate> util = new SpecificationUtil<>();
        Specification<GiftCertificate> spec = util.notNullByParam("tags", "id", Long.class).and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = gcRepo.findAll(spec).size();
        List<GiftCertificate> gCerts = gcRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, gCerts);
    }

    /**
     * Finds all gift certificates with no tags
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<GiftCertificate>} object, holds response search params and found
     * gift certificates with no tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<GiftCertificate> findAllNotTagged(SearchParamRequest spReq) {
        SpecificationFilter<GiftCertificate> filterSpecs = new SpecificationFilter<>(
                spReq, GiftCertificate.class, Tag.class
        );
        SpecificationUtil<GiftCertificate> util = new SpecificationUtil<>();
        Specification<GiftCertificate> spec = util.nullByParam("tags", "id", Long.class).and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = gcRepo.findAll(spec).size();
        List<GiftCertificate> gCerts = gcRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, gCerts);
    }

    /**
     * Finds tags by specified gift certificate id
     *
     * @param gcId requested parameter value, holds gift certificate id value
     * @param spReq requested object, holds search params values and found items
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findTags(Long gcId, SearchParamRequest spReq) {
        checkIdExistence(gcId);

        SpecificationFilter<Tag> filterSpecs = new SpecificationFilter<>(spReq, Tag.class, GiftCertificate.class);
        SpecificationUtil<Tag> util = new SpecificationUtil<>();
        Specification<Tag> spec = util.idJoinEquals(gcId, "giftCertificates", "id").and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = tRepo.findAll(spec).size();
        List<Tag> tags = tRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, tags);
    }

    /**
     * Adds tags to gift certificate
     *
     * @param gCert object, holds requested values for gift certificate
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate addTags(GiftCertificate gCert) {
        checkIdExistence(gCert.getId());
        adjustUpdateTime(gCert);
        refineGiftCertificate(gCert);
        gCert.getTags().forEach(addTag(gCert));
        return getGiftCertificate(gCert.getId());
    }

    /**
     * Deletes tags from gift certificate
     *
     * @param gCert object, holds requested values for gift certificate
     * @return {@code GiftCertificate} updated gift certificate
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GiftCertificate deleteTags(GiftCertificate gCert) {
        checkIdExistence(gCert.getId());
        adjustUpdateTime(gCert);
        refineGiftCertificate(gCert);
        gCert.getTags().forEach(deleteTag(gCert));
        return getGiftCertificate(gCert.getId());
    }

    /**
     * Supplementary method, adjusts update time of updated gift certificate
     *
     * @param preUpdate gift certificate with request values
     */
    private void adjustUpdateTime(GiftCertificate preUpdate) {
        GiftCertificate gc = gcRepo.findById(preUpdate.getId()).get();
        gc.setUpdate(preUpdate.getUpdate());
        gcRepo.save(gc);
    }

    /**
     * Supplementary method, refines tags' missing values
     *
     * @param gCert requested gift certificate
     */
    private void refineGiftCertificate(GiftCertificate gCert) {
        if (gCert.getTags() != null && !gCert.getTags().isEmpty()) {
            gCert.setTags(gCert.getTags().stream().map(refineTag()).collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    /**
     * Function for refining tag's missing values
     *
     * @return {@code Function<Tag, Tag>} function, takes tag with missing values and returns refined tag
     */
    private Function<Tag, Tag> refineTag() {
        return t -> {
            if (tRepo.existsByName(t.getName())) {
                return tRepo.findByName(t.getName()).get();
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
            if (!gcRepo.findById(gCert.getId()).get().getTags().contains(t)) {
                if (tRepo.existsByName(t.getName())) {
                    gcRepo.findById(gCert.getId()).get().getTags().add(t);
                } else {
                    gcRepo.findById(gCert.getId()).get().getTags().add(tRepo.save(t));
                }
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
            if (gcRepo.findById(gCert.getId()).get().getTags().contains(t)) {
                if (tRepo.existsById(t.getId())) {
                    gcRepo.findById(gCert.getId()).get().getTags().remove(tRepo.findById(t.getId()).get());
                }
            }
        };
    }

    /**
     * Supplementary method, returns gift certificate if exists by id
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificate} requested gift certificate
     */
    private GiftCertificate getGiftCertificate(Long id) {
        checkIdExistence(id);
        return gcRepo.findById(id).get();
    }

    /**
     * Supplementary method, checks gift certificate existence by name
     *
     * @param gCert requested gift certificate
     */
    private void checkNameExistence(GiftCertificate gCert) {
        if (gcRepo.existsByName(gCert.getName())) {
            throw new AlreadyExistsException("GiftCertificate with (name = " + gCert.getName() + ") already exists");
        }
    }

    /**
     * Supplementary method, checks gift certificate existence by id
     *
     * @param id requested parameter value, holds gift certificate id value
     */
    private void checkIdExistence(Long id) {
        if (!gcRepo.existsById(id)) {
            throw new NotFoundException("Requested resource wasn't found (id = " + id + ")");
        }
    }

    /**
     * Supplementary method, checks if gift certificate's name suitable for update
     *
     * @param gCert requested gift certificate
     */
    private void checkNameOnUpdate(GiftCertificate gCert) {
        if (checkNameAndCompareIds(gCert)) {
            throw new InputException("Gift Certificate with (name = " + gCert.getName() + ") already exists");
        }
    }

    /**
     * Supplementary method, checks if new name for gift certificate doesn't exist or same and
     * compares ids if name already exists
     *
     * @param gCert requested gift certificate
     * @return {@code true} if check was successful
     */
    private boolean checkNameAndCompareIds(GiftCertificate gCert) {
        return gcRepo.existsByName(gCert.getName()) &&
               !Objects.equals(gcRepo.findByName(gCert.getName()).get().getId(), gCert.getId());
    }
}