package org.agile.dfs.rpc.exception;


public class ReflectOperateException extends RpcException {
    private static final long serialVersionUID = 1978;

    public ReflectOperateException(Exception e) {
        super(e);
    }

    public ReflectOperateException(String msg) {
        super(msg);
    }

    public ReflectOperateException(String msg, Exception e) {
        super(msg, e);
    }
}
