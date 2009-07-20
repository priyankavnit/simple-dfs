package org.agile.dfs.rpc.exception;


public class RpcOperateException extends RpcException {
    private static final long serialVersionUID = 1978;

    public RpcOperateException(Exception e) {
        super(e);
    }

    public RpcOperateException(String msg) {
        super(msg);
    }

    public RpcOperateException(String msg, Exception e) {
        super(msg, e);
    }
}
