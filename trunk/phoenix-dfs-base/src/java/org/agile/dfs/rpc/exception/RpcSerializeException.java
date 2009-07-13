package org.agile.dfs.rpc.exception;

import org.agile.dfs.core.exception.DfsException;

public class RpcSerializeException extends DfsException {
    private static final long serialVersionUID = 1978;

    public RpcSerializeException(String msg) {
        super(msg);
    }

    public RpcSerializeException(String msg, Exception e) {
        super(msg, e);
    }
}
