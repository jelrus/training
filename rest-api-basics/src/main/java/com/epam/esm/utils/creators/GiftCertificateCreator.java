package com.epam.esm.utils.creators;

import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.utils.date.IsoDateFormatter;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * GiftCertificateCreator is the utility class, used to support dao with supplementary methods
 */
public final class GiftCertificateCreator {

    /**
     * Default constructor
     */
    private GiftCertificateCreator() {}

    /**
     * Creates prepared statement with requested query and gift certificate values
     *
     * @param query requested query
     * @param gCert requested object
     * @return {@code PreparedStatementObject} callback interface
     */
    public static PreparedStatementCreator create(String query, GiftCertificate gCert) {
        return c -> {
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, gCert.getName());
            ps.setString(2, gCert.getDescription());
            ps.setBigDecimal(3, gCert.getPrice());
            ps.setInt(4, gCert.getDuration());
            ps.setString(5, IsoDateFormatter.dateToString(gCert.getCreate()));
            ps.setString(6, IsoDateFormatter.dateToString(gCert.getUpdate()));
            return ps;
        };
    }

    /**
     * Returns requested gift certificate fields as array
     *
     * @param gCert requested object
     * @return {@code Object[]} array of gift certificate fields
     */
    public static Object[] getArgs(GiftCertificate gCert) {
        return new Object[]{gCert.getName(),
                            gCert.getDescription(),
                            gCert.getPrice(),
                            gCert.getDuration(),
                            IsoDateFormatter.dateToString(gCert.getCreate()),
                            IsoDateFormatter.dateToString(gCert.getUpdate()),
                            gCert.getId()};
    }
}