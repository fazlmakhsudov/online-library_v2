package com.practice.library.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBInfo {
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;
    protected static final Logger logger = Logger.getLogger("F%  DB itil initialization");

    private DBInfo() {
    }

    static {
        jdbcURL = "jdbc:mysql://db:3306/new_db";
        jdbcUsername = "root";
        jdbcPassword = "123456mfh";
        logger.log(Level.INFO, "{0} - static block {1}   {2}   {3}", new String[]{logger.getName(), jdbcURL, jdbcUsername, jdbcPassword});
    }

    public static String getJdbcURL() {
        return jdbcURL;
    }

    public static String getJdbcUsername() {
        return jdbcUsername;
    }

    public static String getJdbcPassword() {
        return jdbcPassword;
    }
}
