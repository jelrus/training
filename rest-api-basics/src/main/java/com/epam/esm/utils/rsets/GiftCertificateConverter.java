package com.epam.esm.utils.rsets;

import com.epam.esm.entity.impl.GiftCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public final class GiftCertificateConverter {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public static GiftCertificate convertSingle(ResultSet rs) {
        GiftCertificate gCert = new GiftCertificate();

        try {
            while (rs.next()) {
                gCert = convertFields(rs);
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Result set conversion for single object failed.");
        }

        return gCert;
    }

    public static Set<GiftCertificate> convertMultiple(ResultSet rs) {
        Set<GiftCertificate> gCerts = new LinkedHashSet<>();

        try {
            while (rs.next()) {
                gCerts.add(convertFields(rs));
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Result set conversion for multiple objects failed.");
        }

        return gCerts;
    }

    private static GiftCertificate convertFields(ResultSet rs) {
        GiftCertificate gCert = new GiftCertificate();

        try {
            gCert.setId(rs.getLong("id"));
            gCert.setName(rs.getString("name"));
            gCert.setDescription(rs.getString("description"));
            gCert.setPrice(rs.getBigDecimal("price"));
            gCert.setDuration(rs.getInt("duration"));
            gCert.setCreateDate(rs.getTimestamp("create_date"));
            gCert.setLastUpdateDate(rs.getTimestamp("last_update_date"));
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Gift Certificate] Result set conversion for fields failed.");
        }

        return gCert;
    }
}