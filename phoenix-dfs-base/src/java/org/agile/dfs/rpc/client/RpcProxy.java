package org.agile.dfs.rpc.client;

import java.io.IOException;
import java.lang.reflect.Method;

import org.agile.dfs.rpc.endpoint.EndpointFactory;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.exception.RpcOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.piple.RpcResponse;

@SuppressWarnings("unchecked")
public class RpcProxy {
    private static final EndpointFactory endpointMgr = new EndpointFactory();
    private Endpointable endpoint;
    private Class interfaceClz;
    private RpcCaller caller;

    public RpcProxy(String endstring, Class clz) {
        this.interfaceClz = clz;
        this.endpoint = endpointMgr.findEndpoint(endstring);
        this.caller = new RpcCaller(endpoint);
    }

    public Object invoke(Method method, Object[] args) {
        RpcRequest req = new RpcRequest();
        req.setInterfaceClz(interfaceClz.getName());
        req.setMethodName(method.getName());
        req.setArgs(args);
        try {
            RpcResponse resp = caller.call(req);
            return resp.getResult();
        } catch (IOException e) {
            throw new RpcOperateException("Network io exception! ", e);
        }
    }

}
