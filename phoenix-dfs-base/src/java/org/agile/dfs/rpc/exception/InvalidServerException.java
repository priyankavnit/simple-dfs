package org.agile.dfs.rpc.exception;

import java.io.IOException;

import org.agile.dfs.core.exception.DfsException;

public class InvalidServerException extends DfsException {

    private static final long serialVersionUID = 1978;

    public InvalidServerException(String msg, IOException e) {
        super(msg, e);
    }

}
