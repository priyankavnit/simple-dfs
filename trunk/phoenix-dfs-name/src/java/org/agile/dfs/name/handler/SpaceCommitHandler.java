package org.agile.dfs.name.handler;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.action.SpaceCommit;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;

public class SpaceCommitHandler implements ActionHandler {

    public void handle(DfsBaseAction action, RpcContext context) {
        SpaceCommit faction = (SpaceCommit) action;
        try {
            context.success("true");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
