package epam.com.esm.view.resources.impl.products;

import epam.com.esm.controller.MainController;
import epam.com.esm.controller.products.GiftCertificateController;
import epam.com.esm.controller.products.TagController;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.search.transport.response.PageDataResponse;
import epam.com.esm.view.dto.response.DtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import epam.com.esm.view.resources.base.AbstractResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TagResourceModel is the service class, serves for building links for tag dto response and page data with tag
 * dto responses
 */
@Component
public class TagResourceModel extends AbstractResourceModel<TagDtoResponse> {

    /**
     * Default constructor
     */
    @Autowired
    public TagResourceModel() {}

    /**
     * Builds links for tag dto response
     *
     * @param r provided entity
     * @param ps provided params
     */
    public void buildLinks(TagDtoResponse r, ParamString... ps) {
        super.buildLinks(TagController.class, r, ps);
    }

    /**
     * Builds links for tags dto responses of page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksRecords(PageDataResponse<TagDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksRecords(TagController.class, pdr, paramStrings);
    }

    /**
     * Builds links for tags dto responses page data response menu
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<TagDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksMenu(TagController.class, pdr, paramStrings);
    }

    /**
     * Builds links for dto responses of page data response with id
     *
     * @param pdr provided page data response
     * @param id provided id
     * @param paramStrings provided params
     */
    public void buildLinksMenu(PageDataResponse<? extends DtoResponse> pdr, Long id, ParamString... paramStrings) {
        super.buildLinksMenu(TagController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for pagination menu of tags dto responses page data response
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksPaginationMenu(PageDataResponse<TagDtoResponse> pdr, ParamString... paramStrings) {
        super.buildLinksPaginationMenu(TagController.class, pdr, paramStrings);
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
        super.buildLinksPaginationMenu(TagController.class, pdr, id, paramStrings);
    }

    /**
     * Builds links for gift certificates dto responses
     *
     * @param resp provided dto response
     * @param paramStrings provided params
     */
    public void buildLinksGiftCertificates(TagDtoResponse resp, ParamString... paramStrings) {
        LinkBuilder lb = new LinkBuilder();
        if (resp.getGiftCertificates() != null && !resp.getGiftCertificates().isEmpty()) {
            resp.getGiftCertificates().forEach(
                    gc -> lb.loadClass(GiftCertificateController.class)
                            .addModel(gc)
                            .addParams(new PlaceholderValue("{id}", gc.getId().toString()))
                            .addLinks(paramStrings)
                            .build()
            );
        }
    }

    /**
     * Builds links for gift certificates dto responses
     *
     * @param pdr provided page data response
     * @param paramStrings provided params
     */
    public void buildLinksGiftCertificates(PageDataResponse<TagDtoResponse> pdr, ParamString... paramStrings) {
        pdr.getItems().stream().filter(t -> t.getGiftCertificates() != null && !t.getGiftCertificates().isEmpty())
                .forEach(gc -> buildLinksGiftCertificates(gc, paramStrings));
    }

    /**
     * Builds links for main page of tag dto response
     *
     * @param r provided dto response
     * @param ps provided params
     */
    public void addLinksMainPage(TagDtoResponse r, ParamString... ps) {
        super.addLinksMainPage(MainController.class, r, ps);
    }

    /**
     * Builds links for main page of tags dto responses page data response
     *
     * @param pdr provided page data response
     * @param ps provided params
     */
    public void addLinksMainPage(PageDataResponse<TagDtoResponse> pdr, ParamString... ps) {
        super.addLinksMainPage(MainController.class, pdr, ps);
    }
}