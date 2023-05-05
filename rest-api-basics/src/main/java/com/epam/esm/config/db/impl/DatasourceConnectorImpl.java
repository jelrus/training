package com.epam.esm.config.db.impl;

import com.epam.esm.config.db.DatasourceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * DatasourceConnectorImpl is the implementor of DataSource connector, used to establish connection with
 * datasource and to create JdbcTemplate object to execute SQL queries, iterate over ResultSets with
 * Spring JDBC Template framework
 */
@Component
public class DatasourceConnectorImpl implements DatasourceConnector {

    /**
     * Field for setting link on datasource resource, must be environment property
     */
    private static final String RESOURCE_LINK = "java:comp/env/jdbc/rest_api_basics";

    /**
     * Field to hold JdbcTemplate object
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs DataSourceConnectorImpl object and connects to datasource
     */
    @Autowired
    public DatasourceConnectorImpl() {
        connect();
    }

    public DatasourceConnectorImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Creates initial context. Establishes connection with datasource via RESOURCE_LINK.
     * Initializes jdbcTemplate object with given datasource.
     *
     * @return {@code true} if connection was established
     * {@code false} if operations with initial context failed
     */
    @Override
    public boolean connect() {
        try {
            Context initContext = new InitialContext();
            DataSource dataSource = (DataSource) initContext.lookup(RESOURCE_LINK);
            jdbcTemplate = new JdbcTemplate(dataSource);
            return true;
        } catch (NamingException n) {
            return false;
        }
    }

    /**
     * Gets JdbcTemplate object
     *
     * @return JdbcTemplate object
     */
    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatasourceConnectorImpl that = (DatasourceConnectorImpl) o;
        return Objects.equals(jdbcTemplate, that.jdbcTemplate);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(jdbcTemplate);
    }
}