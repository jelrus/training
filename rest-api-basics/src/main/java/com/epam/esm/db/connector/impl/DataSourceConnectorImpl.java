package com.epam.esm.db.connector.impl;

import com.epam.esm.db.connector.DataSourceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSourceConnectorImpl implements DataSourceConnector {

    private Connection connection;

    @Autowired
    public DataSourceConnectorImpl() {
        connect();
    }

    @Override
    public boolean connect() {
        try {
            Context initContext = new InitialContext();
            DataSource dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/rest_api_basics");
            connection = dataSource.getConnection();
            return true;
        } catch (NamingException | SQLException n) {
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}