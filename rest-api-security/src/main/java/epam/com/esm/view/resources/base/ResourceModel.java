package epam.com.esm.view.resources.base;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;

/**
 * ResourceModel is the interface that delegates contracts for link building
 *
 * @param <E> describes dto response
 */
public interface ResourceModel<E extends DtoResponse> {

    /**
     * Contract for building links for dto response
     *
     * @param cls provided controller
     * @param response provided dto response
     * @param paramStrings provided params
     */
    void buildLinks(Class<? extends AbstractController> cls, E response, ParamString ... paramStrings);

    /**
     * Contract for building links for items of page data response
     *
     * @param cls provided controller
     * @param pageDataResponse provided page data response
     * @param paramStrings provided params
     */
    void buildLinksRecords(Class<? extends AbstractController> cls, PageDataResponse<E> pageDataResponse,
                           ParamString ... paramStrings);

    /**
     * Contract for building links for page data response menu
     *
     * @param cls provided controller
     * @param pageDataResponse provided page data response
     * @param paramStrings provided params
     */
    void buildLinksMenu(Class<? extends AbstractController> cls, PageDataResponse<E> pageDataResponse,
                        ParamString ... paramStrings);

    /**
     * Contract for building links for menu of page data response with id
     *
     * @param cls provided controller
     * @param pageDataResponse provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    void buildLinksMenu(Class<? extends AbstractController> cls,
                        PageDataResponse<? extends DtoResponse> pageDataResponse,
                        Long id, ParamString ... paramStrings);

    /**
     * Contract for building links for pagination menu of page data response with id
     *
     * @param cls provided controller
     * @param pageDataResponse provided page data response
     * @param paramStrings provided params
     */
    void buildLinksPaginationMenu(Class<? extends AbstractController> cls, PageDataResponse<E> pageDataResponse,
                                  ParamString ... paramStrings);

    /**
     * Contract for building links for pagination menu of page data response with id
     *
     * @param cls provided controller
     * @param pageDataResponse provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    void buildLinksPaginationMenu(Class<? extends AbstractController> cls,
                                  PageDataResponse<? extends DtoResponse> pageDataResponse,
                                  Long id, ParamString ... paramStrings);
}