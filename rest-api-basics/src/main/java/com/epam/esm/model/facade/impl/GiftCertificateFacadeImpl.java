package com.epam.esm.model.facade.impl;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.model.facade.interfaces.entity.GiftCertificateFacade;
import com.epam.esm.model.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.utils.search.RequestHandler;
import com.epam.esm.utils.search.request.DataRequest;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.DataResponse;
import com.epam.esm.utils.search.response.SearchParamResponse;
import com.epam.esm.utils.verifiers.GiftCertificateDtoVerifier;
import com.epam.esm.view.dto.request.impl.GiftCertificateDtoRequest;
import com.epam.esm.view.dto.response.impl.GiftCertificateDtoResponse;
import com.epam.esm.view.dto.response.impl.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * GiftCertificateFacadeImpl is the service class and implementor of GiftCertificateFacade interface
 * Used for dto requests validation (refuses inappropriate requests) and gift certificate conversion to dto response
 */
@Service
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    /**
     * Field to hold GiftCertificateService object
     */
    private final GiftCertificateService gcService;

    /**
     * Constructs GiftCertificateFacadeImpl with GiftCertificateService object
     * NOTE: If gift certificate service object is null it will create and inject this dependency according
     * to @Autowired annotation
     *
     * @param gcService gift certificate service, which provides methods to manipulate gift certificates
     */
    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateService gcService) {
        this.gcService = gcService;
    }

    /**
     * Consumes dto request, verifies it and produces dto response as the result of creation
     *
     * @param dto requested object
     * @return {@code GiftCertificateDtoResponse} created object
     */
    @Override
    public GiftCertificateDtoResponse create(GiftCertificateDtoRequest dto) {
        return convertToDtoResp(gcService.create(GiftCertificateDtoVerifier.verifyCreate(dto)));
    }

    /**
     * Consumes id value, finds gift certificate and produces dto response if gift certificate was found
     *
     * @param id requested parameter
     * @return {@code GiftCertificateDtoResponse} object if found
     */
    @Override
    public GiftCertificateDtoResponse findById(Long id) {
        return convertToDtoResp(gcService.findById(id));
    }

    /**
     * Consumes dto request and id parameter, verifies it and produces dto response as the result of update
     *
     * @param dto requested object
     * @param id requested parameter
     * @return {@code GiftCertificateDtoResponse} updated object
     */
    @Override
    public GiftCertificateDtoResponse update(GiftCertificateDtoRequest dto, Long id) {
        return convertToDtoResp(gcService.update(GiftCertificateDtoVerifier.verifyUpdate(dto, gcService.findById(id))));
    }

    /**
     * Consumes id value, deletes gift certificate and produces dto response if tag was deleted
     *
     * @param id requested parameter
     * @return {@code GiftCertificateDtoResponse} deleted object
     */
    @Override
    public GiftCertificateDtoResponse delete(Long id) {
        return convertToDtoResp(gcService.delete(id));
    }

    /**
     * Produces set of dto responses on find all request
     *
     * @return {@code Set<GiftCertificateDtoResponse>} set of gift certificates dto responses
     */
    @Override
    public Set<GiftCertificateDtoResponse> findAll() {
        return gcService.findAll().stream().map(this::convertToDtoResp)
                                           .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Produces set of dto responses on find all tagged request
     *
     * @return {@code Set<GiftCertificateDtoResponse>} set of tag dto responses
     */
    @Override
    public Set<GiftCertificateDtoResponse> findAllTagged() {
        return gcService.findAllTagged().stream().map(this::convertToDtoResp)
                                                 .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Consumes web request to retrieve parameters from URL
     * Splits obtained data for search param request on separate maps
     * Generates search param response based on search param request
     * Produces data response as the result of param search
     *
     * @param webRequest object, which holds request parameters for search
     * @return {@code DataResponse<GiftCertificateDtoResponse>} object, which holds output param map and
     * set of gift certificates dto responses as the result of search
     */
    @Override
    public DataResponse<GiftCertificateDtoResponse> findAllSearch(WebRequest webRequest) {
        DataRequest dataRequest = new DataRequest(webRequest);
        DataResponse<GiftCertificateDtoResponse> dataResponse = new DataResponse<>();
        SearchParamRequest spReq = RequestHandler.translate(dataRequest);
        SearchParamResponse<GiftCertificate> spResp = gcService.findAllSearch(spReq);
        dataResponse.setFullParams(spResp.getFullParams());
        dataResponse.setItems(spResp.getItems().stream().map(this::convertToDtoResp)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        return dataResponse;
    }

    /**
     * Adds tag to gift certificate
     *
     * @param gCertId requested parameter, holds value for gift certificate id
     * @param tagId requested parameter, holds value for tag id
     * @return {@code GiftCertificateDtoResponse} object, as the result of add tag operation
     */
    @Override
    public GiftCertificateDtoResponse addTag(Long gCertId, Long tagId) {
        return convertToDtoResp(gcService.addTag(gCertId, tagId));
    }

    /**
     * Deletes tag from gift certificate
     *
     * @param gCertId requested parameter, holds value for gift certificate id
     * @param tagId requested parameter, holds value for tag id
     * @return {@code GiftCertificateDtoResponse} object, as the result of delete tag operation
     */
    @Override
    public GiftCertificateDtoResponse deleteTag(Long gCertId, Long tagId) {

        return convertToDtoResp(gcService.deleteTag(gCertId, tagId));
    }

    /**
     * Converts GiftCertificate object to GiftCertificateDtoResponse Object
     * If GiftCertificate tags are null or if they are empty will convert to GiftCertificateDtoResponse
     * without tags
     *
     * @param gCert requested object
     * @return {@code GiftCertificateDtoResponse} conversion result
     */
    private GiftCertificateDtoResponse convertToDtoResp(GiftCertificate gCert) {
        GiftCertificateDtoResponse dtoResp = new GiftCertificateDtoResponse(gCert);

        if (gCert.getTags() != null && !gCert.getTags().isEmpty()) {
            dtoResp.setTags(gCert.getTags().stream().map(TagDtoResponse::new)
                                                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }

        return dtoResp;
    }
}