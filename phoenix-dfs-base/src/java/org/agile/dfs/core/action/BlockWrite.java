package org.agile.dfs.core.action;

/**
 * Action example: action:block.write;arg:/home/fileName
 * 
 * @author kevin
 * 
 */
public class BlockWrite extends DfsBaseAction {

    public static final String ACTION_NAME = "block.write";

    public BlockWrite() {
        this.setName(ACTION_NAME);
    }

}
