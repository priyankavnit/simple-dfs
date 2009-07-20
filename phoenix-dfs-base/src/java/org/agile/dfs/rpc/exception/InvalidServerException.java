package org.agile.dfs.rpc.exception;

import java.io.IOException;

public class InvalidServerException extends RpcException {

    private static final long serialVersionUID = 1978;

    public InvalidServerException(String msg, IOException e) {
        super(msg, e);
    }

}
