package org.agile.dfs.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IBatisTransactionFactory {

    private static final IBatisSessionFactory sessionFactory = new IBatisSessionFactory();

    @SuppressWarnings("unchecked")
    public <T> T findService(final Class<T> clz, String[] transMethodPattern) {
        IBatisSessionHolder holder = sessionFactory.getSqlMapSession();
        final IBatisTransactionProxy agent = new IBatisTransactionProxy(holder, clz, transMethodPattern);
        T service = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), clz.getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return agent.invoke(method, args);
                    }
                });
        return service;
    }
}
