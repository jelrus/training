package com.epam.esm.utils.search;

import java.util.HashMap;
import java.util.Map;

/**
 * RequestDictionary is the utility class, which holds map for translating request parameters
 */
public final class RequestDictionary {

    /**
     * Object, which holds dictionary for translating parameter input name to db name
     */
    public static final Map<String, String> DICT = new HashMap<String, String>() {{
        put("gc.name", "gc.name");
        put("giftCertificateName", "gc.name");

        put("gc.description", "gc.description");
        put("giftCertificateDescription", "gc.description");
        put("description", "gc.description");

        put("gc.price", "gc.price");
        put("giftCertificatePrice", "gc.price");
        put("price", "gc.price");

        put("gc.duration", "gc.duration");
        put("giftCertificateDuration", "gc.duration");
        put("duration", "gc.duration");

        put("gc.create_date", "gc.create_date");
        put("gc.create", "gc.create_date");
        put("giftCertificateCreate", "gc.create_date");
        put("create", "gc.create_date");

        put("gc.last_update_date", "gc.last_update_date");
        put("gc.update", "gc.last_update_date");
        put("giftCertificateUpdate", "gc.last_update_date");
        put("update", "gc.last_update_date");

        put("t.name", "t.name");
        put("tagName", "t.name");
    }};

    /**
     * Default constructor
     */
    private RequestDictionary() {}
}