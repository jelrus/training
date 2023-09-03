package epam.com.esm.view.resources.impl.action;

import epam.com.esm.controller.MainController;
import epam.com.esm.controller.action.OrderController;
import epam.com.esm.controller.products.GiftCertificateController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.action.OrderDtoResponse;
import epam.com.esm.view.resources.base.AbstractResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OrderResourceModel is the service class, serves for building links for order dto response and page data
 * with order dto responses
 */
@Component
public class OrderResourceModel extends AbstractResourceModel<OrderDtoResponse> {

    /**
     * Default constructor
     */
    @Autowired
    public OrderResourceModel() {}

    /**
     * Builds links for order dto response
     *
     * @param r provided entity
     * @param ps provided params
     */
    public void buildLinks(OrderDtoResponse r, ParamString... ps) {
        super.buildLinks(OrderController.class, r, ps);
    }

    /**
     * Builds links for orders dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecords(PageDataResponse<OrderDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksRecords(OrderController.class, pdr, paramStrings);
    }

    /**
     * Builds links for orders dto responses page data response menu
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<OrderDtoResponse> pdr, ParamString... paramStrings) {
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
     * Builds links for pagination menu of orders dto responses page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<OrderDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksPaginationMenu(OrderController.class, pdr, paramStrings);
    }

    /**
     * Builds links for dto responses pagination menu of page data response with id
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
     * Builds links for order dto response
     *
     * @param resp provided order dto response
     * @param paramStrings provided params
     */
    public void buildLinksGiftCertificates(OrderDtoResponse resp, ParamString... paramStrings) {
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
    public void buildLinksOrders(PageDataResponse<OrderDtoResponse> pdr, ParamString... paramStrings) {
        pdr.getItems().stream().filter(o -> o.getGiftCertificates() != null && !o.getGiftCertificates().isEmpty())
                .forEach(gc -> buildLinksGiftCertificates(gc, paramStrings));
    }

    /**
     * Builds links for main page of orders dto responses page data response
     *
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(PageDataResponse<OrderDtoResponse> pdr, ParamString... ps) {
        super.addLinksMainPage(MainController.class, pdr, ps);
    }
}