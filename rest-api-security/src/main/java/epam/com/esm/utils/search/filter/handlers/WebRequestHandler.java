package epam.com.esm.utils.search.filter.handlers;

import epam.com.esm.exception.types.IncorrectUrlParameterException;
import epam.com.esm.utils.search.data.components.DefaultLoader;
import epam.com.esm.utils.search.transport.request.PageDataRequest;
import epam.com.esm.utils.search.transport.request.SearchParamRequest;

import java.util.*;
import java.util.function.Consumer;

/**
 * WebRequestHandler is the service class, provides methods to manipulate search params
 */
public final class WebRequestHandler {

    /**
     * Holds full search key
     */
    private static final String FULL_SEARCH_FLAG = "f";

    /**
     * Holds part search key
     */
    private static final String PART_SEARCH_FLAG = "p";

    /**
     * Holds sort key
     */
    private static final String SORT_FLAG = "s";

    /**
     * Holds divider
     */
    private static final String DIVIDER = ":";

    /**
     * Default constructor
     */
    private WebRequestHandler() {}

    /**
     * Converts page data request to search param request for provided class with default loader
     *
     * @param pdr provided page data request
     * @param cls provided class for filtering
     * @param dl provided default loader
     * @return {@code SearchParamRequest} generated search param request
     */
    public static SearchParamRequest convertToParamRequest(PageDataRequest pdr, Class<?> cls, DefaultLoader dl) {
        return initRequest(pdr, dl, new FilterAnnotationHandler(cls));
    }

    /**
     * Translates page data request params to search param request
     *
     * @param pdr provided page data request
     * @param dl provided default loader
     * @param fah provided filter annotation handler
     * @return {@code SearchParamRequest} generated search param request
     */
    private static SearchParamRequest initRequest(PageDataRequest pdr, DefaultLoader dl, FilterAnnotationHandler fah) {
        SearchParamRequest spReq = new SearchParamRequest();
        spReq.setFullParams(parseFullParams(pdr.getFullParams(), fah));
        spReq.setPartParams(parsePartParams(pdr.getFullParams(), fah));
        loadSortState(pdr, dl, spReq, fah);
        loadSizeState(pdr, dl, spReq);
        loadPageState(pdr, dl, spReq);
        loadFold(pdr, dl, spReq);
        return spReq;
    }

    /**
     * Loads sort state to search param request, if sort state is not present in page data request, will load default
     * values from default loader
     *
     * @param pdr provided page data request
     * @param dl provided default loader
     * @param spReq provided search param request
     * @param fah provided filter annotation handler
     */
    private static void loadSortState(PageDataRequest pdr, DefaultLoader dl, SearchParamRequest spReq,
                                      FilterAnnotationHandler fah) {
        if (parseSortParams(pdr.getFullParams(), fah).isEmpty()) {
            spReq.setSortParams(new LinkedHashMap<String, List<String>>(){
                   {put(dl.getSort(), Collections.singletonList(dl.getOrder()));}
            });
        } else {
            spReq.setSortParams(parseSortParams(pdr.getFullParams(), fah));
        }
    }

    /**
     * Loads page state to search param request, if page state is not present in page data request, will load default
     * values from default loader
     *
     * @param pdr provided page data request
     * @param dl provided default loader
     * @param spReq provided search param request
     */
    private static void loadPageState(PageDataRequest pdr, DefaultLoader dl, SearchParamRequest spReq) {
        if (!pdr.getFullParams().containsKey("page")) {
            spReq.setPage((dl.getPage() - 1) * dl.getSize());
        } else {
            if (isInteger(pdr.getFullParams().get("page")[0])) {
                if (parsePage(pdr.getFullParams()) > 0) {
                    spReq.setPage(parsePage(pdr.getFullParams()) - 1);
                } else {
                    throw new IncorrectUrlParameterException(
                            "This page is incorrect. Check if its greater than 0 (page = " +
                            parsePage(pdr.getFullParams()) + ")."
                    );
                }
            } else {
                throw new IncorrectUrlParameterException(
                        "This page has incorrect format. Parameter should be integer number, got (page = " +
                        pdr.getFullParams().get("page")[0] + ") instead."
                );
            }
        }
    }

    /**
     * Loads size state to search param request, if size state is not present in page data request, will load default
     * values from default loader
     *
     * @param pdr provided page data request
     * @param dl provided default loader
     * @param spReq provided search param request
     */
    private static void loadSizeState(PageDataRequest pdr, DefaultLoader dl, SearchParamRequest spReq) {
        if (!pdr.getFullParams().containsKey("size")) {
            spReq.setSize(dl.getSize());
        } else {
            if (isInteger(pdr.getFullParams().get("size")[0])) {
                if (parseSize(pdr.getFullParams()) > 0) {
                    spReq.setSize(parseSize(pdr.getFullParams()));
                } else {
                    throw new IncorrectUrlParameterException(
                            "This size is incorrect. Check if its greater than 0 (size = " +
                            parsePage(pdr.getFullParams()) + ")."
                    );
                }
            } else {
                throw new IncorrectUrlParameterException(
                        "This size has incorrect format. Parameter should be integer number, got (size = " +
                        pdr.getFullParams().get("size")[0] + ") instead."
                );
            }
        }
    }

    /**
     * Loads fold state to search param request, if fold state is not present in page data request, will load default
     * values from default loader
     *
     * @param pdr provided page data request
     * @param dl provided default loader
     * @param spReq provided search param request
     */
    private static void loadFold(PageDataRequest pdr, DefaultLoader dl, SearchParamRequest spReq) {
        if (!pdr.getFullParams().containsKey("fold")) {
            spReq.setFold(dl.isFold());
        } else {
            if (pdr.getFullParams().get("fold")[0].equalsIgnoreCase("on")) {
                spReq.setFold(true);
            }

            if (pdr.getFullParams().get("fold")[0].equalsIgnoreCase("off")) {
                spReq.setFold(false);
            }
        }
    }

    /**
     * Checks if string is integer
     *
     * @param s provided string
     * @return {@code true} if string is an integer
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses full params from map
     *
     * @param toFilter request params map
     * @param fah provided filter annotation handler
     * @return {@code Map<String, List<String>>} parsed full params map
     */
    private static Map<String, List<String>> parseFullParams(Map<String, String[]> toFilter,
                                                             FilterAnnotationHandler fah) {
        Map<String, List<String>> fullParams = new LinkedHashMap<>();
        toFilter.entrySet()
                .stream()
                .filter(x -> fullParamCondition(x, fah))
                .forEach(addToSearchMap(fullParams, fah));
        return fullParams;
    }

    /**
     * Parses part params from map
     *
     * @param toFilter request params map
     * @param fah provided filter annotation handler
     * @return {@code Map<String, List<String>>} parsed part params map
     */
    private static Map<String, List<String>> parsePartParams(Map<String, String[]> toFilter,
                                                             FilterAnnotationHandler fah) {
        Map<String, List<String>> partParams = new LinkedHashMap<>();
        toFilter.entrySet()
                .stream()
                .filter(x -> partParamCondition(x, fah))
                .forEach(addToSearchMap(partParams, fah));
        return partParams;
    }

    /**
     * Parses sort params from map
     *
     * @param toFilter request params map
     * @param fah provided filter annotation handler
     * @return {@code Map<String, List<String>>} parsed sort params map
     */
    private static Map<String, List<String>> parseSortParams(Map<String, String[]> toFilter,
                                                             FilterAnnotationHandler fah) {
        Map<String, List<String>> sortParams = new LinkedHashMap<>();
        toFilter.entrySet()
                .stream()
                .filter(x -> sortParamCondition(x, fah))
                .forEach(addToSortMap(sortParams, fah));
        return sortParams;
    }

    /**
     * Parses page param from map
     *
     * @param toFilter request params map
     * @return {@code Map<String, List<String>>} parsed page
     */
    private static Integer parsePage(Map<String, String[]> toFilter) {
        return toFilter.entrySet()
                       .stream()
                       .filter(x -> x.getKey().equals("page"))
                       .map(x -> Integer.parseInt(x.getValue()[0])).findFirst().get();
    }

    /**
     * Parses size param from map
     *
     * @param toFilter request params map
     * @return {@code Map<String, List<String>>} parsed size
     */
    private static Integer parseSize(Map<String, String[]> toFilter) {
        return toFilter.entrySet()
                       .stream()
                       .filter(x -> x.getKey().equals("size"))
                       .map(x -> Integer.parseInt(x.getValue()[0])).findFirst().get();
    }

    /**
     * Checks if param compliant with full param search
     *
     * @param f full param candidate
     * @param fah provided filter annotation handler
     * @return {@code true} if param compliant
     */
    private static boolean fullParamCondition(Map.Entry<String, String[]> f, FilterAnnotationHandler fah) {
        return f.getKey().contains(DIVIDER)
               && f.getKey().split(DIVIDER).length == 2
               && f.getKey().split(DIVIDER)[0].equals(FULL_SEARCH_FLAG)
               && containsValueInAliases(fah.getDictionary(), f.getKey().split(DIVIDER)[1]);
    }

    /**
     * Checks if param compliant with part param search
     *
     * @param p part param candidate
     * @param fah provided filter annotation handler
     * @return {@code true} if param compliant
     */
    private static boolean partParamCondition(Map.Entry<String, String[]> p, FilterAnnotationHandler fah) {
        return p.getKey().contains(DIVIDER)
               && p.getKey().split(DIVIDER).length == 2
               && p.getKey().split(DIVIDER)[0].equals(PART_SEARCH_FLAG)
               && containsValueInAliases(fah.getDictionary(), p.getKey().split(DIVIDER)[1]);
    }

    /**
     * Checks if param compliant with sort param search
     *
     * @param s sort param candidate
     * @param fah provided filter annotation handler
     * @return {@code true} if param compliant
     */
    private static boolean sortParamCondition(Map.Entry<String, String[]> s, FilterAnnotationHandler fah) {
        return s.getKey().contains(DIVIDER)
               && s.getKey().split(DIVIDER).length == 2
               && s.getKey().split(DIVIDER)[0].equals(SORT_FLAG)
               && containsValueInAliases(fah.getDictionary(), s.getKey().split(DIVIDER)[1]);
    }

    /**
     * Checks if value contained in filtered search dictionary
     *
     * @param dictionary provided dictionary
     * @param val provided value
     * @return {@code true} if dictionary contains value
     */
    private static boolean containsValueInAliases(Map<String, String> dictionary, String val) {
        return dictionary.containsKey(val);
    }

    /**
     * Changes alias name to filtered search dictionary key
     *
     * @param dictionary provided filtered search dictionary
     * @param val provided value
     * @return {@code String} dictionary key value of alias value
     */
    private static String fixKey(Map<String, String> dictionary, String val) {
        return dictionary.get(val);
    }

    /**
     * Function to put correct values into search param map
     *
     * @param paramMap provided param map
     * @param fah provided filter annotation handler
     * @return {@code Consumer<Map.Entry<String, String[]>} function for adding to search param map
     */
    private static Consumer<Map.Entry<String, String[]>> addToSearchMap(Map<String, List<String>> paramMap,
                                                                        FilterAnnotationHandler fah) {
        return x -> {
            if (paramMap.containsKey(fixKey(fah.getDictionary(), x.getKey().split(DIVIDER)[1]))) {
                paramMap.get(
                        fixKey(fah.getDictionary(), x.getKey().split(DIVIDER)[1])).addAll(Arrays.asList(x.getValue())
                );
            } else {
                paramMap.put(fixKey(
                        fah.getDictionary(), x.getKey().split(DIVIDER)[1]),
                        new ArrayList<>(Arrays.asList(x.getValue()))
                );
            }
        };
    }

    /**
     * Function to put correct values into sort param map
     *
     * @param paramMap provided param map
     * @param fah provided filter annotation handler
     * @return {@code Consumer<Map.Entry<String, String[]>} function for adding to sort param map
     */
    private static Consumer<Map.Entry<String, String[]>> addToSortMap(Map<String, List<String>> paramMap,
                                                                      FilterAnnotationHandler fah) {
        return x -> {
            if (paramMap.containsKey(fixKey(fah.getDictionary(), x.getKey().split(DIVIDER)[1]))) {
                paramMap.get(
                        fixKey(fah.getDictionary(), x.getKey().split(DIVIDER)[1])).addAll(fixSortList(x.getValue())
                );
            } else {
                paramMap.put(
                        fixKey(fah.getDictionary(), x.getKey().split(DIVIDER)[1]),
                        new ArrayList<>(fixSortList(x.getValue()))
                );
            }
        };
    }

    /**
     * Translates sort string array to sort list
     *
     * @param toFix provided array
     * @return {@code List<String>} fixed sort list
     */
    private static List<String> fixSortList(String[] toFix) {
        List<String> fixed = new ArrayList<>();

        for (String s: toFix) {
            if (s.equalsIgnoreCase("") || s.equalsIgnoreCase("asc")) {
                fixed.add("ASC");
            } else if (s.equalsIgnoreCase("desc")) {
                fixed.add("DESC");
            }
        }

        return fixed;
    }
}