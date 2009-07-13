package org.agile.dfs.name.handler;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.action.FileExist;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;
import org.agile.dfs.name.manager.FileItemManager;

public class FileExistHandler implements ActionHandler {
    private static final FileItemManager fileMgr = (FileItemManager) ServiceFactory.findService(FileItemManager.class);

    public void handle(DfsBaseAction action, RpcContext context) {
        FileExist faction = (FileExist) action;
        String[] args = faction.getArguments();
        if (args != null && args.length >= 2) {
            String ns = args[0];
            String ph = args[1];
            FileItem item = fileMgr.findByPath(ns, ph);
            if (item != null) {
                context.success(item.toString());
            } else {
                context.success("null");
            }
        } else {
            context.exception("Args setting error! ");
        }
    }
}
