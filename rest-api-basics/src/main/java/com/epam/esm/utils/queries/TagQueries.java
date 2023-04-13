package com.epam.esm.utils.queries;

public final class TagQueries {

    public static final String CREATE = "INSERT INTO tag VALUES(default, ?);";

    public static final String FIND_BY_ID = "SELECT id, name FROM tag WHERE id = ?;";

    public static final String DELETE = "DELETE FROM tag WHERE id = ?;";

    public static final String FIND_ALL = "SELECT t.id, t.name FROM tag;";

    public static final String EXIST = "SELECT count(*) AS count FROM tag WHERE id = ?;";

    public static final String COUNT = "SELECT count(*) AS count FROM tag;";

    public static final String COUNT_CERTIFICATES_BY_TAG = "SELECT count(*) as count " +
                                                              "FROM gift_certificate_tag " +
                                                              "INNER JOIN gift_certificate gc " +
                                                              "ON gc.id = gift_certificate_tag.gift_certificate_id " +
                                                              "WHERE tag_id = ?;";

    public static final String FIND_GIFT_CERTIFICATES_BY_TAG = "SELECT gc.id, " +
                                                                         "gc.name, " +
                                                                         "gc.description " +
                                                                         "gc.price " +
                                                                         "gc.duration " +
                                                                         "gc.create_date " +
                                                                         "gc.last_update_date " +
                                                              "FROM gift_certificate_tag " +
                                                              "INNER JOIN gift_certificates gc " +
                                                              "ON gc.id = gift_certificate_tag.gift_certificate_id " +
                                                              "WHERE tag_id = ?;";

    public static final String ADD_GIFT_CERTIFICATE = "INSERT INTO gift_certificate_tag VALUES (default, ?, ?);";

    public static final String REMOVE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate_tag " +
                                                         "WHERE gift_certificate_id = ?;";

    public static final String FIND_ALL_CERTIFICATED = "SELECT DISTINCT t.id, t.name " +
                                                            "FROM gift_certificate_tag " +
                                                            "INNER JOIN tag t " +
                                                            "ON t.id = gift_certificate_tag.tag_id;";

    private TagQueries() {}
}