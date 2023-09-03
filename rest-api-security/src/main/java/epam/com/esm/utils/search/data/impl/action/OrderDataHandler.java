package epam.com.esm.utils.search.data.impl.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.utils.converters.dto.action.OrderDtoConverter;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderDataHandler is the service class, provides operations for searching by Order
 */
public class OrderDataHandler extends AbstractDataHandler<Order, OrderDtoResponse> {

    /**
     * Converts web request to search param request, transfers params between them
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    @Override
    public SearchParamRequest processSearch(WebRequest webRequest) {
        PageDataRequest dataRequest = new PageDataRequest(webRequest);
        DefaultLoader dl = new DefaultLoader("o.id", "asc", 1, 10, false);
        return WebRequestHandler.convertToParamRequest(dataRequest, Order.class, dl);
    }

    /**
     * Converts search param response to page data response, transfers params and found items between them
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<OrderDtoResponse>} found orders with requested params
     */
    @Override
    public PageDataResponse<OrderDtoResponse> processOutput(SearchParamResponse<Order> spResp) {
        PageDataResponse<OrderDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);

        if (!spResp.isFold()) {
            dataResponse.setItems(getUnfolded(spResp));
        } else {
            dataResponse.setItems(getFolded(spResp));
        }

        return dataResponse;
    }

    /**
     * Generates unfolded view of order
     *
     * @param spResp requested search param response
     * @return {@code List<OrderDtoResponse>} unfolded orders
     */
    private List<OrderDtoResponse> getUnfolded(SearchParamResponse<Order> spResp) {
        return spResp.getItems().stream().map(
                o -> OrderDtoConverter.toDto(o, true)).collect(Collectors.toCollection(ArrayList::new)
        );
    }

    /**
     * Generates folded view of order
     *
     * @param spResp requested search param response
     * @return {@code List<OrderDtoResponse>} folded orders
     */
    private List<OrderDtoResponse> getFolded(SearchParamResponse<Order> spResp) {
        return spResp.getItems().stream().map(
                o -> OrderDtoConverter.toDto(o, false)).collect(Collectors.toCollection(ArrayList::new)
        );
    }
}