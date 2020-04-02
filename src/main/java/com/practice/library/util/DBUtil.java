package com.practice.library.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static DBUtil dbUtil;
    private DataSource dataSource;
    private Connection jdbcConnection;

    public DBUtil() {
        ConnectionPool pool = new ConnectionPool();
        this.dataSource = pool.setUp();
    }

    public static DBUtil getInstance() {
        if (dbUtil == null) {
            dbUtil = new DBUtil();
        }
        return dbUtil;
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            jdbcConnection = this.dataSource.getConnection();
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }
}
