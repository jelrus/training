package com.epam.esm.utils.search;

import com.epam.esm.persistence.entity.BaseEntity;
import com.epam.esm.utils.queries.GiftCertificateQuery;
import com.epam.esm.utils.search.request.DataRequest;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;

import java.util.Iterator;
import java.util.Map;

/**
 * RequestHandler is the utility class, which used for handling request and response operations
 */
public final class RequestHandler {

    /**
     * Constant field, which holds full parameter search key
     */
    private static final String FULL_SEARCH_FLAG = "f";

    /**
     * Constant field, which holds part parameter search key
     */
    private static final String PART_SEARCH_FLAG = "p";

    /**
     * Constant field, which holds sort parameter key
     */
    private static final String SORT_FLAG = "s";

    /**
     * Constant field, which holds divider between parameter key and input parameter name
     */
    private static final String DIVIDER = ":";

    /**
     * Default constructor
     */
    private RequestHandler() {}

    /**
     * Translates input request parameters to database request parameters, puts them into relevant map within
     * SearchParamRequest object to form database request in future
     *
     * @param dataRequest requested object
     * @return {@code SearchParamRequest} object, which holds translated params
     */
    public static SearchParamRequest translate(DataRequest dataRequest) {
        SearchParamRequest spr = new SearchParamRequest();

        for (Map.Entry<String, String[]> paramEntry: dataRequest.getFullParams().entrySet()) {
            getFullSearchParams(paramEntry, spr);
            getPartSearchParams(paramEntry, spr);
            getSortParams(paramEntry, spr);
        }

        return spr;
    }

    /**
     * Checks whether key and <b>first</b> value of the array argument is related to full search param
     *
     * @param paramEntry requested map entry
     * @return {@code true} if param entry contains full search param
     */
    private static boolean argCompliantForFullSearch(Map.Entry<String, String[]> paramEntry) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        return splitParams[0].equals(FULL_SEARCH_FLAG)
               && RequestDictionary.DICT.containsKey(splitParams[1])
               && !paramEntry.getValue()[0].isEmpty();
    }

    /**
     * Checks param entry for full param search arg:
     * - if true - puts key and value to full search param map in search param request
     * - if false - does nothing
     *
     * @param paramEntry requested map entry
     * @param spr requested search param request
     */
    private static void getFullSearchParams(Map.Entry<String, String[]> paramEntry, SearchParamRequest spr) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        if (argCompliantForFullSearch(paramEntry)) {
            spr.getFullSearch().put(RequestDictionary.DICT.get(splitParams[1]), paramEntry.getValue()[0]);
        }
    }

    /**
     * Checks whether key and <b>first</b> value of the array argument is related to part search param
     *
     * @param paramEntry requested map entry
     * @return {@code true} if param entry contains part search param
     */
    private static boolean argCompliantForPartSearch(Map.Entry<String, String[]> paramEntry) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        return splitParams[0].equals(PART_SEARCH_FLAG)
               && RequestDictionary.DICT.containsKey(splitParams[1])
               && !paramEntry.getValue()[0].isEmpty();
    }

    /**
     * Checks param entry for full part search arg:
     * - if true - puts key and value to part search param map in search param request
     * - if false - does nothing
     *
     * @param paramEntry requested map entry
     * @param spr requested search param request
     */
    private static void getPartSearchParams(Map.Entry<String, String[]> paramEntry, SearchParamRequest spr) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        if (argCompliantForPartSearch(paramEntry)) {
            spr.getPartSearch().put(RequestDictionary.DICT.get(splitParams[1]), paramEntry.getValue()[0]);
        }
    }

    /**
     * Checks whether key and <b>first</b> value of the array argument is related to sort param
     *
     * @param paramEntry requested map entry
     * @return {@code true} if param entry contains sort param
     */
    private static boolean argCompliantForSort(Map.Entry<String, String[]> paramEntry) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        return splitParams[0].equals(SORT_FLAG) && RequestDictionary.DICT.containsKey(splitParams[1]);
    }

    /**
     * Checks param entry for sort arg:
     * - if true - puts key and value to part search sort map in search param request
     * - if false - does nothing
     *
     * @param paramEntry requested map entry
     * @param spr requested search param request
     */
    private static void getSortParams(Map.Entry<String, String[]> paramEntry, SearchParamRequest spr) {
        String[] splitParams = paramEntry.getKey().split(DIVIDER);
        if (argCompliantForSort(paramEntry)) {
            if (sortParamEmpty(paramEntry)) {
                spr.getSortParams().put(RequestDictionary.DICT.get(splitParams[1]), "ASC");
            }

            if (sortParamNotEmpty(paramEntry)) {
                spr.getSortParams().put(RequestDictionary.DICT.get(splitParams[1]), paramEntry.getValue()[0]);
            }
        }
    }

    /**
     * Checks whether sort param contains no value
     *
     * @param paramEntry requested map entry
     * @return {@code true} if sort param has no value
     */
    private static boolean sortParamEmpty(Map.Entry<String, String[]> paramEntry) {
        return paramEntry.getValue().length == 0 || paramEntry.getValue()[0].isEmpty();
    }

    /**
     * Checks whether sort param contains sort value
     *
     * @param paramEntry requested map entry
     * @return {@code true} if sort param has sort value
     */
    private static boolean sortParamNotEmpty(Map.Entry<String, String[]> paramEntry) {
        return paramEntry.getValue()[0].equalsIgnoreCase("ASC")
               || paramEntry.getValue()[0].equalsIgnoreCase("DESC");
    }

    /**
     * Generates sql query based on the search params in search param request
     *
     * @param spr requested search param request
     * @return {@code String} sql query
     */
    public static String produceSqlQuery(SearchParamRequest spr) {
        StringBuffer sb = new StringBuffer(GiftCertificateQuery.FIND_PARAM_INIT);
        generateFullAndPartSearch(sb, spr);
        generateSort(sb, spr);
        return sb.toString();
    }

    /**
     * Generates string part for full and part search of sql query
     *
     * @param sb requested string buffer
     * @param spr requested search param request
     */
    private static void generateFullAndPartSearch(StringBuffer sb, SearchParamRequest spr) {
        if (!spr.getFullSearch().isEmpty() || !spr.getPartSearch().isEmpty()) {
            sb.append("WHERE ");
            generateFullSearch(sb, spr);
            generatePartSearch(sb, spr);
        }
    }

    /**
     * Generates string part for full search of sql query
     *
     * @param sb requested string buffer
     * @param spr requested search param request
     */
    private static void generateFullSearch(StringBuffer sb, SearchParamRequest spr) {
        if (!spr.getFullSearch().isEmpty()) {
            appendFullSearchParams(spr.getFullSearch().entrySet().iterator(), sb);
            appendAndIfPartSearchExists(sb, spr);
        }
    }

    /**
     * Checks whether part exist params exist and appends AND in string buffer
     *
     * @param sb requested string buffer
     * @param spr requested search param request
     */
    private static void appendAndIfPartSearchExists(StringBuffer sb, SearchParamRequest spr) {
        if (!spr.getPartSearch().isEmpty()) {
            sb.append("AND ");
        }
    }

    /**
     * Appends all full search params from param map to string buffer
     *
     * @param fsIt requested full search param map iterator
     * @param sb requested string buffer
     */
    private static void appendFullSearchParams(Iterator<Map.Entry<String, String>> fsIt, StringBuffer sb) {
        while (fsIt.hasNext()) {
            appendFullSearchParam(fsIt.next(), sb);
            appendAnd(fsIt, sb);
        }
    }

    /**
     * Appends full search param from param map to string buffer
     *
     * @param e requested param map entry
     * @param sb requested string buffer
     */
    private static void appendFullSearchParam(Map.Entry<String, String> e, StringBuffer sb) {
        sb.append(e.getKey()).append(" = ").append(e.getValue());
    }

    /**
     * Generates string part for part search of sql query
     *
     * @param sb requested string buffer
     * @param spr requested search param request
     */
    private static void generatePartSearch(StringBuffer sb, SearchParamRequest spr) {
        if (!spr.getPartSearch().isEmpty()) {
            appendPartSearchParams(spr.getPartSearch().entrySet().iterator(), sb);
        }
    }

    /**
     * Appends all part search params from param map to string buffer
     *
     * @param psIt requested full part param map iterator
     * @param sb requested string buffer
     */
    private static void appendPartSearchParams(Iterator<Map.Entry<String, String>> psIt, StringBuffer sb) {
        while (psIt.hasNext()) {
            appendPartSearchParam(psIt.next(), sb);
            appendAnd(psIt, sb);
        }
    }

    /**
     * Appends part search param from param map to string buffer
     *
     * @param e requested param map entry
     * @param sb requested string buffer
     */
    private static void appendPartSearchParam(Map.Entry<String, String> e, StringBuffer sb) {
        sb.append("INSTR(").append(e.getKey()).append(",").append(e.getValue()).append(")");
    }

    /**
     * Checks whether next full or part param exists:
     * - if true - appends AND in string buffer
     * - if false - appends SPACE character
     *
     * @param it requested parameter map iterator
     * @param sb requested string buffer
     */
    private static void appendAnd(Iterator<Map.Entry<String, String>> it, StringBuffer sb) {
        if (it.hasNext()) {
            sb.append(" AND ");
        } else {
            sb.append(" ");
        }
    }

    /**
     * Generates string part for sort of sql query
     *
     * @param sb requested string buffer
     * @param spr requested search param request
     */
    private static void generateSort(StringBuffer sb, SearchParamRequest spr) {
        if (!spr.getSortParams().isEmpty()) {
            sb.append("ORDER BY ");
            appendSortParams(spr.getSortParams().entrySet().iterator(), sb);
        }
    }

    /**
     * Appends all sort params from param map to string buffer
     *
     * @param sIt requested sort param map iterator
     * @param sb requested string buffer
     */
    private static void appendSortParams(Iterator<Map.Entry<String, String>> sIt, StringBuffer sb) {
        while (sIt.hasNext()) {
            appendSortParam(sIt.next(), sb);
            appendComa(sIt, sb);
        }
    }

    /**
     * Appends sort param from param map to string buffer
     *
     * @param e requested param map entry
     * @param sb requested string buffer
     */
    private static void appendSortParam(Map.Entry<String, String> e, StringBuffer sb) {
        sb.append(e.getKey()).append(" ").append(e.getValue());
    }

    /**
     * Checks if next sort param exists:
     * - if true - appends coma
     * - if false - does nothing
     *
     * @param sIt requested sort param map iterator
     * @param sb requested string buffer
     */
    private static void appendComa(Iterator<Map.Entry<String, String>> sIt, StringBuffer sb) {
        if (sIt.hasNext()) {
            sb.append(", ");
        }
    }

    /**
     * Gets search requests params and puts them into search response params map
     *
     * @param spReq requested search param request
     * @param spResp requested search param response
     */
    public static void scrubParams(SearchParamRequest spReq, SearchParamResponse<? extends BaseEntity> spResp) {
        scrubFullSearchParams(spReq, spResp);
        scrubPartSearchParams(spReq, spResp);
        scrubSortParams(spReq, spResp);
    }

    /**
     * Gets full search params from search param request and puts them into search params response map
     *
     * @param spReq requested search param request
     * @param spResp requested search param response
     */
    private static void scrubFullSearchParams(SearchParamRequest spReq,
                                              SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("Full Search", spReq.getFullSearch()
                                                       .entrySet()
                                                       .stream()
                                                       .map(x -> x.getKey() + "=" + x.getValue())
                                                       .toArray(String[]::new));
    }

    /**
     * Gets part search params from search param request and puts them into search params response map
     *
     * @param spReq requested search param request
     * @param spResp requested search param response
     */
    private static void scrubPartSearchParams(SearchParamRequest spReq,
                                              SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("Part Search", spReq.getPartSearch()
                                                       .entrySet()
                                                       .stream()
                                                       .map(x -> x.getKey() + "=" + x.getValue())
                                                       .toArray(String[]::new));
    }

    /**
     * Gets sort params from search param request and puts them into search params response map
     *
     * @param spReq requested search param request
     * @param spResp requested search param response
     */
    private static void scrubSortParams(SearchParamRequest spReq,
                                        SearchParamResponse<? extends BaseEntity> spResp) {
        spResp.getFullParams().put("Sort Params", spReq.getSortParams()
                                                       .entrySet()
                                                       .stream()
                                                       .map(x -> x.getKey() + " " + x.getValue())
                                                       .toArray(String[]::new));
    }
}