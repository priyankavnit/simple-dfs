package org.agile.dfs.name.handler;

import java.io.IOException;

import org.agile.dfs.core.action.BlockLocate;
import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;

public class BlockLocateHandler implements ActionHandler {

    public void handle(DfsBaseAction action, RpcContext context) {
        BlockLocate faction = (BlockLocate) action;
        System.out.println("action is:" + faction);
        try {
            context.write("ip:127.0.0.1,port:45100,type:DATA,status:RUN");
            context.write("\0\0\0\0");
            context.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
