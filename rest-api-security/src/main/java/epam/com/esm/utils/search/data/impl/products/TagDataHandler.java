package epam.com.esm.utils.search.data.impl.products;

import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.utils.converters.dto.products.TagDtoConverter;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TagDataHandler is the service class, provides operations for searching by Tag
 */
public class TagDataHandler extends AbstractDataHandler<Tag, TagDtoResponse> {

    /**
     * Converts web request to search param request, transfers params between them
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    @Override
    public SearchParamRequest processSearch(WebRequest webRequest) {
        PageDataRequest dataRequest = new PageDataRequest(webRequest);
        DefaultLoader dl = new DefaultLoader("t.id", "asc", 1, 10, true);
        return WebRequestHandler.convertToParamRequest(dataRequest, Tag.class, dl);
    }

    /**
     * Converts search param response to page data response, transfers params and found items between them
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<TagDtoResponse>} found tags with requested params
     */
    @Override
    public PageDataResponse<TagDtoResponse> processOutput(SearchParamResponse<Tag> spResp) {
        PageDataResponse<TagDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);

        if (!spResp.isFold()) {
            dataResponse.setItems(getUnfolded(spResp));
        } else {
            dataResponse.setItems(getFolded(spResp));
        }

        return dataResponse;
    }

    /**
     * Generates unfolded view of tags
     *
     * @param spResp requested search param response
     * @return {@code List<TagDtoResponse>} unfolded tags
     */
    private List<TagDtoResponse> getUnfolded(SearchParamResponse<Tag> spResp) {
        return spResp.getItems().stream().map(
                t -> TagDtoConverter.toDto(t, true)).collect(Collectors.toCollection(ArrayList::new)
        );
    }

    /**
     * Generates folded view of tags
     *
     * @param spResp requested search param response
     * @return {@code List<TagDtoResponse>} folded tags
     */
    private List<TagDtoResponse> getFolded(SearchParamResponse<Tag> spResp) {
        return spResp.getItems().stream().map(
                t -> TagDtoConverter.toDto(t, false)).collect(Collectors.toCollection(ArrayList::new)
        );
    }
}
