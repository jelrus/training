package com.epam.esm.utils.rsets;

import com.epam.esm.entity.impl.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public final class TagConverter {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public static Tag convertSingle(ResultSet rs) {
        Tag tag = new Tag();

        try {
            while (rs.next()) {
                tag = convertFields(rs);
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Result set conversion for single object failed.");
        }

        return tag;
    }

    public static Set<Tag> convertMultiple(ResultSet rs) {
        Set<Tag> tags = new LinkedHashSet<>();

        try {
            while (rs.next()) {
                tags.add(convertFields(rs));
            }
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Result set conversion for multiple objects failed.");
        }

        return tags;
    }

    private static Tag convertFields(ResultSet rs) {
        Tag tag = new Tag();

        try {
            tag.setId(rs.getLong("id"));
            tag.setName(rs.getString("name"));
        } catch (SQLException e) {
            LOGGER_ERROR.error("[Tag] Result set conversion for fields failed.");
        }

        return tag;
    }
}