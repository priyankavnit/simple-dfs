package org.agile.dfs.rpc.server;

import org.agile.dfs.rpc.exception.ReflectOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;

public interface RpcInvoker {

    public Object invoke(String clzName, String methodName, Object[] args) throws ReflectOperateException;

    public Object invoke(RpcRequest req) throws ReflectOperateException;

}