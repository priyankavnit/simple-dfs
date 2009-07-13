package org.agile.dfs.name.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private static final ConnectionPool pool = ConnectionPool.instance();

    public void execute(final String sql) {
        this.doInStatement(new StatementAction() {
            public Object excute(Statement stat) throws Exception {
                logger.debug("Excute sql: {}",sql);
                stat.execute(sql);
                return null;
            }
        }, false);
    }

    public Object doInConnection(ConnectionAction action, boolean transaction) throws RuntimeException {
        Object result = null;
        Connection conn = pool.getConnection();
        try {
            if (transaction) {
                conn.setAutoCommit(false);
            }
            result = action.excute(conn);
            if (transaction) {
                conn.commit();
            }
            if (transaction) {
                conn.setAutoCommit(true);
            }
            return result;
        } catch (Throwable e) {
            if (transaction) {
                try {
                    conn.rollback();
                } catch (SQLException er) {
                    logger.error("Excute rollback error!", er);
                    throw new RuntimeException(er);
                }
            }
            // logger.error("Excute jdbc action error!", e);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    public Object doInStatement(final StatementAction action, boolean transaction) throws RuntimeException {
        Object obj = this.doInConnection(new ConnectionAction() {
            public Object excute(Connection conn) throws Exception {
                Statement stat = null;
                try {
                    stat = conn.createStatement();
                    Object res = action.excute(stat);
                    return res;
                } finally {
                    close(stat);
                }
            }
        }, transaction);
        return obj;
    }

    public Object doInStatement(final String sql, final PreparedStatementAction action, boolean transaction)
            throws RuntimeException {
        logger.debug("SQL:" + sql);
        Object obj = this.doInConnection(new ConnectionAction() {
            public Object excute(Connection conn) throws Exception {
                PreparedStatement pstat = null;
                try {
                    pstat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    Object res = action.excute(pstat);
                    return res;
                } finally {
                    close(pstat);
                }
            }
        }, transaction);
        return obj;
    }

    private void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            conn = null;
            throw new RuntimeException(se);
        }
    }

    private void close(Statement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException e) {
            stat = null;
            throw new RuntimeException(e);
        }
    }

    /* two interface, for callback */
    public interface ConnectionAction {
        public Object excute(Connection conn) throws Exception;
    }

    public interface PreparedStatementAction {
        public Object excute(PreparedStatement pstat) throws Exception;
    }

    public interface StatementAction {
        public Object excute(Statement stat) throws Exception;
    }
}
