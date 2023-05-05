package com.epam.esm.utils.mappers;

import com.epam.esm.persistence.entity.impl.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TagMapper is the utility class which used result set extraction to tag object
 */
public class TagMapper implements RowMapper<Tag> {

    /**
     * Default constructor
     */
    public TagMapper() {}

    /**
     * Extracts result set to tag object
     *
     * @param rs requested result set
     * @param i number of the current row
     * @return {@code Tag} mapped object
     * @throws SQLException if some low-level operations failed
     */
    @Override
    public Tag mapRow(ResultSet rs, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}