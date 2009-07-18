package org.agile.dfs.dao;

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.agile.dfs.util.ServiceFactory;

import com.ibatis.sqlmap.client.SqlMapSession;

public class IBatisTransactionProxy<T> {
    private IBatisSessionHolder holder;
    private SqlMapSession session;
    private Object target;
    private String[] pattern;

    public IBatisTransactionProxy(IBatisSessionHolder holder, Class<T> clz, String[] pattern) {
        target = ServiceFactory.findService(clz);
        this.pattern = pattern;
        this.session = holder.getSession();
        this.holder = holder;
    }

    public Object invoke(Method method, Object[] args) {
        String name = method.getName();
        // TODO handle nest transaction match
        boolean transaction = matches(name, pattern);
        try {
            if (transaction) {
                session.startTransaction();
            }
            Object res = method.invoke(target, args);
            if (transaction) {
                session.commitTransaction();
            }
            return res;
        } catch (Throwable t) {
            throw new IBatisDaoException("Fail to execute sqlmap action!", t);
        } finally {
            try {
                session.endTransaction();
            } catch (SQLException e) {
                throw new IBatisDaoException("Fail to end sqlmap session transaction!", e);
            } finally {
                if (!holder.isBound()) {
                    session.close();
                }
            }
        }
    }

    private boolean matches(String name, String[] regxs) {
        for (int i = 0, l = regxs.length; i < l; i++) {
            if (name.matches(regxs[i])) {
                return true;
            }
        }
        return false;
    }
}
