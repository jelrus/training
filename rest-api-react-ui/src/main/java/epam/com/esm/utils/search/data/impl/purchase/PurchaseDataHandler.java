package epam.com.esm.utils.search.data.impl.purchase;

import epam.com.esm.persistence.entity.impl.purchase.PurchaseData;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * PurchaseDataHandler is the service class, provides operations for searching by PurchaseData
 */
public class PurchaseDataHandler extends AbstractDataHandler<PurchaseData, PurchaseDataDtoResponse> {

    /**
     * Converts web request to search param request, transfers params between them
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    @Override
    public SearchParamRequest processSearch(WebRequest webRequest) {
        PageDataRequest dataRequest = new PageDataRequest(webRequest);
        DefaultLoader dl = new DefaultLoader("pd.id", "asc", 1, 10, false);
        return WebRequestHandler.convertToParamRequest(dataRequest, PurchaseData.class, dl);
    }

    /**
     * Converts search param response to page data response, transfers params and found items between them
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<PurchaseDataDtoResponse>} found purchase data with requested params
     */
    @Override
    public PageDataResponse<PurchaseDataDtoResponse> processOutput(SearchParamResponse<PurchaseData> spResp) {
        PageDataResponse<PurchaseDataDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);
        dataResponse.setItems(
                spResp.getItems().stream().map(p -> new PurchaseDataDtoResponse(p, dataResponse.isFold()))
                                          .collect(Collectors.toCollection(ArrayList::new))
        );
        return dataResponse;
    }
}