package mfh.faztech.online_library.util;

import java.util.logging.Logger;

public class DBInfo {
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;
    protected static final Logger logger = Logger.getLogger("F%  DB itil initialization");


    static {
        jdbcURL = "jdbc:mysql://db:3306/new_db";
        jdbcUsername = "root";
        jdbcPassword = "123456mfh";
        logger.info(logger.getName() + " - static block " + jdbcURL + "  " + jdbcUsername + "  " + jdbcPassword);;
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
