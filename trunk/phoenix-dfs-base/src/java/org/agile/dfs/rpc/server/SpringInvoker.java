package org.agile.dfs.rpc.server;

import java.lang.reflect.Method;

import org.agile.dfs.rpc.exception.ReflectOperateException;
import org.agile.dfs.rpc.exception.RpcOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.framework.ComponentFactory;

public class SpringInvoker implements RpcInvoker {

    public Object invoke(String clzName, String methodName, Object[] args) throws ReflectOperateException {
        try {
            return doInvoke(clzName, methodName, args);
        } catch (Exception e) {
            throw new ReflectOperateException("Fail to reflect invoke " + clzName + ":" + methodName, e);
        }
    }

    public Object invoke(RpcRequest req) throws ReflectOperateException {
        return this.invoke(req.getInterfaceClz(), req.getMethodName(), req.getArgs());
    }

    private Object doInvoke(String clzName, String methodName, Object[] args) throws Exception {
        Object ins = getInstance(clzName);
        Method method = getMethod(ins.getClass(), methodName, args);
        Object res = method.invoke(ins, args);
        return res;
    }

    @SuppressWarnings("unchecked")
    private Object getInstance(String clzName) throws Exception {
        // TODO l2, cache instance
        Class clz = Class.forName(clzName);
        Object ins = ComponentFactory.getBean("dfs.",clz);
        if (ins == null) {
            throw new RpcOperateException("Can't find service for " + clzName);
        }
        return ins;
    }

    @SuppressWarnings("unchecked")
    private Method getMethod(Class clz, String methodName, Object[] args) {
        Method[] ms = clz.getDeclaredMethods();
        if (ms != null && ms.length != 0) {
            for (int i = 0, l = ms.length; i < l; i++) {
                if (methodName.equals(ms[i].getName())) {
                    if (ms[i].getParameterTypes().length == args.length) {
                        return ms[i];
                    }
                }
            }
        }
        throw new ReflectOperateException("Can't find method " + methodName + " from " + clz.getName());
    }
}
