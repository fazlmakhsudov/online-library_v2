package com.practice.library.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

public class DBUtil {
    private static DBUtil dbUtil;
    private DataSource dataSource;
    private ConnectionPool connectionPool;
    private Connection jdbcConnection;
    private final Logger logger = Logger.getLogger("DBUtil");

    public DBUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            dbUtil.logger.info("com.mysql.jdbc.Driver can't be found yet :(!!!");
        }
        connectionPool = new ConnectionPool();
        this.dataSource = connectionPool.setUp();
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
            Optional<String> connectionPoolStatus = Optional.of(connectionPool.printStatus());
            String poolStatus = connectionPoolStatus.orElse("report is impossible");
            logger.info(poolStatus);
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
            Optional<String> connectionPoolStatus = Optional.of(connectionPool.printStatus());
            String poolStatus = connectionPoolStatus.orElse("report is impossible");
            logger.info(poolStatus);
        }
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }
}
