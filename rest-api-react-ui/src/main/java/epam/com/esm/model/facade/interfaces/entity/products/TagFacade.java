package epam.com.esm.model.facade.interfaces.entity.products;

import epam.com.esm.model.facade.interfaces.base.CrdFacade;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * TagFacade is the interface that delegates CRUD contracts from ancestors and specific operations for
 * tag logic contracts to implementor
 */
public interface TagFacade extends CrdFacade<TagDtoRequest, TagDtoResponse, Long> {

    /**
     * Contract for finding tags with gift certificates only and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, contains found tag dto responses and response params
     */
    PageDataResponse<TagDtoResponse> findAllCertificated(WebRequest webRequest);

    /**
     * Contract for finding tags with no gift certificates only and producing PageDataResponse as the result
     *
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, contains found tag dto responses and response params
     */
    PageDataResponse<TagDtoResponse> findAllNotCertificated(WebRequest webRequest);

    /**
     * Contract for finding gift certificate by requested tag id and producing PageDataResponse as the result
     *
     * @param tagId requested parameter, holds requested tag id value
     * @param webRequest requested object, contains URL params
     * @return {@code PageDataResponse<TagDtoResponse>} object, contains found tag dto responses and response params
     */
    PageDataResponse<GiftCertificateDtoResponse> findGiftCertificates(Long tagId, WebRequest webRequest);

    /**
     * Contract for adding gift certificates and producing tag dto response as the result
     *
     * @param id requested parameter, holds requested tag id value
     * @param dto requested tag, holds requested values
     * @return {@code TagDtoResponse} response object
     */
    TagDtoResponse addGiftCertificates(Long id, TagGiftCertificatesDtoRequest dto);

    /**
     * Contract for deleting gift certificates and producing tag dto response as the result
     *
     * @param id requested parameter, holds requested tag id value
     * @param dto requested tag, holds requested values
     * @return {@code TagDtoResponse} response object
     */
    TagDtoResponse deleteGiftCertificates(Long id, TagGiftCertificatesDtoRequest dto);
}