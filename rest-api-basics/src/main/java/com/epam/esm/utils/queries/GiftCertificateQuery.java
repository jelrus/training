package com.epam.esm.utils.queries;

/**
 * GiftCertificateQuery is the utility class which holds constants for prepared statements queries
 */
public final class GiftCertificateQuery {

    /**
     * Constant field which holds create query
     */
    public static final String CREATE = "INSERT INTO gift_certificate " +
                                        "VALUES(default, ?, ?, ?, ?, ?, ?);";

    /**
     * Constant field which holds find by id query
     */
    public static final String FIND_BY_ID = "SELECT id, name, description, price, duration, create_date, " +
                                            "last_update_date " +
                                            "FROM gift_certificate " +
                                            "WHERE id = ?;";

    /**
     * Constant field which holds update query
     */
    public static final String UPDATE = "UPDATE gift_certificate " +
                                        "SET name = ?, description = ?, price = ?, duration = ?, create_date = ?, " +
                                        "last_update_date = ? " +
                                        "WHERE id = ?;";

    /**
     * Constant field which holds delete query
     */
    public static final String DELETE = "DELETE FROM gift_certificate " +
                                        "WHERE id = ?;";

    /**
     * Constant field which holds find all query
     */
    public static final String FIND_ALL = "SELECT id, name, description, price, duration, create_date, " +
                                          "last_update_date " +
                                          "FROM gift_certificate;";

    /**
     * Constant field which holds exist by id query
     */
    public static final String EXIST_BY_ID = "SELECT count(*) AS count " +
                                             "FROM gift_certificate " +
                                             "WHERE id = ?;";

    /**
     * Constant field which holds exist by name query
     */
    public static final String EXIST_BY_NAME = "SELECT count(*) AS count " +
                                               "FROM gift_certificate " +
                                               "WHERE name = ?";

    /**
     * Constant field which holds find by name query
     */
    public static final String FIND_BY_NAME = "SELECT id, name, description, price, duration, create_date, " +
                                              "last_update_date " +
                                              "FROM gift_certificate " +
                                              "WHERE name = ?";

    /**
     * Constant field which holds find tagged certificates query
     */
    public static final String FIND_TAGGED = "SELECT DISTINCT gc.id, gc.name, gc.description, gc.price, gc.duration, " +
                                             "gc.create_date, gc.last_update_date " +
                                             "FROM gift_certificate_tag " +
                                             "INNER JOIN gift_certificate gc " +
                                             "ON gc.id = gift_certificate_tag.gift_certificate_id;";

    /**
     * Constant field which holds initial query for param search
     */
    public static final String FIND_PARAM_INIT = "SELECT DISTINCT gc.id, gc.name, gc.description, gc.price, " +
                                                 "gc.duration, gc.create_date, gc.last_update_date " +
                                                 "FROM gift_certificate_tag " +
                                                 "INNER JOIN gift_certificate gc " +
                                                 "ON gc.id = gift_certificate_tag.gift_certificate_id " +
                                                 "INNER JOIN tag t on gift_certificate_tag.tag_id = t.id ";

    /**
     * Constant field which holds find tags by gift certificate query
     */
    public static final String FIND_TAGS = "SELECT t.id, t.name " +
                                           "FROM gift_certificate_tag " +
                                           "INNER JOIN tag t " +
                                           "ON t.id = gift_certificate_tag.tag_id " +
                                           "WHERE gift_certificate_id = ?;";

    /**
     * Constant field which holds add tag to gift certificate query
     */
    public static final String ADD_TAG = "INSERT INTO gift_certificate_tag " +
                                         "VALUES(default, ?, ?);";

    /**
     * Constant field which holds delete tag from gift certificate query
     */
    public static final String DELETE_TAG = "DELETE FROM gift_certificate_tag " +
                                            "WHERE gift_certificate_id = ? AND tag_id = ?;";

    /**
     * Default constructor
     */
    private GiftCertificateQuery() {}
}