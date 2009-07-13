package org.agile.dfs.name.service;

public interface FileService {

    public boolean createNewFile(String nameSpace, String fullPath);

    public boolean mkdir(String nameSpace, String fullPath, boolean parent);

    public boolean exists(String nameSpace, String fullPath);

}
