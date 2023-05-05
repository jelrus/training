package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.interfaces.entity.TagDao;
import com.epam.esm.config.db.DatasourceConnector;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.creators.TagCreator;
import com.epam.esm.utils.mappers.GiftCertificateMapper;
import com.epam.esm.utils.mappers.TagMapper;
import com.epam.esm.utils.queries.TagQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TagDaoImpl class is the service class and implementor of TagDao interface
 * Used to execute SQL commands on datasource via Spring JDBC Template
 */
@Service
public class TagDaoImpl implements TagDao {

    /**
     * Field to hold DatasourceConnector object
     */
    private final DatasourceConnector dsc;

    /**
     * Constructs TagDaoImpl with DataSourceConnector object
     * NOTE: If data source connector is null it will create and inject this dependency according to @Autowired
     * annotation
     *
     * @param dsc DatasourceConnector object, which provides connection to data source
     */
    @Autowired
    public TagDaoImpl(DatasourceConnector dsc) {
        this.dsc = dsc;
    }

    /**
     * Creates row in database table
     *
     * @param tag request object, holds tag values for create
     * @return {@code Long} holder for id of created entity
     */
    @Override
    public Long create(Tag tag) {
        KeyHolder key = new GeneratedKeyHolder();
        dsc.getJdbcTemplate().update(TagCreator.create(TagQuery.CREATE, tag), key);
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    /**
     * Finds row by id in database table and maps it to object
     *
     * @param id requested parameter for row search, holds tag id value
     * @return {@code Tag} mapped object
     */
    @Override
    public Tag findById(Long id) {
        return dsc.getJdbcTemplate().queryForObject(TagQuery.FIND_BY_ID, new TagMapper(), id);
    }

    /**
     * Deletes row in database table by id
     *
     * @param id requested parameter for row delete, holds tag id value
     * @return {@code true} if the row was deleted
     */
    @Override
    public Boolean delete(Long id) {
        return dsc.getJdbcTemplate().update(TagQuery.DELETE, id) > 0;
    }

    /**
     * Finds all rows in database table, maps every row to object and put them into set
     *
     * @return {@code Set<Tag>} set of mapped objects
     */
    @Override
    public Set<Tag> findAll() {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(TagQuery.FIND_ALL, new TagMapper()));
    }

    /**
     * Checks if requested by id row exists
     *
     * @param id requested param for row count, holds tag id value
     * @return {@code true} if row exists
     */
    @Override
    public Boolean existById(Long id) {
        return dsc.getJdbcTemplate().queryForObject(TagQuery.EXIST_BY_ID, Integer.class, id) == 1;
    }

    /**
     * Checks if requested by name exists
     *
     * @param name requested param for row count, holds tag name value
     * @return {@code true} if row exists
     */
    @Override
    public Boolean existByName(String name) {
        return dsc.getJdbcTemplate().queryForObject(TagQuery.EXIST_BY_NAME, Integer.class, name) == 1;
    }

    /**
     * Finds row by name in database table and maps it to object
     *
     * @param name requested parameter for row search, holds tag name value
     * @return {@code Tag} mapped object
     */
    @Override
    public Tag findByName(String name) {
        return dsc.getJdbcTemplate().queryForObject(TagQuery.FIND_BY_NAME, new TagMapper(), name);
    }

    /**
     * Finds all rows in result database table, maps every row to object and put them into set
     *
     * @return {@code Set<Tag>} set of mapped objects
     */
    @Override
    public Set<Tag> findAllCertificated() {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(TagQuery.FIND_CERTIFICATED, new TagMapper()));
    }

    /**
     * Finds gift certificates rows by tag id, maps every row into object and put them into set
     *
     * @param tagId requested parameter for rows search, holds tag id value
     * @return {@code Set<Tag>} set of mapped objects
     */
    @Override
    public Set<GiftCertificate> findGiftCertificates(Long tagId) {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(TagQuery.FIND_CERTIFICATES,
                                                               new GiftCertificateMapper(),
                                                               tagId));
    }
}