package epam.com.esm.view.resources.impl.products;

import epam.com.esm.controller.MainController;
import epam.com.esm.controller.products.GiftCertificateController;
import epam.com.esm.controller.products.TagController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.resources.base.AbstractResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GiftCertificateResourceModel is the service class, serves for building links for gift certificate dto response and
 * page data with gift certificate dto responses
 */
@Component
public class GiftCertificateResourceModel extends AbstractResourceModel<GiftCertificateDtoResponse> {

    /**
     * Default constructor
     */
    @Autowired
    public GiftCertificateResourceModel() {}

    /**
     * Builds links for gift certificate dto response
     *
     * @param r provided entity
     * @param ps provided params
     */
    public void buildLinks(GiftCertificateDtoResponse r, ParamString ... ps) {
        super.buildLinks(GiftCertificateController.class, r, ps);
    }

    /**
     * Builds links for gift certificates dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecords(PageDataResponse<GiftCertificateDtoResponse> pdr, ParamString ... paramStrings) {
        super.buildLinksRecords(GiftCertificateController.class, pdr, paramStrings);
    }

    /**
     * Builds links for gift certificates dto responses page data response menu
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<GiftCertificateDtoResponse> pdr, ParamString ... paramStrings) {
        super.buildLinksMenu(GiftCertificateController.class, pdr, paramStrings);
    }

    /**
     * Builds links for dto responses of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<? extends DtoResponse> pdr, Long id, ParamString ... paramStrings) {
        super.buildLinksMenu(GiftCertificateController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for pagination menu of gift certificates dto responses page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<GiftCertificateDtoResponse> pdr,
                                         ParamString ... paramStrings) {
        super.buildLinksPaginationMenu(GiftCertificateController.class, pdr, paramStrings);
    }

    /**
     * Builds links for gift certificates dto responses pagination menu of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<? extends DtoResponse> pdr, Long id,
                                         ParamString ... paramStrings) {
        super.buildLinksPaginationMenu(GiftCertificateController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for tags dto responses
     *
     * @param resp provided dto response
     * @param paramStrings provided params
     */
    public void buildLinksTags(GiftCertificateDtoResponse resp, ParamString ... paramStrings) {
        LinkBuilder lb = new LinkBuilder();

        if (resp.getTags() != null && !resp.getTags().isEmpty()) {
            resp.getTags().forEach(
                    t -> lb.loadClass(TagController.class)
                            .addModel(t)
                            .addParams(new PlaceholderValue("{id}", t.getId().toString()))
                            .addLinks(paramStrings)
                            .build()
            );
        }
    }

    /**
     * Builds links for tags dto responses
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksTags(PageDataResponse<GiftCertificateDtoResponse> pdr,
                               ParamString ... paramStrings) {
        pdr.getItems().stream().filter(gc -> gc.getTags() != null && !gc.getTags().isEmpty())
                .forEach(gc -> buildLinksTags(gc, paramStrings));
    }

    /**
     * Builds links for main page of gift certificate dto response
     *
     * @param r provided dto response
     * @param ps provided params
     */
    public void addLinksMainPage(GiftCertificateDtoResponse r, ParamString... ps) {
        super.addLinksMainPage(MainController.class, r, ps);
    }

    /**
     * Builds links for main page of gift certificates dto responses page data response
     *
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(PageDataResponse<GiftCertificateDtoResponse> pdr, ParamString... ps) {
        super.addLinksMainPage(MainController.class, pdr, ps);
    }
}