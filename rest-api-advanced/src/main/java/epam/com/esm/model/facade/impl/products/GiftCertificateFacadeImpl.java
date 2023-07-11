package epam.com.esm.model.facade.impl.products;

import epam.com.esm.model.facade.interfaces.entity.products.GiftCertificateFacade;
import epam.com.esm.model.service.interfaces.entity.products.GiftCertificateService;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.impl.products.GiftCertificateDataHandler;
import epam.com.esm.utils.search.data.impl.products.TagDataHandler;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.verifiers.products.GiftCertificateDtoVerifier;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import static epam.com.esm.utils.converters.dto.products.GiftCertificateDtoConverter.toDto;

/**
 * GiftCertificateFacadeImpl is the service class and implementor of GiftCertificateFacade interface
 * Validates and adjusts gift certificate dto request before forwarding it to service and converts to dto response
 * after receiving answer from service
 */
@Service
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    /**
     * Holds GiftCertificateService object
     */
    private final GiftCertificateService gcService;

    /**
     * Holds GiftCertificateDtoVerifier object
     */
    private final GiftCertificateDtoVerifier gcDtoVerifier;

    /**
     * Constructs GiftCertificateFacadeImpl with GiftCertificateService and GiftCertificateDtoVerifier objects
     *
     * @param gcService service, provides logic operations for gift certificates
     * @param gcDtoVerifier service, provides validations operations for gift certificates
     */
    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateService gcService, GiftCertificateDtoVerifier gcDtoVerifier) {
        this.gcService = gcService;
        this.gcDtoVerifier = gcDtoVerifier;
    }

    /**
     * Consumes dto request, validates it and produces dto response as the result of creation
     *
     * @param dto requested object, holds requested values for gift certificate
     * @return {@code GiftCertificateDtoResponse} created gift certificate
     */
    @Override
    public GiftCertificateDtoResponse create(GiftCertificateDtoRequest dto) {
        return toDto(gcService.create(gcDtoVerifier.verifyCreate(dto)), true);
    }

    /**
     * Consumes id parameter value, finds gift certificate and produces dto response if gift certificate was found
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificateDtoResponse} found gift certificate
     */
    @Override
    public GiftCertificateDtoResponse findById(Long id) {
        return toDto(gcService.findById(id), true);
    }

    /**
     * Consumes dto request and id parameter value, validates it and produces dto response as the result of update
     *
     * @param dto requested object, holds requested values for gift certificate
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificateDtoResponse} updated gift certificate
     */
    @Override
    public GiftCertificateDtoResponse update(GiftCertificateDtoRequest dto, Long id) {
        return toDto(gcService.update(gcDtoVerifier.verifyUpdate(dto, gcService.findById(id))), true);
    }

    /**
     * Consumes id value, deletes gift certificate and produces dto response if gift certificate was deleted
     *
     * @param id requested parameter value, holds gift certificate id value
     * @return {@code GiftCertificateDtoResponse} deleted gift certificate
     */
    @Override
    public GiftCertificateDtoResponse delete(Long id) {
        return toDto(gcService.delete(id), true);
    }

    /**
     * Consumes web request, finds by its URL request params gift certificates and produces page data response as
     * the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, holds found gift certificates and search
     * response params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> findAll(WebRequest webRequest) {
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        return adh.processOutput(gcService.findAll(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request, finds by its URL request params gift certificates with tags and produces page
     * data response as the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, holds found gift certificates with tags
     * and search response params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> findAllTagged(WebRequest webRequest) {
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        return adh.processOutput(gcService.findAllTagged(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request, finds by its URL request params gift certificates with no tags and produces page data
     * response as the result of search
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, holds found gift certificates with no tags
     * and search response params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> findAllNotTagged(WebRequest webRequest) {
        AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> adh = new GiftCertificateDataHandler();
        return adh.processOutput(gcService.findAllNotTagged(adh.processSearch(webRequest)));
    }

    /**
     * Consumes web request and gift certificate id parameter value, finds by its URL request params tags of significant
     * gift certificate and produces page data response as the result of search
     *
     * @param gCertId requested parameter value, holds gift certificate id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, holds found tags and search response params
     */
    @Override
    public PageDataResponse<TagDtoResponse> findTags(Long gCertId, WebRequest webRequest) {
        AbstractDataHandler<Tag, TagDtoResponse> adh = new TagDataHandler();
        return adh.processOutput(gcService.findTags(gCertId, adh.processSearch(webRequest)));
    }

    /**
     * Consumes parameter value id and requested dto, adds tags to gift certificate and produces dto response as the
     * result of adding
     *
     * @param id requested parameter value, holds requested gift certificate id value
     * @param dto requested tags, holds requested values
     * @return {@code GiftCertificateDtoResponse} updated gift certificate
     */
    @Override
    public GiftCertificateDtoResponse addTags(Long id, GiftCertificateTagsDtoRequest dto) {
        return toDto(gcService.addTags(gcDtoVerifier.verifyTagsChanges(dto, gcService.findById(id))), true);
    }

    /**
     * Consumes parameter value id and requested dto, deletes tags for gift certificate and produces dto response
     * as the result of deleting
     *
     * @param id requested parameter value, holds requested gift certificate id value
     * @param dto requested tags, holds requested values
     * @return {@code GiftCertificateDtoResponse} updated gift certificate
     */
    @Override
    public GiftCertificateDtoResponse deleteTags(Long id, GiftCertificateTagsDtoRequest dto) {
        return toDto(gcService.deleteTags(gcDtoVerifier.verifyTagsChanges(dto, gcService.findById(id))), true);
    }
}