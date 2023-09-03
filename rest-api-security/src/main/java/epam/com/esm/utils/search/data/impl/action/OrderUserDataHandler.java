package epam.com.esm.utils.search.data.impl.action;

import epam.com.esm.persistence.entity.impl.action.Order;
import epam.com.esm.utils.converters.dto.action.OrderUserDtoConverter;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderUserDataHandler is the service class, provides operations for searching by OrderUser
 */
public class OrderUserDataHandler extends AbstractDataHandler<Order, OrderUserDtoResponse> {

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
     * @return {@code PageDataResponse<OrderUserDtoResponse>} found orders with user with requested params
     */
    @Override
    public PageDataResponse<OrderUserDtoResponse> processOutput(SearchParamResponse<Order> spResp) {
        PageDataResponse<OrderUserDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);

        if (!spResp.isFold()) {
            dataResponse.setItems(getUnfolded(spResp));
        } else {
            dataResponse.setItems(getFolded(spResp));
        }

        return dataResponse;
    }

    /**
     * Generates unfolded view of order with user
     *
     * @param spResp requested search param response
     * @return {@code List<OrderUserDtoResponse>} unfolded orders with user
     */
    private List<OrderUserDtoResponse> getUnfolded(SearchParamResponse<Order> spResp) {
        return spResp.getItems().stream().map(
                ou -> OrderUserDtoConverter.toDto(ou, true)).collect(Collectors.toCollection(ArrayList::new)
        );
    }

    /**
     * Generates folded view of order with user
     *
     * @param spResp requested search param response
     * @return {@code List<OrderUserDtoResponse>} folded orders with user
     */
    private List<OrderUserDtoResponse> getFolded(SearchParamResponse<Order> spResp) {
        return spResp.getItems().stream().map(
                ou -> OrderUserDtoConverter.toDto(ou, false)).collect(Collectors.toCollection(ArrayList::new)
        );
    }
}