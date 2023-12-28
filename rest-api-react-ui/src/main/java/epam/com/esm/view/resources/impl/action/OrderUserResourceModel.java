package epam.com.esm.view.resources.impl.action;

import epam.com.esm.controller.MainController;
import epam.com.esm.controller.action.OrderController;
import epam.com.esm.controller.products.GiftCertificateController;
import epam.com.esm.controller.user.UserController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.action.OrderUserDtoResponse;
import epam.com.esm.view.resources.base.AbstractResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OrderUserResourceModel is the service class, serves for building links for order user dto response and page data
 * with order user dto responses
 */
@Component
public class OrderUserResourceModel extends AbstractResourceModel<OrderUserDtoResponse> {

    /**
     * Default constructor
     */
    @Autowired
    public OrderUserResourceModel() {}

    /**
     * Builds links for order user dto response
     *
     * @param r provided entity
     * @param ps provided params
     */
    public void buildLinks(OrderUserDtoResponse r, ParamString... ps) {
        super.buildLinks(OrderController.class, r, ps);
    }

    /**
     * Builds links for orders user dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecords(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksRecords(OrderController.class, pdr, paramStrings);
    }

    /**
     * Builds links for orders user dto responses page data response menu
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksMenu(OrderController.class, pdr, paramStrings);
    }

    /**
     * Builds links for dto responses of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<? extends DtoResponse> pdr, Long id, ParamString... paramStrings) {
        super.buildLinksMenu(OrderController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for pagination menu of orders user dto responses page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksPaginationMenu(OrderController.class, pdr, paramStrings);
    }

    /**
     * Builds links for orders user dto responses pagination menu of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<? extends DtoResponse> pdr, Long id,
                                         ParamString... paramStrings) {
        super.buildLinksPaginationMenu(OrderController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for gift certificates dto response
     *
     * @param resp provided gift certificate dto response
     * @param paramStrings provided params
     */
    public void buildLinksGiftCertificates(OrderUserDtoResponse resp, ParamString... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        resp.getGiftCertificates().forEach(
                gc -> lb.loadClass(GiftCertificateController.class)
                        .addModel(gc)
                        .addParams(new PlaceholderValue("{id}", gc.getId().toString()))
                        .addLinks(paramStrings)
                        .build()
        );
    }

    /**
     * Builds links for gift certificates dto response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksGiftCertificates(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... paramStrings) {
        pdr.getItems().stream().filter(o -> o.getGiftCertificates() != null && !o.getGiftCertificates().isEmpty())
                .forEach(gc -> buildLinksGiftCertificates(gc, paramStrings));
    }

    /**
     * Builds links for order user dto response
     *
     * @param resp provided dto response
     * @param paramStrings provided params
     */
    public void buildLinksUser(OrderUserDtoResponse resp, ParamString... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(UserController.class)
                .addModel(resp.getUser())
                .addParams(new PlaceholderValue("{id}", resp.getUser().getId().toString()))
                .addLinks(paramStrings)
                .build();
    }

    /**
     * Builds links for user dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecordUser(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... paramStrings) {
        pdr.getItems().stream().filter(o -> o.getUser() != null).forEach(o -> buildLinksUser(o, paramStrings));
    }

    /**
     * Builds links for main page of orders user dto response
     *
     * @param r provided dto response
     * @param ps provided params
     */
    public void addLinksMainPage(OrderUserDtoResponse r, ParamString... ps) {
        super.addLinksMainPage(MainController.class, r, ps);
    }

    /**
     * Builds links for main page of orders user dto responses page data response
     *
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(PageDataResponse<OrderUserDtoResponse> pdr, ParamString... ps) {
        super.addLinksMainPage(MainController.class, pdr, ps);
    }
}