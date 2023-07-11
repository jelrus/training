package epam.com.esm.utils.search.data.impl.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.utils.converters.dto.products.GiftCertificateDtoConverter;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GiftCertificateDataHandler is the service class, provides operations for searching by GiftCertificate
 */
public class GiftCertificateDataHandler extends AbstractDataHandler<GiftCertificate, GiftCertificateDtoResponse> {

    /**
     * Converts web request to search param request, transfers params between them
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    @Override
    public SearchParamRequest processSearch(WebRequest webRequest) {
        PageDataRequest dataRequest = new PageDataRequest(webRequest);
        DefaultLoader dl = new DefaultLoader("gc.id", "asc", 1, 10, false);
        return WebRequestHandler.convertToParamRequest(dataRequest, GiftCertificate.class, dl);
    }

    /**
     * Converts search param response to page data response, transfers params and found items between them
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<GiftCertificateDtoResponse>} found gift certificates with requested params
     */
    @Override
    public PageDataResponse<GiftCertificateDtoResponse> processOutput(SearchParamResponse<GiftCertificate> spResp) {
        PageDataResponse<GiftCertificateDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);

        if (!spResp.isFold()) {
            dataResponse.setItems(getUnfolded(spResp));
        } else {
            dataResponse.setItems(getFolded(spResp));
        }

        return dataResponse;
    }

    /**
     * Generates unfolded view of gift certificates
     *
     * @param spResp requested search param response
     * @return {@code List<GiftCertificateDtoResponse>} unfolded gift certificates
     */
    private List<GiftCertificateDtoResponse> getUnfolded(SearchParamResponse<GiftCertificate> spResp) {
        return spResp.getItems().stream().map(
                gc -> GiftCertificateDtoConverter.toDto(gc, true)).collect(Collectors.toCollection(ArrayList::new)
        );
    }

    /**
     * Generates folded view of gift certificates
     *
     * @param spResp requested search param response
     * @return {@code List<GiftCertificateDtoResponse>} folded gift certificates
     */
    private List<GiftCertificateDtoResponse> getFolded(SearchParamResponse<GiftCertificate> spResp) {
        return spResp.getItems().stream().map(
                gc -> GiftCertificateDtoConverter.toDto(gc, false)).collect(Collectors.toCollection(ArrayList::new)
        );
    }
}