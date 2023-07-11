package epam.com.esm.model.facade.impl.products;

import epam.com.esm.model.facade.interfaces.entity.products.TagFacade;
import epam.com.esm.model.service.interfaces.entity.products.TagService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.verifiers.products.TagDtoVerifier;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import static epam.com.esm.utils.converters.dto.products.TagDtoConverter.toDto;

/**
 * TagFacadeImpl is the service class and implementor of TagFacade interface
 * Validates and adjusts tag dto request before forwarding it to service and converts to dto response
 * after receiving answer from service
 */
@Service
public class TagFacadeImpl implements TagFacade {

    /**
     * Holds TagService object
     */
    private final TagService tagService;

    /**
     * Holds TagDtoVerifier object
     */
    private final TagDtoVerifier tagDtoVerifier;

    /**
     * Constructs TagFacadeImpl with TagService and TagDtoVerifier objects
     *
     * @param tagService service, provides logic operations for tags
     * @param tagDtoVerifier service, provides validations operations for tags
     */
    @Autowired
    public TagFacadeImpl(TagService tagService, TagDtoVerifier tagDtoVerifier) {
        this.tagService = tagService;
        this.tagDtoVerifier = tagDtoVerifier;
    }

    /**
     * Consumes dto request, validates it and produces dto response as the result of creation
     *
     * @param dto requested object, holds requested values for tag
     * @return {@code TagDtoResponse} created tag
     */
    @Override
    public TagDtoResponse create(TagDtoRequest dto) {
        return toDto(tagService.create(tagDtoVerifier.verifyOnCreate(dto)), true);
    }

    /**
     * Consumes id parameter value, finds tag and produces dto response if tag was found
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code TagDtoResponse} found tag
     */
    @Override
    public TagDtoResponse findById(Long id) {
        return toDto(tagService.findById(id), true);
    }

    /**
     * Consumes id value, deletes tag and produces dto response if tag was deleted
     *
     * @param id requested parameter value, holds tag id value
     * @return {@code TagDtoResponse} deleted tag
     */
    @Override
    public TagDtoResponse delete(Long id) {
        return toDto(tagService.delete(id), true);
    }

    /**
     * Consumes web request, finds by its URL request params tags and produces page data response as
     * the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, holds found tags and search response params
     */
    @Override
    public PageDataResponse<TagDtoResponse> findAll(WebRequest webRequest) {
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        return adh.processOutput(tagService.findAll(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request, finds by its URL request params tags with gift certificates and produces page
     * data response as the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, holds found tags with gift certificates
     * and search response params
     */
    @Override
    public PageDataResponse<TagDtoResponse> findAllCertificated(WebRequest webRequest) {
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        return adh.processOutput(tagService.findAllCertificated(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request, finds by its URL request params tags with no gift certificates and produces page
     * data response as the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, holds found tags with no gift certificates
     * and search response params
     */
    @Override
    public PageDataResponse<TagDtoResponse> findAllNotCertificated(WebRequest webRequest) {
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        return adh.processOutput(tagService.findAllNotCertificated(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request and gift certificate id parameter value, finds by its URL request params gift certificates
     * of significant tag and produces page data response as the result of search
     *
     * @param tagId requested parameter value, holds tag id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, holds found gift certificates and search
     * response params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> findGiftCertificates(Long tagId, WebRequest webRequest) {
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        return adh.processOutput(tagService.findGiftCertificates(tagId, adh.processSearch(webRequest)));
    }

    /**
     * Consumes parameter value id and requested dto, adds gift certificates to tag and produces dto response as the
     * result of adding
     *
     * @param id requested parameter value, holds requested tag id value
     * @param dto requested gift certificates, holds requested values
     * @return {@code TagDtoResponse} updated tag
     */
    @Override
    public TagDtoResponse addGiftCertificates(Long id, TagGiftCertificatesDtoRequest dto) {
        return toDto(
                tagService.addGiftCertificates(tagDtoVerifier.verifyChanges(dto, tagService.findById(id))), true
        );
    }

    /**
     * Consumes parameter value id and requested dto, adds gift certificates for tag and produces dto response as the
     * result of deleting
     *
     * @param id requested parameter value, holds requested tag id value
     * @param dto requested gift certificates, holds requested values
     * @return {@code TagDtoResponse} updated tag
     */
    @Override
    public TagDtoResponse deleteGiftCertificates(Long id, TagGiftCertificatesDtoRequest dto) {
        return toDto(
                tagService.deleteGiftCertificates(tagDtoVerifier.verifyChanges(dto, tagService.findById(id))), true
        );
    }
}