package com.epam.esm.utils.queries;

import com.epam.esm.utils.search.dao.SearchParamRequest;

public final class GiftCertificateQueries {
    
    public static final String CREATE = "INSERT INTO gift_certificate VALUES (default, ?, ?, ?, ?, ?, ?);";

    public static final String FIND_BY_ID = "SELECT id, name, description, price, duration, create_date, " +
                                                   "last_update_date " +
                                            "FROM gift_certificate WHERE id = ?;";

    public static final String FIND_BY_NAME = "SELECT id, name, description, price, duration, create_date, " +
                                                     "last_update_date " +
                                              "FROM gift_certificate WHERE name = ?;";

    public static final String UPDATE = "UPDATE gift_certificate " +
                                              "SET name = ?, description = ?, price = ?, duration = ?, " +
                                                  "last_update_date = ? " +
                                              "WHERE id = ?;";

    public static final String DELETE = "DELETE from gift_certificate WHERE id = ?;";

    public static final String FIND_ALL = "SELECT id, name, description, price, duration, create_date, " +
                                                 "last_update_date " +
                                          "FROM gift_certificate;";

    public static final String EXIST = "SELECT count(*) AS count FROM gift_certificate WHERE id = ?;";

    public static final String EXIST_BY_NAME = "SELECT count(*) AS count FROM gift_certificate WHERE name = ?;";

    public static final String COUNT = "SELECT count(*) AS count FROM gift_certificate;";

    public static final String COUNT_TAGS_BY_GIFT_CERTIFICATE = "SELECT count(*) as count " +
                                                                   "FROM gift_certificate_tag " +
                                                                   "INNER JOIN tag t " +
                                                               "ON t.id = gift_certificate_tag.tag_id " +
                                                               "WHERE gift_certificate_id = ?;";

    public static final String FIND_TAGS_BY_GIFT_CERTIFICATE = "SELECT t.id, t.name from gift_certificate_tag " +
                                                                  "JOIN tag t " +
                                                                  "ON t.id = gift_certificate_tag.tag_id " +
                                                                  "WHERE gift_certificate_id = ?;";

    public static final String ADD_TAG = "INSERT INTO gift_certificate_tag values (default, ?, ?);";

    public static final String REMOVE_TAG = "DELETE FROM gift_certificate_tag " +
                                            "WHERE gift_certificate_id = ? AND tag_id = ?";

    public static final String FIND_ALL_TAGGED_CERTIFICATES = "SELECT DISTINCT gc.id, " +
                                                                     "gc.name, " +
                                                                     "gc.description, " +
                                                                     "gc.price, " +
                                                                     "gc.duration, " +
                                                                     "gc.create_date, " +
                                                                     "gc.last_update_date " +
                                                              "FROM gift_certificate_tag " +
                                                              "INNER JOIN gift_certificate gc " +
                                                              "ON gc.id = gift_certificate_tag.gift_certificate_id;";

    public static final String FIND_ALL_CERTIFICATES_PARAM_SEARCH = "SELECT gc.id, " +
                                                                           "gc.name, " +
                                                                           "gc.description, " +
                                                                           "gc.price, " +
                                                                           "gc.duration, " +
                                                                           "gc.create_date, " +
                                                                           "gc.last_update_date " +
                                                              "FROM gift_certificate_tag " +
                                                              "INNER JOIN gift_certificate gc " +
                                                              "ON gc.id = gift_certificate_tag.gift_certificate_id " +
                                                              "INNER JOIN tag t " +
                                                              "ON t.id = gift_certificate_tag.tag_id ";

    private GiftCertificateQueries() {}

    //TODO: find short way to substitute params in query
    public static String findAllCertificatesByParams(SearchParamRequest spr) {
        StringBuffer queryBuilder = new StringBuffer(FIND_ALL_CERTIFICATES_PARAM_SEARCH);
        getTagName(queryBuilder, spr);
        getCertificateName(queryBuilder, spr);
        getCertificateDescription(queryBuilder, spr);
        getOrder(queryBuilder, spr);
        getOrderSortDate(queryBuilder, spr);
        getOrderSortName(queryBuilder, spr);
        queryBuilder.append(";");
        System.out.println(queryBuilder);
        return queryBuilder.toString();
    }

    private static void getTagName(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getTagName() != null) {
            queryBuilder.append("WHERE t.name = ").append(spr.getTagName()).append(" ");
            if (spr.getPartName() != null || spr.getPartDescription() != null){
                queryBuilder.append(" AND ");
            }
        }
    }

    private static void getCertificateName(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getPartName() != null) {
            queryBuilder.append(" INSTR(gc.name, ").append(spr.getPartName()).append(") ");

            if (spr.getPartDescription() != null) {
                queryBuilder.append(" AND ");
            }
        }
    }

    private static void getCertificateDescription(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getPartDescription() != null) {
            queryBuilder.append(" INSTR(gc.description, ").append(spr.getPartDescription()).append(") ");
        }
    }

    private static void getOrder(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getOrderDate() != null || spr.getOrderName() != null) {
            queryBuilder.append("ORDER BY ");
        }
    }

    private static void getOrderSortDate(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getOrderDate() != null) {
            queryBuilder.append("gc.create_date ");

            if (spr.getSortDate() == null) {
                queryBuilder.append(" ");
            } else {
                queryBuilder.append(spr.getSortDate());
            }

            if (spr.getOrderName() != null) {
                queryBuilder.append(", ");
            }
        }
    }

    private static void getOrderSortName(StringBuffer queryBuilder, SearchParamRequest spr) {
        if (spr.getOrderName() != null) {
            queryBuilder.append("gc.name ");

            if (spr.getSortName() == null) {
                queryBuilder.append(" ");
            } else {
                queryBuilder.append(spr.getSortName());
            }
        }
    }
}