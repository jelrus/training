package epam.com.esm.view.resources.base;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;

/**
 * AbstractResourceModel is the abstract class, serves as parent class to all resource model children
 *
 * @param <E> describes child of dto response
 */
public abstract class AbstractResourceModel<E extends DtoResponse> implements ResourceModel<E> {

    /**
     * Builds links for entity
     *
     * @param c provided controller
     * @param r provided entity
     * @param ps provided params
     */
    @Override
    public void buildLinks(Class<? extends AbstractController> c, E r, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c)
          .addModel(r)
          .addParams(new PlaceholderValue("{id}", r.getId().toString()))
          .addLinks(ps)
          .build();
    }

    /**
     * Builds links for items of page data response
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param ps provided params
     */
    @Override
    public void buildLinksRecords(Class<? extends AbstractController> c, PageDataResponse<E> pdr, ParamString ... ps) {
        pdr.getItems().forEach(i -> buildLinks(c, i, ps));
    }

    /**
     * Builds links for page data response menu
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param ps provided params
     */
    @Override
    public void buildLinksMenu(Class<? extends AbstractController> c, PageDataResponse<E> pdr, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c).addModel(pdr.getMainMenu()).addLinks(ps).build();
    }

    /**
     * Builds links for menu of page data response with id
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param id provided id
     * @param ps provided params
     */
    @Override
    public void buildLinksMenu(Class<? extends AbstractController> c, PageDataResponse<? extends DtoResponse> pdr,
                               Long id, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c)
          .addModel(pdr.getMainMenu())
          .addParams(new PlaceholderValue("{id}", id.toString()))
          .addLinks(ps)
          .build();
    }

    /**
     * Builds links for pagination menu of page data response with id
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param ps provided params
     */
    @Override
    public void buildLinksPaginationMenu(Class<? extends AbstractController> c, PageDataResponse<E> pdr,
                                         ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c).addModel(pdr).addLinks(ps).addPageTypes(pdr.getPages()).buildPages();
    }

    /**
     * Builds links for pagination menu of page data response with id
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param id provided id
     * @param ps provided params
     */
    @Override
    public void buildLinksPaginationMenu(Class<? extends AbstractController> c,
                                         PageDataResponse<? extends DtoResponse> pdr,
                                         Long id, ParamString... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c)
          .addModel(pdr)
          .addLinks(ps)
          .addParams(new PlaceholderValue("{id}", id.toString()))
          .addPageTypes(pdr.getPages())
          .buildPages();
    }

    /**
     * Builds links for dto response
     *
     * @param c provided controller
     * @param r provided dto response
     * @param ps provided params
     */
    public void addLinksMainPage(Class<? extends AbstractController> c, DtoResponse r, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c).addModel(r).addLinks(ps).build();
    }

    /**
     * Builds links for main page of page data response
     *
     * @param c provided controller
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(Class<? extends AbstractController> c,
                                 PageDataResponse<? extends DtoResponse> pdr, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(c).addModel(pdr.getMainMenu()).addLinks(ps).build();
    }
}