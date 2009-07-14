package org.agile.dfs.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AsyncProxyFactory {

    /**
     * 
     * @param endpoint
     *            service location flag, ex:tcp://192.168.1.9:8080, http://www.some.com/service
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T findService(final Class<T> clz, final String endpoint) {
        final AsyncProxy agent = new AsyncProxy(endpoint, clz);
        T service = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { clz },
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        agent.invoke(method, args);
                        return null;
                    }
                });
        return service;
    }
}
