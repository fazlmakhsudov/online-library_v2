package com.practice.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
    private static DBUtil dbUtil;

    {
        this.jdbcURL = DBInfo.getJdbcURL();
        this.jdbcUsername = DBInfo.getJdbcUsername();
        this.jdbcPassword = DBInfo.getJdbcPassword();
    }

    public static DBUtil getInstance() {
        if (dbUtil == null) {
            dbUtil = new DBUtil();
        }
        return dbUtil;
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            DBInfo.logger.info("F%  DbUtil  connect() -> if()");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                DBInfo.logger.info("F%  DbUtil  connect() -> try{}");
            } catch (ClassNotFoundException e) {
                DBInfo.logger.info("F%  DbUtil  connect() -> catch()");
                throw new SQLException(e);

            }
            DBInfo.logger.info("F%  DbUtil  connect() before DriverManager getConnection");
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
            DBInfo.logger.info("F%  DbUtil  connect() -> jdbcConnection " + jdbcConnection.isClosed());
            DBInfo.logger.info("F%  DbUtil  connect() -> jdbcConnecton " + jdbcConnection.toString() + "\n" +
                    jdbcConnection.getMetaData());
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

}
