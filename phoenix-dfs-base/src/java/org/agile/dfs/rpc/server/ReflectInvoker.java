package org.agile.dfs.rpc.server;

import java.lang.reflect.Method;

import org.agile.dfs.rpc.exception.ReflectOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;

public class ReflectInvoker {

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

    @SuppressWarnings("unchecked")
    private Object doInvoke(String clzName, String methodName, Object[] args) throws Exception {
        // TODO l2, cache instance
        Class clz = Class.forName(clzName + "Impl");
        Object ins = clz.newInstance();

        Method method = getMethod(ins.getClass(), methodName, args);
        Object res = method.invoke(ins, args);
        return res;
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
