package org.agile.dfs.name.exception;

import org.agile.dfs.core.exception.DfsException;

public class NameNodeException extends DfsException {
    private static final long serialVersionUID = 1978;

    public NameNodeException() {

    }

    public NameNodeException(String msg) {
        super(msg, null);
    }

    public NameNodeException(String msg, Throwable e) {
        super(msg, e);
    }
}
