package epam.com.esm.utils.search.request.handlers;

import epam.com.esm.exception.types.IncorrectUrlParameterException;
import epam.com.esm.exception.types.OperationFailedException;
import epam.com.esm.persistence.entity.BaseEntity;
import epam.com.esm.utils.hateoas.wrappers.type.PageType;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.search.transport.response.SearchParamResponse;
import epam.com.esm.view.dto.response.DtoResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * ResponseHandler is the util class, provides methods for data converting between
 * page data request -> search param request -> search param response -> page data response objects
 */
public final class ResponseHandler {

    /**
     * Default constructor
     */
    private ResponseHandler() {}

    /**
     * Generates search param response from provided search param request
     *
     * @param spReq provided search param request
     * @param total total found items size
     * @param items found items
     * @return {@code SearchParamResponse<E>} generated search param response
     * @param <E> describes class of generated search param response
     */
    public static <E extends BaseEntity> SearchParamResponse<E> initResponse(SearchParamRequest spReq, int total,
                                                                             List<E> items) {
        SearchParamResponse<E> spResp = scrubPaginationData(new ArrayList<>(items), total, spReq);
        scrubParams(spReq, spResp);
        return spResp;
    }

    /**
     * Generates page data response from search param response
     *
     * @param spResp provided search param response
     * @return {@code PageDataResponse<E>} generated page data response
     * @param <E> describes class of generated page data response
     */
    public static <E extends DtoResponse> PageDataResponse<E> convertToPageData(
                                                                    SearchParamResponse<? extends BaseEntity> spResp) {
        PageDataResponse<E> dataResponse = new PageDataResponse<>();
        dataResponse.setFullParams(spResp.getFullParams());
        dataResponse.setCurrentPage(spResp.getCurrentPage());
        dataResponse.setTotalPages(spResp.getTotalPages());
        dataResponse.setItemsShown(spResp.getShownItems());
        dataResponse.setItemsFound(spResp.getFoundItems());
        dataResponse.setMaxShown(spResp.getPageSize());
        dataResponse.setPages(spResp.getPages());
        dataResponse.setFold(spResp.isFold());
        return dataResponse;
    }

    /**
     * Transfers search request params to search param response
     *
     * @param items items found
     * @param total total found items size
     * @param spReq provided search param request
     * @return {@code SearchParamResponse<E>} generated search param response
     * @param <E> describes class of generated search param response
     */
    private static <E extends BaseEntity> SearchParamResponse<E> scrubPaginationData(List<E> items, int total,
                                                                                     SearchParamRequest spReq) {
        SearchParamResponse<E> spResp = new SearchParamResponse<>();
        spResp.setItems(items);
        spResp.setShownItems(spResp.getItems().size());
        spResp.setFoundItems(total);
        spResp.setFold(spReq.isFold());
        spResp.setCurrentPage(spReq.getPage() + 1);
        spResp.setPageSize(spReq.getSize());
        setTotalPages(spResp);
        checkPages(spResp);
        spResp.setPages(generatePageMap(spReq, spResp));
        return spResp;
    }

    /**
     * Checks for pages pool size
     *
     * @param spResp provided search param response
     */
    private static void checkPages(SearchParamResponse<? extends BaseEntity> spResp) {
        if (spResp.getCurrentPage() > spResp.getTotalPages()) {
            throw new IncorrectUrlParameterException(
                    "Page cannot be greater than total pages size. Check page parameter " +
                    "(current page = " + spResp.getCurrentPage() + "; " +
                    "total pages = " + spResp.getTotalPages() + ")."
            );
        }
    }

    /**
     * Calculates and sets total pages to search param response
     *
     * @param spResp provided search param response
     */
    private static void setTotalPages(SearchParamResponse<? extends BaseEntity> spResp) {
        if (!spResp.getItems().isEmpty()) {
            spResp.setTotalPages((int) Math.ceil((double) spResp.getFoundItems() / spResp.getPageSize()));
        } else {
            spResp.setTotalPages(1);
        }
    }

    /**
     * Transfers search and sort params from search param request to search param response
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     */
    private static void scrubParams(SearchParamRequest spReq, SearchParamResponse<? extends BaseEntity> spResp) {
        scrubFullSearchParams(spReq, spResp);
        scrubPartSearchParams(spReq, spResp);
        scrubSortParams(spReq, spResp);
    }

    /**
     * Transfers full search params from search param request to search param response
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     */
    private static void scrubFullSearchParams(SearchParamRequest spReq,
                                              SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("fullSearchParams", spReq.getFullParams()
                              .entrySet()
                              .stream()
                              .map(x -> x.getKey() + "=" + Arrays.toString(x.getValue().toArray(new String[0])))
                              .toArray(String[]::new));
    }

    /**
     * Transfers part search params from search param request to search param response
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     */
    private static void scrubPartSearchParams(SearchParamRequest spReq,
                                              SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("partSearchParams", spReq.getPartParams()
                              .entrySet()
                              .stream()
                              .map(x -> x.getKey() + "=" + Arrays.toString(x.getValue().toArray(new String[0])))
                              .toArray(String[]::new));
    }

    /**
     * Transfers sort params from search param request to search param response
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     */
    private static void scrubSortParams(SearchParamRequest spReq,
                                        SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("sortParams", spReq.getSortParams()
                              .entrySet()
                              .stream()
                              .map(x -> x.getKey() + " " + Arrays.toString(x.getValue().toArray(new String[0])))
                              .toArray(String[]::new));
    }

    /**
     * Generates page map for pagination, which provides url links with URL params for previous, current, next pages
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     * @return {@code Map<PageType, String>} generated page map
     */
    private static Map<PageType, String> generatePageMap(SearchParamRequest spReq,
                                                         SearchParamResponse<? extends BaseEntity> spResp) {
        Map<PageType, String> pageMap = new LinkedHashMap<>();

        if (spResp.getCurrentPage() - 1 > 0) {
            pageMap.put(PageType.PREV, scrubUrlParams(spReq, spResp, PageType.PREV));
        }

        pageMap.put(PageType.CURRENT, scrubUrlParams(spReq, spResp, PageType.CURRENT));

        if (spResp.getCurrentPage() + 1 <= spResp.getTotalPages()) {
            pageMap.put(PageType.NEXT, scrubUrlParams(spReq, spResp, PageType.NEXT));
        }

        return pageMap;
    }

    /**
     * Folds URL params for pages into string for search param response from search param request by page type
     * for pagination
     *
     * @param spReq provided search param request
     * @param spResp provided search param response
     * @param pageType provided page type
     * @return {@code String} generated URL for page
     */
    private static String scrubUrlParams(SearchParamRequest spReq, SearchParamResponse<? extends BaseEntity> spResp,
                                         PageType pageType) {
        StringBuffer sb = new StringBuffer("?");
        scrubPageUrlParams(spResp, sb, pageType);
        scrubSizeUrlParams(spResp, sb);
        scrubFoldParams(spResp, sb);
        scrubFilterParams(spReq, sb);
        return sb.toString();
    }

    /**
     * Folds URL pagination params into string for search param response by page type for pagination
     *
     * @param spResp provided search param response
     * @param pageType provided page type
     */
    private static void scrubPageUrlParams(SearchParamResponse<? extends BaseEntity> spResp, StringBuffer sb,
                                           PageType pageType) {
        if (pageType.equals(PageType.PREV)) {
            sb.append("page").append("=").append(spResp.getCurrentPage() - 1).append("&");
        }

        if (pageType.equals(PageType.CURRENT)) {
            sb.append("page").append("=").append(spResp.getCurrentPage()).append("&");
        }

        if (pageType.equals(PageType.NEXT)) {
            sb.append("page").append("=").append(spResp.getCurrentPage() + 1).append("&");
        }
    }

    /**
     * Folds URL size param into string for search param response for pagination
     *
     * @param spResp provided search param response
     * @param sb provided string buffer
     */
    private static void scrubSizeUrlParams(SearchParamResponse<? extends BaseEntity> spResp, StringBuffer sb) {
        sb.append("size").append("=").append(spResp.getPageSize()).append("&");
    }

    /**
     * Folds URL fold param into string for search param response for pagination
     *
     * @param spResp provided search param response
     * @param sb provided string buffer
     */
    private static void scrubFoldParams(SearchParamResponse<? extends BaseEntity> spResp, StringBuffer sb) {
        if (spResp.isFold()) {
            sb.append("fold").append("=").append("on").append("&");
        }

        if (!spResp.isFold()){
            sb.append("fold").append("=").append("off").append("&");
        }
    }

    /**
     * Folds URL search params into string for search param response for pagination
     *
     * @param spReq provided search param request
     * @param sb provided string buffer
     */
    private static void scrubFilterParams(SearchParamRequest spReq, StringBuffer sb) {
        if (spReq.getFullParams() != null && !spReq.getFullParams().isEmpty()) {
            spReq.getFullParams().forEach(appendUrlParams(sb, "f:"));
        }

        if (spReq.getPartParams() != null && !spReq.getPartParams().isEmpty()) {
            spReq.getPartParams().forEach(appendUrlParams(sb, "p:"));
        }

        if (spReq.getSortParams() != null && !spReq.getSortParams().isEmpty()) {
            spReq.getSortParams().forEach(appendUrlParams(sb, "s:"));
        }
    }

    /**
     * Function for appending URL params in string buffer
     *
     * @param sb provided string buffer
     * @param paramKey provided param key
     * @return {@code BiConsumer<String, List<String>>} map append function
     */
    private static BiConsumer<String, List<String>> appendUrlParams(StringBuffer sb, String paramKey) {
        return (k, v) -> v.forEach(y -> {
            try {
                sb.append(paramKey).append(k).append("=").append(URLEncoder.encode(y, "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                throw new OperationFailedException("Operation failed due bad request");
            }
        });
    }
}