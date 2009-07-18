package org.agile.dfs.dao;

import com.ibatis.sqlmap.client.SqlMapSession;

public class IBatisSessionHolder {
    private SqlMapSession session;
    private boolean bound;
    private boolean transaction;

    public IBatisSessionHolder() {
    }

    public SqlMapSession getSession() {
        return session;
    }

    public void setSession(SqlMapSession session) {
        this.session = session;
    }

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }

}
