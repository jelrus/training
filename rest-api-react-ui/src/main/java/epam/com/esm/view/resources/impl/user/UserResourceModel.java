package epam.com.esm.view.resources.impl.user;

import epam.com.esm.controller.MainController;
import epam.com.esm.controller.action.OrderController;
import epam.com.esm.controller.products.GiftCertificateController;
import epam.com.esm.controller.products.TagController;
import epam.com.esm.controller.user.UserController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.purchase.PurchaseDataDtoResponse;
import epam.com.esm.view.dto.response.impl.user.UserDtoResponse;
import epam.com.esm.view.resources.base.AbstractResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

/**
 * UserResourceModel is the service class, serves for building links for user dto response and page data with user
 * dto responses
 */
@Component
public class UserResourceModel extends AbstractResourceModel<UserDtoResponse> {

    /**
     * Default constructor
     */
    @Autowired
    public UserResourceModel() {}

    /**
     * Builds links for user dto response
     *
     * @param r provided entity
     * @param ps provided params
     */
    public void buildLinks(UserDtoResponse r, ParamString... ps) {
        super.buildLinks(UserController.class, r, ps);
    }

    /**
     * Builds links for users dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecords(PageDataResponse<UserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksRecords(UserController.class, pdr, paramStrings);
    }

    /**
     * Builds links for user dto responses page data response menu
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<UserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksMenu(UserController.class, pdr, paramStrings);
    }

    /**
     * Builds links for user dto responses of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<? extends DtoResponse> pdr, Long id, ParamString... paramStrings) {
        super.buildLinksMenu(UserController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for pagination menu of user dto responses page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<UserDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksPaginationMenu(UserController.class, pdr, paramStrings);
    }

    /**
     * Builds links for dto responses pagination menu of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<? extends DtoResponse> pdr,
                                         Long id, ParamString... paramStrings) {
        super.buildLinksPaginationMenu(UserController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for orders dto responses
     *
     * @param resp provided dto response
     * @param paramStrings provided params
     */
    public void buildLinksOrders(UserDtoResponse resp, ParamString... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        if (resp.getOrders() != null && !resp.getOrders().isEmpty()) {
            resp.getOrders().forEach(
                    o -> lb.loadClass(OrderController.class)
                            .addModel(o)
                            .addParams(new PlaceholderValue("{id}", o.getId().toString()))
                            .addLinks(paramStrings)
                            .build()
            );
        }
    }

    /**
     * Builds links for orders dto responses
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksOrders(PageDataResponse<UserDtoResponse> pdr, ParamString... paramStrings) {
        pdr.getItems().stream().filter(u -> u.getOrders() != null && !u.getOrders().isEmpty())
                .forEach(gc -> buildLinksOrders(gc, paramStrings));
    }

    /**
     * Builds links for purchase data dto responses
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPurchases(PageDataResponse<PurchaseDataDtoResponse> pdr, ParamString ... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        pdr.getItems().forEach(p -> lb.loadClass(GiftCertificateController.class)
                      .addModel(p).addParams(new PlaceholderValue("{id}",
                                              p.getGiftCertificate().getId().toString()))
                      .addLinks(paramStrings).build());
    }

    /**
     * Builds links for dto data tag wrapped collection
     *
     * @param wc provided wrapped collection
     * @param paramStrings provided params
     */
    public void buildLinksDataTags(WrappedCollection<DtoDataTag> wc, ParamString ... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        wc.getItems()
          .forEach(t -> lb.loadClass(TagController.class)
                          .addModel(t)
                          .addParams(new PlaceholderValue("{id}", t.getTag().getId().toString()))
                          .addLinks(paramStrings)
                          .build());
    }

    /**
     * Builds links for wrapped collection menu with id
     *
     * @param wc provided wrapped collection
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksWrappedCollectionMenu(WrappedCollection<? extends RepresentationModel<?>> wc, Long id,
                                                ParamString ... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(UserController.class)
          .addModel(wc.getMainMenu())
          .addParams(new PlaceholderValue("{id}", id.toString()))
          .addLinks(paramStrings)
          .build();
        wc.setPaginationMenu(null);
    }

    /**
     * Builds links for main page of dto response
     *
     * @param r provided dto response
     * @param ps provided params
     */
    public void addLinksMainPage(DtoResponse r, ParamString... ps) {
        super.addLinksMainPage(MainController.class, r, ps);
    }

    /**
     * Builds links for main page of dto responses page data response
     *
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(PageDataResponse<? extends DtoResponse> pdr, ParamString... ps) {
        super.addLinksMainPage(MainController.class, pdr, ps);
    }

    /**
     * Builds links for main page of dto data tags wrapped collection
     *
     * @param wc provided wrapped collection
     * @param ps provided params
     */
    public void addLinksMainPage(WrappedCollection<DtoDataTag> wc, ParamString ... ps) {
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(MainController.class).addModel(wc.getMainMenu()).addLinks(ps).build();
        wc.setPaginationMenu(null);
    }
}