package com.epam.esm.utils.queries;

/**
 * TagQuery is the utility class which holds constants for prepared statements queries
 */
public final class TagQuery {

    /**
     * Constant field which holds create query
     */
    public static final String CREATE = "INSERT INTO tag VALUES(default, ?);";

    /**
     * Constant field which holds find by id query
     */
    public static final String FIND_BY_ID = "SELECT id, name FROM tag WHERE id = ?;";

    /**
     * Constant field which holds delete query
     */
    public static final String DELETE = "DELETE FROM tag WHERE id = ?;";

    /**
     * Constant field which holds find all query
     */
    public static final String FIND_ALL = "SELECT id, name FROM tag;";

    /**
     * Constant field which holds exist by id query
     */
    public static final String EXIST_BY_ID = "SELECT count(*) AS count FROM tag WHERE id = ?;";

    /**
     * Constant field which holds exist by name query
     */
    public static final String EXIST_BY_NAME = "SELECT count(*) AS count FROM tag WHERE name = ?;";

    /**
     * Constant field which holds find by name query
     */
    public static final String FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = ?;";

    /**
     * Constant field which holds find certificated tags query
     */
    public static final String FIND_CERTIFICATED = "SELECT DISTINCT t.id, t.name " +
                                                   "FROM gift_certificate_tag " +
                                                   "INNER JOIN tag t " +
                                                   "ON t.id = gift_certificate_tag.tag_id;";

    /**
     * Constant field which holds find gift certificates by tag query
     */
    public static final String FIND_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, " +
                                                   "gc.create_date, gc.last_update_date " +
                                                   "FROM gift_certificate_tag " +
                                                   "INNER JOIN gift_certificate gc " +
                                                   "ON gc.id = gift_certificate_tag.gift_certificate_id " +
                                                   "WHERE tag_id = ?;";

    /**
     * Default constructor
     */
    private TagQuery() {}
}