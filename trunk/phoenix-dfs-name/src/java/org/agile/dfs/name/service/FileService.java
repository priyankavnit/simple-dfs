package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.FileItem;

public interface FileService {

    public String createNewFile(String schema, String fullPath);

    public String createNewFiles(String schema, String fullPath);

    public boolean mkdir(String schema, String fullPath);

    public boolean mkdirs(String schema, String fullPath);

    public boolean exists(String schema, String fullPath);

    public void delete(String schema, String fullPath);

    public FileItem findByPath(String schema, String fullPath);

}
