package org.agile.dfs.name.handler;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.action.SpaceLocate;
import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;

public class SpaceLocateHandler implements ActionHandler {

    public void handle(DfsBaseAction act, RpcContext context) {
        SpaceLocate action = (SpaceLocate) act;
        try {
            // context.write("ip:127.0.0.1,port:45100,type:DATA,status:RUN");
            BlockItem item = new BlockItem();
            context.success(item.toString());
            // TODO Pull Up to NameRunnable, total impl by one place.
            // context.write("\0\0\0\0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
