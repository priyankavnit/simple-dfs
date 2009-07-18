package org.agile.dfs.name.handler;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.action.FileCreate;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.core.handler.ActionHandler;
import org.agile.dfs.core.rpc.RpcContext;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.SchemaManager;
import org.agile.dfs.util.StringUtil;

public class FileCreateHandler implements ActionHandler {
    private static final SchemaManager nsMgr = (SchemaManager) ServiceFactory.findService(SchemaManager.class);
    private static final FileItemManager fileMgr = (FileItemManager) ServiceFactory.findService(FileItemManager.class);

    // private static final DirItemManager dirMgr = (DirItemManager) ServiceFactory.findService(DirItemManager.class);

    public void handle(DfsBaseAction act, RpcContext context) {
        FileCreate action = (FileCreate) act;
        String[] args = action.getArguments();
        if (args == null || args.length < 2) {
            throw new RuntimeException("Args should include ns and path!");
        }
        String nt = args[0];
        String ph = args[1];
        boolean id = args.length >= 3 ? StringUtil.getBoolean(args[2]) : false;
        boolean ip = args.length >= 4 ? StringUtil.getBoolean(args[3]) : false;
        // TODO l3, validate ns and path (name format, length and level)
        Schema ns = nsMgr.findByName(nt);
        if (ns == null) {
            throw new RuntimeException("Name space is null!");
        }
        FileItem item;
        if (id) {
            item = fileMgr.mkdir(ns, ph, ip);
        } else {
            item = fileMgr.mkfile(ns, ph);
        }
        context.success(item.getId());
    }

    public static void main(String[] args) {
        String dir = "/home/agile/photo/one.jpg";
        String[] paths = dir.split("/");
        for (int i = 0; i < paths.length; i++) {
            System.out.println("->" + paths[i]);
        }

        String[] paths2 = StringUtil.simpleSplit(dir, '/');
        for (int i = 0; i < paths2.length; i++) {
            System.out.println("->" + paths2[i]);
        }
    }
}
