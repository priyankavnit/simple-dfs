package org.agile.dfs.rpc.exception;

public class RpcException extends RuntimeException {

    private static final long serialVersionUID = 1978;

    public RpcException() {

    }

    public RpcException(Throwable e) {
        super(e);
    }

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(String msg, Throwable e) {
        super(msg, e);
    }

}
