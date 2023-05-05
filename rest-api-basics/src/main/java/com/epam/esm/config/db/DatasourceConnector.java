package com.epam.esm.config.db;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * DatasourceConnector is the interface that delegates data source connection contract to implementor
 */
public interface DatasourceConnector {

    /**
     * Contract for establishing connection with data source
     *
     * @return {@code true} if connection was established
     */
    boolean connect() throws SQLException;

    /**
     * Contract to retrieve JdbcTemplate object for Spring JDBC Template usage
     *
     * @return {@code JdbcTemplate} object
     */
    JdbcTemplate getJdbcTemplate();
}