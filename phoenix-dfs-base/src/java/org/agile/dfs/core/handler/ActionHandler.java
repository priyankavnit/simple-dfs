package org.agile.dfs.core.handler;

import java.io.IOException;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.rpc.RpcContext;

public interface ActionHandler {
    // public String action();

    // public DfsBaseAction getAction();

    // public DfsSocketContext getSocketContext();

    public void handle(DfsBaseAction action, RpcContext context) throws IOException;
}
