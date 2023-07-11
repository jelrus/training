package epam.com.esm.utils.hateoas.builder;

import epam.com.esm.controller.AbstractController;
import epam.com.esm.exception.types.BuildException;
import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.hateoas.builder.components.ParamString;
import epam.com.esm.utils.hateoas.templates.ReplaceableTemplate;
import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;
import epam.com.esm.utils.hateoas.wrappers.WrappedCollection;
import epam.com.esm.utils.hateoas.wrappers.type.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;

/**
 * LinkBuilder is the service class which provides link building operations for representation model children
 * Wrapper for Spring HATEOAS
 */
@Component
public class LinkBuilder {

    /**
     * Holds cls value
     */
    private Class<? extends AbstractController> cls;

    /**
     * Holds representationModel value
     */
    private RepresentationModel<?> representationModel;

    /**
     * Holds links values
     */
    private ParamString[] links;

    /**
     * Holds pages values
     */
    private Map<PageType, String> pages;

    /**
     * Holds placeholders values
     */
    private PlaceholderValue[] placeholderValues;

    /**
     * Default constructor
     */
    @Autowired
    public LinkBuilder() {}

    /**
     * Loads class for link building
     *
     * @param cls requested class
     * @return {@code LinkBuilder} current link builder instance
     */
    public LinkBuilder loadClass(Class<? extends AbstractController> cls) {
        if (cls != null) {
            this.cls = cls;
            return this;
        } else {
            throw new BuildException("Loaded Class cannot be null");
        }
    }

    /**
     * Adds representation model object
     *
     * @param rm requested representation model
     * @return {@code LinkBuilder} current link builder instance
     */
    public LinkBuilder addModel(RepresentationModel<?> rm) {
        if (rm != null) {
            this.representationModel = rm;
            return this;
        } else {
            throw new BuildException("Model cannot be null");
        }
    }

    /**
     * Add params to replace in link pattern
     *
     * @param params requested placeholder value params
     * @return {@code LinkBuilder} current link builder instance
     */
    public LinkBuilder addParams(PlaceholderValue ... params) {
        this.placeholderValues = params;
        return this;
    }

    /**
     * Adds links to link builder
     *
     * @param lt requested links
     * @return {@code LinkBuilder} current link builder instance
     */
    public LinkBuilder addLinks(ParamString ... lt) {
        this.links = lt;
        return this;
    }

    /**
     * Adds pages for pagination building
     *
     * @param pages requested page type and page params
     * @return {@code LinkBuilder} current link builder instance
     */
    public LinkBuilder addPageTypes(Map<PageType, String> pages) {
        this.pages = pages;
        return this;
    }

    /**
     * Applies links to representation model
     * Will reset all link builder values upon completion
     */
    public void build() {
        checkModelParams();
        addLinksToModel();
        reset();
    }

    /**
     * Adds pagination links for wrapped collection object
     * Will reset all link builder values upon completion
     */
    public void buildPages() {
        checkPaginationParams();

        if (this.representationModel instanceof WrappedCollection) {
            WrappedCollection<?> wc = (WrappedCollection<?>) representationModel;

            if (pages.containsKey(PageType.PREV)) {
                String prev = getBaseUri(cls) + links[0].getLink() + pages.get(PageType.PREV);
                wc.getPaginationMenu().add(Link.of(buildTemplate(prev)).withRel("Previous Page"));
            }

            if (pages.containsKey(PageType.CURRENT)) {
                String current = getBaseUri(cls) + links[0].getLink() + pages.get(PageType.CURRENT);
                wc.getPaginationMenu().add(Link.of(buildTemplate(current)).withRel("Current Page"));
            }

            if (pages.containsKey(PageType.NEXT)) {
                String next = getBaseUri(cls) + links[0].getLink() + pages.get(PageType.NEXT);
                wc.getPaginationMenu().add(Link.of(buildTemplate(next)).withRel("Next Page"));
            }

        } else {
            throw new BuildException("Representation model invalid or null");
        }

        reset();
    }

    /**
     * Fetches base URI for link from ControllerLink class annotation
     *
     * @param cls requested abstract controller child class
     * @return {@code String} fetched base uri
     */
    private String getBaseUri(Class<? extends AbstractController> cls) {
        if (cls.isAnnotationPresent(ControllerLink.class)) {
            return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/" +
                    cls.getAnnotation(ControllerLink.class).mapping();
        } else {
            throw new BuildException("Controller Link Mapping hasn't been set");
        }
    }

    /**
     * Fetches class name for link's relation from ControllerLink class annotation
     *
     * @return {@code String} fetched class name
     */
    private String getName() {
        String className;

        if (cls.isAnnotationPresent(ControllerLink.class)) {
            className = cls.getAnnotation(ControllerLink.class).name();
        } else {
            throw new BuildException("Controller Link Name hasn't been set");
        }

        return className;
    }

    /**
     * Checks LinkBuilder params before building for object
     * If class, model or links are null with throw BuildException
     */
    private void checkModelParams() {
        if (cls == null || representationModel == null || links == null) {
            throw new BuildException("Build failed, null params found");
        }
    }

    /**
     * Checks LinkBuilder pagination params before building for collection
     * If class, model, links or pages null will throw BuildException
     */
    private void checkPaginationParams() {
        if (cls == null || representationModel == null || links == null || pages == null) {
            throw new BuildException("Build failed, null params found");
        }
    }

    /**
     * Adds links to model
     */
    private void addLinksToModel() {
        if (links != null) {
            for (ParamString link : links) {
                representationModel.add(buildLink(link).withRel(buildRel(link)));
            }
        }
    }

    /**
     * Resets LinkBuilder to initial state
     */
    private void reset() {
        cls = null;
        representationModel = null;
        placeholderValues = null;
        links = null;
        pages = null;
    }

    /**
     * Builds link
     *
     * @param ps requested param string
     * @return {@code Link} built link
     */
    private Link buildLink(ParamString ps) {
        return Link.of(buildTemplate(getBaseUri(cls) + ps.getLink()));
    }

    /**
     * Builds resource from template
     *
     * @param base requested string template
     * @return {@code String} built resource
     */
    private String buildTemplate(String base) {
        if (placeholderValues != null) {
            ReplaceableTemplate rt = new ReplaceableTemplate(base, placeholderValues);
            Arrays.stream(placeholderValues).forEach(rt::addReplacement);
            return rt.build();
        }

        return base;
    }

    /**
     * Builds relation for link
     *
     * @param ps requested param string
     * @return {@code String} built relation for link
     */
    private String buildRel(ParamString ps) {
        return ps.getPrefix() + getName() + ps.getSuffix();
    }
}