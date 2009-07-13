package org.agile.dfs.server;

import org.agile.dfs.core.action.DfsBaseAction;

public interface DfsServerHandler {

    public String capable();

    public void handle(DfsServerContext context, DfsBaseAction action);
}
