package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.config.db.DatasourceConnector;
import com.epam.esm.persistence.entity.impl.GiftCertificate;
import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.utils.creators.GiftCertificateCreator;
import com.epam.esm.utils.mappers.GiftCertificateMapper;
import com.epam.esm.utils.mappers.TagMapper;
import com.epam.esm.utils.queries.GiftCertificateQuery;
import com.epam.esm.utils.search.RequestHandler;
import com.epam.esm.utils.search.request.SearchParamRequest;
import com.epam.esm.utils.search.response.SearchParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * GiftCertificateDaoImpl class is the service class and implementor of GiftCertificateDao interface
 * Used to execute SQL commands on datasource via Spring JDBC Template
 */
@Service
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    /**
     * Field to hold DatasourceConnector object
     */
    private final DatasourceConnector dsc;

    /**
     * Constructs GiftCertificateDaoImpl with DataSourceConnector object
     * NOTE: If data source connector is null it will create and inject this dependency according to @Autowired
     * annotation
     *
     * @param dsc DatasourceConnector object, which provides connection to data source
     */
    @Autowired
    public GiftCertificateDaoImpl(DatasourceConnector dsc) {
        this.dsc = dsc;
    }

    /**
     * Creates row in database table
     *
     * @param gCert request object, holds gift certificate values for create
     * @return {@code Long} holder for id of created entity
     */
    @Override
    public Long create(GiftCertificate gCert) {
        KeyHolder key = new GeneratedKeyHolder();
        dsc.getJdbcTemplate().update(GiftCertificateCreator.create(GiftCertificateQuery.CREATE, gCert), key);
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    /**
     * Finds row by id in database table and maps it to object
     *
     * @param id requested parameter for row search, holds gift certificate id value
     * @return {@code GiftCertificate} mapped object
     */
    @Override
    public GiftCertificate findById(Long id) {
        return dsc.getJdbcTemplate().queryForObject(GiftCertificateQuery.FIND_BY_ID, new GiftCertificateMapper(), id);
    }

    /**
     * Updates row in database table by request object
     *
     * @param gCert request object, holds gift certificate values for update
     * @return {@code true} if the row was updated
     */
    @Override
    public Boolean update(GiftCertificate gCert) {
        return dsc.getJdbcTemplate().update(GiftCertificateQuery.UPDATE, GiftCertificateCreator.getArgs(gCert)) == 1;
    }

    /**
     * Deletes row in database table by id
     *
     * @param id requested parameter for row delete, holds gift certificate id value
     * @return {@code true} if the row was deleted
     */
    @Override
    public Boolean delete(Long id) {
        return dsc.getJdbcTemplate().update(GiftCertificateQuery.DELETE, id) == 1;
    }

    /**
     * Finds all rows in database table, maps every row to object and put them into set
     *
     * @return {@code Set<GiftCertificate>} set of mapped objects
     */
    @Override
    public Set<GiftCertificate> findAll() {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(GiftCertificateQuery.FIND_ALL,
                                                               new GiftCertificateMapper()));
    }

    /**
     * Checks if requested by id row exists
     *
     * @param id requested param for row count, holds gift certificate id value
     * @return {@code true} if row exists
     */
    @Override
    public Boolean existById(Long id) {
        return dsc.getJdbcTemplate().queryForObject(GiftCertificateQuery.EXIST_BY_ID, Integer.class, id) == 1;
    }

    /**
     * Checks if requested by name exists
     *
     * @param name requested param for row count, holds gift certificate name value
     * @return {@code true} if row exists
     */
    @Override
    public Boolean existByName(String name) {
        return dsc.getJdbcTemplate().queryForObject(GiftCertificateQuery.EXIST_BY_NAME, Integer.class, name) == 1;
    }

    /**
     * Finds row by name in database table and maps it to object
     *
     * @param name requested parameter for row search, holds gift certificate name value
     * @return {@code GiftCertificate} mapped object
     */
    @Override
    public GiftCertificate findByName(String name) {
        return dsc.getJdbcTemplate().queryForObject(GiftCertificateQuery.FIND_BY_NAME,
                                                    new GiftCertificateMapper(),
                                                    name);
    }

    /**
     * Finds all rows in result database table, maps every row to object and put them into set
     *
     * @return {@code Set<GiftCertificate>} set of mapped objects
     */
    @Override
    public Set<GiftCertificate> findAllTagged() {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(GiftCertificateQuery.FIND_TAGGED,
                                                               new GiftCertificateMapper()));
    }

    /**
     * Finds all rows by search param request in result database table, maps every row to object and forms
     * search param response with mapped objects and requested param map
     *
     * @param spReq object, which contains request params maps
     * @return {@code SearchParamResponse<GiftCertificate>} object, which contains request params map and
     * set of mapped objects
     */
    @Override
    public SearchParamResponse<GiftCertificate> findAllSearch(SearchParamRequest spReq) {
        SearchParamResponse<GiftCertificate> spResp = new SearchParamResponse<>();
        Set<GiftCertificate> gCerts = new LinkedHashSet<>(dsc.getJdbcTemplate()
                                                             .query(RequestHandler.produceSqlQuery(spReq),
                                                                    new GiftCertificateMapper()));
        RequestHandler.scrubParams(spReq, spResp);
        spResp.setItems(gCerts);
        return spResp;
    }

    /**
     * Finds tag rows by gift certificate id, maps every row into object and put them into set
     *
     * @param gcId requested parameter for rows search, holds gift certificate id value
     * @return {@code Set<Tag>} set of mapped objects
     */
    @Override
    public Set<Tag> findTags(Long gcId) {
        return new LinkedHashSet<>(dsc.getJdbcTemplate().query(GiftCertificateQuery.FIND_TAGS, new TagMapper(), gcId));
    }

    /**
     * Creates row in result database table for requested ids
     *
     * @param gcId requested parameter for row creating, holds gift certificate id value
     * @param tagId requested parameter for row creating, holds tag id value
     * @return {@code true} if the row was created
     */
    @Override
    public Boolean addTag(Long gcId, Long tagId) {
        return dsc.getJdbcTemplate().update(GiftCertificateQuery.ADD_TAG, gcId, tagId) == 1;
    }

    /**
     * Deletes row from result database for requested ids
     *
     * @param gcId requested parameter for row creating, holds gift certificate id value
     * @param tagId requested parameter for row creating, holds tag id value
     * @return {@code true} if the row was deleted
     */
    @Override
    public Boolean deleteTag(Long gcId, Long tagId) {
        return dsc.getJdbcTemplate().update(GiftCertificateQuery.DELETE_TAG, gcId, tagId) == 1;
    }
}