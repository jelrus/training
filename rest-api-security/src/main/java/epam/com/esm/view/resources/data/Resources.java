package epam.com.esm.view.resources.data;

import epam.com.esm.utils.hateoas.builder.components.ParamString;

/**
 * Resources is the utility class, holds constants for parts of links' templates and relations
 */
public final class Resources {

    /**
     * Holds parts of link and relation for create resource
     */
    public static final ParamString CREATE =
            new ParamString("/create", "Create ", "");

    /**
     * Holds parts of link and relation for read resource
     */
    public static final ParamString READ =
            new ParamString("/{id}", "", " Page");

    /**
     * Holds parts of link and relation for update resource
     */
    public static final ParamString UPDATE =
            new ParamString("/{id}/update", "Update ", "");

    /**
     * Holds parts of link and relation for delete resource
     */
    public static final ParamString DELETE =
            new ParamString("/{id}/delete", "Delete ", "");

    /**
     * Holds parts of link and relation for find all resource
     */
    public static final ParamString FIND_ALL =
            new ParamString("/all", "All ", "s");

    /**
     * Holds parts of link and relation for find all tagged resource
     */
    public static final ParamString FIND_ALL_TAGGED =
            new ParamString("/all/tagged", "All Tagged ", "s");

    /**
     * Holds parts of link and relation for find all certificated resource
     */
    public static final ParamString FIND_ALL_CERTIFICATED =
            new ParamString("/all/certificated", "All Certificated ", "s");

    /**
     * Holds parts of link and relation for find all not tagged resource
     */
    public static final ParamString FIND_ALL_NOT_TAGGED =
            new ParamString("/all/not_tagged", "All Not Tagged ", "s");

    /**
     * Holds parts of link and relation for find all not certificated resource
     */
    public static final ParamString FIND_ALL_NOT_CERTIFICATED =
            new ParamString("/all/not_certificated", "All Not Certificated ", "s");

    /**
     * Holds parts of link and relation for tags resource
     */
    public static final ParamString TAGS =
            new ParamString("/{id}/tags", "", "'s Tags");

    /**
     * Holds parts of link and relation for gift certificates resource
     */
    public static final ParamString GIFT_CERTIFICATES =
            new ParamString("/{id}/gift/certificates", "", "'s Gift Certificates");

    /**
     * Holds parts of link and relation for purchases resource
     */
    public static final ParamString PURCHASES =
            new ParamString("/{id}/purchases", "", "'s Purchases");

    /**
     * Holds parts of link and relation for orders resource
     */
    public static final ParamString ORDERS =
            new ParamString("/{id}/orders", "", "'s Orders");

    /**
     * Holds parts of link and relation for create order resource
     */
    public static final ParamString CREATE_ORDER =
            new ParamString("/{id}/orders/create", "Create Order for ", "");

    /**
     * Holds parts of link and relation for add tags resource
     */
    public static final ParamString ADD_TAGS =
            new ParamString("/{id}/tags/add", "Add Tags for ", "");

    /**
     * Holds parts of link and relation for add gift certificates resource
     */
    public static final ParamString ADD_GIFT_CERTIFICATES =
            new ParamString("/{id}/gift/certificates/add", "Add Gift Certificates for ", "");

    /**
     * Holds parts of link and relation for tags delete resource
     */
    public static final ParamString DELETE_TAGS =
            new ParamString("/{id}/tags/delete", "Delete Tags for ", "");

    /**
     * Holds parts of link and relation for gift certificates delete resource
     */
    public static final ParamString DELETE_GIFT_CERTIFICATES =
            new ParamString("/{id}/gift/certificates/delete", "Delete Gift Certificates for ", "");

    /**
     * Holds parts of link and relation for popular tags resource
     */
    public static final ParamString POPULAR_TAGS =
            new ParamString("/{id}/tags/popular", "", "'s Tags By Popularity");

    /**
     * Holds parts of link and relation for max popular tags resource
     */
    public static final ParamString POPULAR_MAX_TAGS =
            new ParamString("/{id}/tags/popular/max", "", "'s Max Popular Tags");

    /**
     * Holds parts of link and relation for find all users resource
     */
    public static final ParamString USERS_ALL =
            new ParamString("users/all", "All Users", "");

    /**
     * Holds parts of link and relation for find all orders resource
     */
    public static final ParamString ORDERS_ALL =
            new ParamString("orders/all", "All Orders", "");

    /**
     * Holds parts of link and relation for find all gift certificates resource
     */
    public static final ParamString GIFT_CERTIFICATES_ALL =
            new ParamString("gift/certificates/all", "All Gift Certificates", "");

    /**
     * Holds parts of link and relation for find all tags resource
     */
    public static final ParamString TAGS_ALL =
            new ParamString("tags/all", "All Tags", "");

    /**
     * Holds parts of link and relation for main page resource
     */
    public static final ParamString MAIN =
            new ParamString("", "Main", " Page");

    /**
     * Holds parts of link and relation for signup page resource
     */
    public static final ParamString SIGNUP =
            new ParamString("signup", "Signup", " Page");

    /**
     * Holds parts of link and relation for login page resource
     */
    public static final ParamString LOGIN =
            new ParamString("login", "Login", " Page");

    /**
     * Default constructor
     */
    private Resources() {}
}