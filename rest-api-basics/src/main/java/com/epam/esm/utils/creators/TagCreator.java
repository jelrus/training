package com.epam.esm.utils.creators;

import com.epam.esm.persistence.entity.impl.Tag;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * TagCreator is the utility class, used to support dao with supplementary methods
 */
public final class TagCreator {

    /**
     * Default constructor
     */
    private TagCreator() {}

    /**
     * Creates prepared statement with requested query and tag values
     *
     * @param query requested query
     * @param tag requested object
     * @return {@code PreparedStatementObject} callback interface
     */
    public static PreparedStatementCreator create(String query, Tag tag) {
        return c -> {
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        };
    }
}