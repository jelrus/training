package com.epam.esm.db.connector;

import java.sql.Connection;

public interface DataSourceConnector {

    boolean connect();

    Connection getConnection();
}