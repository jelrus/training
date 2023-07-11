package epam.com.esm.utils.search.data;

import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.DtoResponse;
import org.springframework.web.context.request.WebRequest;

/**
 * AbstractDataHandler is the abstract class, serves as parent class to all AbstractDataHandlers children
 * Provides abstract methods to make search via web request
 *
 * @param <E> describes POJO object class
 * @param <A> describes DTO object class
 */
public abstract class AbstractDataHandler<E extends BaseEntity, A extends DtoResponse> {

    /**
     * Delegates contract for converting web request to search param request
     *
     * @param webRequest requested web request
     * @return {@code SearchParamRequest} search request
     */
    public abstract SearchParamRequest processSearch(WebRequest webRequest);

    /**
     * Delegates contract for converting search param response to page data response
     *
     * @param spResp requested search param response
     * @return {@code PageDataResponse<A>} result of search
     */
    public abstract PageDataResponse<A> processOutput(SearchParamResponse<E> spResp);
}