package org.agile.dfs.name.service;

public interface FileService {

    public boolean createNewFile(String schema, String fullPath);

    public boolean createNewFiles(String schema, String fullPath);

    public boolean mkdir(String schema, String fullPath);

    public boolean mkdirs(String schema, String fullPath);

    public boolean exists(String schema, String fullPath);

    public void delete(String schema, String fullPath);

}
