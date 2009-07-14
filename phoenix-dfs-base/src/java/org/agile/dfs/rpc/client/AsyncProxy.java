package org.agile.dfs.rpc.client;

import java.io.IOException;
import java.lang.reflect.Method;

import org.agile.dfs.rpc.endpoint.EndpointFactory;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.exception.RpcOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.piple.RpcResponse;
import org.agile.dfs.util.MulValueThreadLocal;

@SuppressWarnings("unchecked")
public class AsyncProxy {
    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();
    private static final EndpointFactory endpointMgr = new EndpointFactory();
    private Endpointable endpoint;
    private Class interfaceClz;
    private AsyncCaller caller;

    public AsyncProxy(String endstring, Class clz) {
        this.interfaceClz = clz;
        this.endpoint = endpointMgr.findEndpoint(endstring);
        this.caller = new AsyncCaller(endpoint);
    }

    public void invoke(Method method, Object[] args) {
        RpcRequest req = new RpcRequest();
        req.setInterfaceClz(interfaceClz.getName());
        req.setMethodName(method.getName());
        req.setArgs(args);
        try {
            local.set("dfs.endpoint", endpoint);
            caller.call(req);
        } catch (IOException e) {
            throw new RpcOperateException("Network io exception! ", e);
        }
    }

}
