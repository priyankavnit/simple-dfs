package org.agile.dfs.dao;

import org.agile.dfs.core.exception.DfsException;

public class IBatisDaoException extends DfsException {
    private static final long serialVersionUID = 1978;

    public IBatisDaoException(String msg) {
        super(msg);
    }

    public IBatisDaoException(Throwable t) {
        super(t);
    }

    public IBatisDaoException(String msg, Throwable t) {
        super(msg, t);
    }

}
