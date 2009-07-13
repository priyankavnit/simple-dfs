package org.agile.dfs.core.exception;

public class DfsException extends RuntimeException {

    private static final long serialVersionUID = 1978;

    public DfsException() {

    }

    public DfsException(Throwable e) {
        super(e);
    }

    public DfsException(String msg) {
        super(msg);
    }

    public DfsException(String msg, Throwable e) {
        super(msg, e);
    }

}
