package org.agile.dfs.name.handler;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;

public class HelloWordHandler implements ActionHandler {

    public void handle(DfsBaseAction action, RpcContext context) {
        String name = "guest";
        String[] args = action.getArguments();
        if (args != null && args.length > 0) {
            name = args[0];
        }
        context.success("Hello " + name + " ! ");
    }
}
