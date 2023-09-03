package epam.com.esm.model.facade.interfaces.entity.products;

import epam.com.esm.model.facade.interfaces.base.CrudFacade;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * GiftCertificateFacade is the interface that delegates CRUD contracts from ancestors and specific operations for
 * gift certificate logic contracts to implementor
 */
public interface GiftCertificateFacade extends CrudFacade<GiftCertificateDtoRequest, GiftCertificateDtoResponse, Long> {

    /**
     * Contract for finding gift certificates with tags only and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, contains found gift certificates dto
     * responses and response params
     */
    PageDataResponse<GiftCertificateDtoResponse> findAllTagged(WebRequest webRequest);

    /**
     * Contract for finding gift certificates with no tags only and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} object, contains found gift certificates dto
     * responses and response params
     */
    PageDataResponse<GiftCertificateDtoResponse> findAllNotTagged(WebRequest webRequest);

    /**
     * Contract for finding tags and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, contains found tag dto responses and response params
     */
    PageDataResponse<TagDtoResponse> findTags(Long gCertId, WebRequest webRequest);

    /**
     * Contract for adding tags and producing gift certificate dto response as the result
     *
     * @param id requested parameter, holds requested gift certificate id value
     * @param dto requested gift certificate, holds requested values
     * @return {@code GiftCertificateDtoResponse} response object
     */
    GiftCertificateDtoResponse addTags(Long id, GiftCertificateTagsDtoRequest dto);

    /**
     * Contract for deleting tags and producing gift certificate dto response as the result
     *
     * @param id requested parameter, holds requested gift certificate id value
     * @param dto requested gift certificate, holds requested values
     * @return {@code GiftCertificateDtoResponse} response object
     */
    GiftCertificateDtoResponse deleteTags(Long id, GiftCertificateTagsDtoRequest dto);
}