package epam.com.esm.utils.search.data.impl.user;

import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.utils.converters.dto.user.UserDtoConverter;
import epam.com.esm.utils.search.data.AbstractDataHandler;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.filter.handlers.WebRequestHandler;
import epam.com.esm.utils.search.request.handlers.ResponseHandler;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDataHandler is the service class, provides operations for searching by User
 */
public class UserDataHandler extends AbstractDataHandler<User, UserDtoResponse> {

    /**
     * Converts web request to search param request, transfers params between them
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    @Override
    public SearchParamRequest processSearch(WebRequest webRequest) {
        PageDataRequest dataRequest = new PageDataRequest(webRequest);
        DefaultLoader dl = new DefaultLoader("u.id", "asc", 1, 10, false);
        return WebRequestHandler.convertToParamRequest(dataRequest, User.class, dl);
    }

    /**
     * Converts search param response to page data response, transfers params and found items between them
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<UserDtoResponse>} found users with requested params
     */
    @Override
    public PageDataResponse<UserDtoResponse> processOutput(SearchParamResponse<User> spResp) {
        PageDataResponse<UserDtoResponse> dataResponse = ResponseHandler.convertToPageData(spResp);

        if (!spResp.isFold()) {
            dataResponse.setItems(getUnfolded(spResp));
        } else {
            dataResponse.setItems(getFolded(spResp));
        }

        return dataResponse;
    }

    /**
     * Generates unfolded view of users
     *
     * @param spResp requested search param response
     * @return {@code List<UserDtoResponse>} unfolded users
     */
    private List<UserDtoResponse> getUnfolded(SearchParamResponse<User> spResp) {
        return spResp.getItems().stream().map(
                u -> UserDtoConverter.toDto(u, true)).collect(Collectors.toCollection(ArrayList::new)
        );
    }

    /**
     * Generates folded view of users
     *
     * @param spResp requested search param response
     * @return {@code List<UserDtoResponse>} folded users
     */
    private List<UserDtoResponse> getFolded(SearchParamResponse<User> spResp) {
        return spResp.getItems().stream().map(
                u -> UserDtoConverter.toDto(u, false)).collect(Collectors.toCollection(ArrayList::new)
        );
    }
}