package org.agile.dfs.rpc.exception;

import org.agile.dfs.core.exception.DfsException;

public class RpcOperateException extends DfsException {
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
