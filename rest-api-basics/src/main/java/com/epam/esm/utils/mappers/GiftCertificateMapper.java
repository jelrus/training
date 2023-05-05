package com.epam.esm.utils.mappers;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.utils.date.IsoDateFormatter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * GiftCertificateMapper is the utility class which used result set extraction to gift certificate object
 */
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    /**
     * Default constructor
     */
    public GiftCertificateMapper() {}

    /**
     * Extracts result set to gift certificate object
     *
     * @param rs requested result set
     * @param i number of the current row
     * @return {@code GiftCertificate} mapped object
     * @throws SQLException if some low-level operations failed
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int i) throws SQLException {
        GiftCertificate gCert = new GiftCertificate();
        gCert.setId(rs.getLong("id"));
        gCert.setName(rs.getString("name"));
        gCert.setDescription(rs.getString("description"));
        gCert.setPrice(rs.getBigDecimal("price"));
        gCert.setDuration(rs.getInt("duration"));
        gCert.setCreate(IsoDateFormatter.stringToDate(rs.getString("create_date")));
        gCert.setUpdate(IsoDateFormatter.stringToDate(rs.getString("last_update_date")));
        return gCert;
    }
}