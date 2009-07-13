package org.agile.dfs.core.action;

/**
 * Action example: action:file.create;arg:/home/fileName
 * 
 * @author kevin
 * 
 */
public class FileCreate extends DfsBaseAction {
    public static final String ACTION_NAME = "file.create";

    public FileCreate() {
        this.setName(ACTION_NAME);
    }

}
