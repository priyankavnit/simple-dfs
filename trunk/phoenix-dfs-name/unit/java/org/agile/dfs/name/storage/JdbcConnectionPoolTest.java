package org.agile.dfs.name.storage;

import java.sql.Connection;
import java.sql.SQLException;

import org.agile.dfs.name.jdbc.ConnectionPool;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JdbcConnectionPoolTest extends TestCase {

    ConnectionPool pool;

    protected void setUp() throws Exception {
        super.setUp();
        pool = ConnectionPool.instance();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetConnection() throws SQLException {
        Connection conn = pool.getConnection();
        Assert.assertNotNull(conn);
        conn.close();
    }

}
