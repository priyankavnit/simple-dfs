package org.agile.dfs.name.handler;

import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.core.action.BlockLocate;
import org.agile.dfs.core.action.FileCreate;
import org.agile.dfs.core.action.FileExist;
import org.agile.dfs.core.action.HelloWord;
import org.agile.dfs.core.action.SpaceCommit;
import org.agile.dfs.core.action.SpaceLocate;
import org.agile.dfs.core.handler.ActionHandler;

public class NameHandlerFactory {

    private static final Map map = new HashMap();
    static {
        map.put(HelloWord.ACTION_NAME, new HelloWordHandler());
        map.put(FileExist.ACTION_NAME, new FileExistHandler());
        map.put(FileCreate.ACTION_NAME, new FileCreateHandler());
        map.put(BlockLocate.ACTION_NAME, new BlockLocateHandler());
        map.put(SpaceLocate.ACTION_NAME, new SpaceLocateHandler());
        map.put(SpaceCommit.ACTION_NAME, new SpaceCommitHandler());

    }

    public ActionHandler findByAction(String action) {

        return (ActionHandler) map.get(action);
    }
}
