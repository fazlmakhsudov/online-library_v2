package com.practice.library.util;


import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;

public class ConnectionPool {
    private static final String URL = DBInfo.getJdbcURL();
    private static final String USERNAME = DBInfo.getJdbcUsername();
    private static final String PASSWORD = DBInfo.getJdbcPassword();
    private static final int MAX_TOTAL_CONN = DBInfo.getMaxTotalConn();

    private GenericObjectPool<PoolableConnection> genericObjectPool = null;

    public DataSource setUp() {
        // Creates a connection factory object which will be use by
        // the pool to create the connection object. We passes the
        // JDBC url info, username and password.
        ConnectionFactory cf = new DriverManagerConnectionFactory(
                ConnectionPool.URL,
                ConnectionPool.USERNAME,
                ConnectionPool.PASSWORD);

        // Creates a PoolableConnectionFactory that will wraps the
        // connection object created by the ConnectionFactory to add
        // object pooling functionality.
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
        pcf.setValidationQuery("SELECT 1");

        // Creates an instance of GenericObjectPool that holds our
        // pool of connections object.
        genericObjectPool = new GenericObjectPool<>(pcf);
        genericObjectPool.setMaxTotal(ConnectionPool.MAX_TOTAL_CONN);
        pcf.setPool(genericObjectPool);

        return new PoolingDataSource<>(genericObjectPool);
    }

    public GenericObjectPool getGenericObjectPool() {
        return genericObjectPool;
    }

    /**
     * Prints connection pool status.
     */
    public String printStatus() {
        return "MinIdle   : " + getGenericObjectPool().getMinIdle() + "; " +
                "MaxTotal: " + getGenericObjectPool().getMaxTotal() + "; " +
                "num idle  : " + getGenericObjectPool().getNumIdle();
    }
}
