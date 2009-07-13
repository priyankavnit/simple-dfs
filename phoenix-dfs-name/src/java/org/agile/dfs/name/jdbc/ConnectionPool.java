package org.agile.dfs.name.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.agile.dfs.core.common.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPool {
    /** Logger for this class */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private String driver, url, userName, passWord;
    private int maxConnNum = 50, minConnNum = 10;
    private List pool = new ArrayList(maxConnNum);
    private static final ConnectionPool _instance = new ConnectionPool();

    private ConnectionPool() {
        driver = Configuration.getProperty("name.jdbc.driver", null);
        url = Configuration.getProperty("name.jdbc.url", null);
        userName = Configuration.getProperty("name.jdbc.username", null);
        passWord = Configuration.getProperty("name.jdbc.password", null);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find driver class {} ! ", driver);
        }
    }

    public static ConnectionPool instance() {
        return _instance;
    }

    public synchronized Connection getConnection() {
        // TODO l5, impl thread bound conn
        if (pool.size() > 0) {
            Connection c = (Connection) pool.get(pool.size() - 1);
            pool.remove(pool.size() - 1);
            return c;
        } else {
            return _getConnection();
        }
    }

    public synchronized void close(Object proxy, Connection conn) {
        // logger.debug("Back connection b into pools.");
        if (pool.size() < minConnNum) {
            pool.add(proxy);
        } else {
            // really close connection
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
                logger.error("Close connection error!", e);
            }
        }
    }

    private Connection _getConnection() {
        try {
            logger.debug("Get new database connection.");
            Connection conn = DriverManager.getConnection(url, userName, passWord);
            Object pconn = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[] { Connection.class }, new ConnectionWrapper(conn));
            return (Connection) pconn;
        } catch (SQLException e) {
            logger.error("Connect database error! ", e);
            throw new RuntimeException("Connect database error!", e);
        }
    }

    class ConnectionWrapper implements InvocationHandler {
        private Connection conn;

        public ConnectionWrapper(Connection conn) {
            this.conn = conn;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();
            if ("close".equals(name)) {
                close(proxy, conn);
                return null;
            } else {
                return method.invoke(conn, args);
            }
        }

    }
}
