package org.agile.dfs.rpc.exception;


public class RpcSerializeException extends RpcException {
    private static final long serialVersionUID = 1978;

    public RpcSerializeException(String msg) {
        super(msg);
    }

    public RpcSerializeException(String msg, Exception e) {
        super(msg, e);
    }
}
