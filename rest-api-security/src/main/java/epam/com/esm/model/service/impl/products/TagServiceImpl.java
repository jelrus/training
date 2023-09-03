package epam.com.esm.model.service.impl.products;

import epam.com.esm.exception.types.AlreadyExistsException;
import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.model.service.interfaces.entity.products.TagService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.persistence.repository.BaseRepository;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static epam.com.esm.utils.search.request.handlers.ResponseHandler.initResponse;

/**
 * TagServiceImpl class is the service class and implementor of TagService interface.
 * Provides business logic operations for tag
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * Holds TagRepository object
     */
    private final TagRepository tRepo;

    /**
     * Holds GiftCertificateDao object
     */
    private final GiftCertificateRepository gcRepo;

    /**
     * Holds BaseCrudRepository object
     */
    private final BaseCrudRepository<Tag, BaseRepository<Tag>> baseRepo;

    /**
     * Constructs TagServiceImpl with TagRepository, GiftCertificateRepository and BaseCrudRepository objects
     *
     * @param tRepo    repository, provides jpa operations for tag
     * @param gcRepo   repository, provides jpa operations for gift certificate
     * @param baseRepo service, provides jpa crud operations
     */
    @Autowired
    public TagServiceImpl(TagRepository tRepo,
                          GiftCertificateRepository gcRepo,
                          BaseCrudRepository<Tag, BaseRepository<Tag>> baseRepo) {
        this.tRepo = tRepo;
        this.gcRepo = gcRepo;
        this.baseRepo = baseRepo;
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
        return baseRepo.create(tRepo, tag);
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
        return baseRepo.findById(tRepo, id).orElseThrow(
                () -> new NotFoundException("Tag with (id = " + id + ") not found")
        );
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
        return baseRepo.delete(tRepo, id);
    }

    /**
     * Finds all tags
     *
     * @param spReq object, holds requested params for search
     * @return {@code SearchParamResponse<Tag>} object, holds response search params and found tags
     */
    @Override
    @Transactional(readOnly = true)
    public SearchParamResponse<Tag> findAll(SearchParamRequest spReq) {
        return baseRepo.findAll(tRepo, spReq, Tag.class, GiftCertificate.class);
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
        SpecificationFilter<Tag> filterSpecs = new SpecificationFilter<>(spReq, Tag.class, GiftCertificate.class);
        SpecificationUtil<Tag> util = new SpecificationUtil<>();
        Specification<Tag> spec = util.notNullByParam("giftCertificates", "id", Long.class).and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = tRepo.findAll(spec).size();
        List<Tag> tags = tRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, tags);
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
        SpecificationFilter<Tag> filterSpecs = new SpecificationFilter<>(spReq, Tag.class, GiftCertificate.class);
        SpecificationUtil<Tag> util = new SpecificationUtil<>();
        Specification<Tag> spec = util.nullByParam("giftCertificates", "id", Long.class).and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = tRepo.findAll(spec).size();
        List<Tag> tags = tRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, tags);
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

        SpecificationFilter<GiftCertificate> filterSpecs = new SpecificationFilter<>(
                spReq, GiftCertificate.class, Tag.class
        );
        SpecificationUtil<GiftCertificate> util = new SpecificationUtil<>();
        Specification<GiftCertificate> spec = util.idJoinEquals(tagId, "tags", "id").and(filterSpecs);
        PageRequest pageRequest = PageRequest.of(spReq.getPage(), spReq.getSize());

        int total = gcRepo.findAll(spec).size();
        List<GiftCertificate> gCerts = gcRepo.findAll(spec, pageRequest).getContent();

        return initResponse(spReq, total, gCerts);
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
            if (gcRepo.existsByName(gc.getName())) {
                return gcRepo.findByName(gc.getName()).get();
            } else {
                throw new NotFoundException("Gift Certificate with (name = " + gc.getName() + ") not found");
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
            if (!tRepo.findById(tag.getId()).get().getGiftCertificates().contains(gc)) {
                if (gcRepo.existsById(gc.getId())) {
                    tRepo.findById(tag.getId()).get().getGiftCertificates().add(gc);
                }
            }
        };
    }

    /**
     * Function for deleting gift certificate from tag
     * Will add if gift certificate applied to tag and exists by id
     *
     * @param t requested gift certificate
     * @return {@code Consumer<GiftCertificate>} function, deletes gift certificate from tag
     */
    private Consumer<GiftCertificate> deleteGiftCertificate(Tag t) {
        return gc -> {
            if (tRepo.findById(t.getId()).get().getGiftCertificates().contains(gc)) {
                if (gcRepo.existsById(gc.getId())) {
                    tRepo.findById(t.getId()).get().getGiftCertificates().remove(gcRepo.findById(gc.getId()).get());
                }
            }
        };
    }

    /**
     * Supplementary method, returns tag if exists by id
     *
     * @param id requested parameter value, holds tag
     * @return {@code Tag} requested tag
     */
    private Tag getTag(Long id) {
        checkIdExistence(id);
        return tRepo.findById(id).get();
    }

    /**
     * Supplementary method, checks tag existence by name
     *
     * @param tag requested tag
     */
    private void checkNameExistence(Tag tag) {
        if (tRepo.existsByName(tag.getName())) {
            throw new AlreadyExistsException("Tag with (name = " + tag.getName() + ") already exists");
        }
    }

    /**
     * Supplementary method, checks tag existence by id
     *
     * @param id requested parameter value, holds tag id value
     */
    private void checkIdExistence(Long id) {
        if (!tRepo.existsById(id)) {
            throw new NotFoundException("Tag with (id = " + id + ") not found");
        }
    }
}