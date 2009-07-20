package org.agile.dfs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapSession;

public class IBatisTemplate {

    private IBatisSessionFactory sessionFactory = new IBatisSessionFactory();

    public Object doInSqlMapSession(SqlMapAction action) {
        SqlMapSession session = sessionFactory.getSqlMapSession().getSession();
        try {
            return action.execute(session);
        } catch (SQLException e) {
            throw new IBatisDaoException(e);
        }

    }

    public Object insert(final String statementId, final Object param) {
        return this.doInSqlMapSession(new SqlMapAction() {
            public Object execute(SqlMapSession session) throws SQLException {
                session.insert(statementId, param);
                return param;
            }
        });
    }

    public void update(final String statementId, final Object param) throws IBatisDaoException {
        this.doInSqlMapSession(new SqlMapAction() {
            public Object execute(SqlMapSession session) throws SQLException {
                session.update(statementId, param);
                return param;
            }
        });
    }

    public void delete(final String statementId, final Object param) throws IBatisDaoException {
        this.doInSqlMapSession(new SqlMapAction() {
            public Object execute(SqlMapSession session) throws SQLException {
                session.delete(statementId, param);
                return null;
            }
        });
    }

    public Object findById(String statementId, Object pid) throws SQLException {
        return null;
    }

    public Object findByParameter(final String statementId, final Object param) throws IBatisDaoException {
        return this.doInSqlMapSession(new SqlMapAction() {
            public Object execute(SqlMapSession session) throws SQLException {
                return session.queryForObject(statementId, param);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List findListByParameter(final String statementId, final Object param) throws IBatisDaoException {
        return (List) this.doInSqlMapSession(new SqlMapAction() {
            public Object execute(SqlMapSession session) throws SQLException {
                return session.queryForList(statementId, param);
            }
        });
    }

    public interface SqlMapAction {
        public Object execute(SqlMapSession session) throws SQLException;
    }
}
