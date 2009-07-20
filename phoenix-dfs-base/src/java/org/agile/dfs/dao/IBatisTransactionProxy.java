package org.agile.dfs.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.util.ServiceFactory;

import com.ibatis.sqlmap.client.SqlMapSession;

public class IBatisTransactionProxy<T> {
    private static final IBatisSessionFactory sessionFactory = new IBatisSessionFactory();
    private Object target;
    private String[] pattern;

    public IBatisTransactionProxy(Class<T> clz, String[] pattern) {
        target = ServiceFactory.findService(clz);
        this.pattern = pattern;
    }

    public Object invoke(Method method, Object[] args) {
        IBatisSessionHolder holder = sessionFactory.getSqlMapSession();
        SqlMapSession session = holder.getSession();
        boolean bound = holder.isBound();
        boolean transaction = matches(method.getName(), pattern) && !holder.isTransaction();
        try {
            if (transaction) {
                session.startTransaction();
                holder.setTransaction(true);
            }
            Object res = method.invoke(target, args);
            if (transaction) {
                session.commitTransaction();
            }
            return res;
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof DfsException) {
                throw (DfsException) target;
            } else {
                throw new RuntimeException(target);
            }
        } catch (Throwable t) {
            throw new IBatisDaoException("Fail to execute method in " + target.getClass() + ":" + method.getName(), t);
        } finally {
            try {
                if (transaction) {
                    session.endTransaction();
                }
            } catch (SQLException e) {
                throw new IBatisDaoException("Fail to end sqlmap session transaction!", e);
            } finally {
                if (!bound) {
                    sessionFactory.clearSqlMapSession(holder);
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
