package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.SchemaManager;
import org.agile.dfs.util.ServiceFactory;

public class FileServiceImpl implements FileService {

    private FileItemManager fileItemManager;
    private SchemaManager schemaManager;

    public FileServiceImpl() {
        this.fileItemManager = (FileItemManager) ServiceFactory.findService(FileItemManager.class);
        this.schemaManager = (SchemaManager) ServiceFactory.findService(SchemaManager.class);
    }

    public boolean createNewFile(String nameSpace, String fullPath) {
        DfsSchema ns = schemaManager.findByName(nameSpace);
        FileItem item = fileItemManager.mkfile(ns, fullPath);
        return item != null ? item.getId() != null ? true : false : false;
    }

    public boolean exists(String nameSpace, String fullPath) {
        DfsSchema ns = schemaManager.findByName(nameSpace);
        FileItem item = fileItemManager.findByPath(ns, fullPath);
        if (item == null) {
            item = fileItemManager.findByPath(ns, fullPath);
        }
        return item != null;
    }

    public boolean mkdir(String nameSpace, String fullPath, boolean parent) {
        // TODO handle same name
        DfsSchema ns = schemaManager.findByName(nameSpace);
        fileItemManager.mkdir(ns, fullPath, parent);
        return true;
    }

    public boolean delete(String nameSpace, String fullPath) {
        FileItem item = fileItemManager.findByPath(nameSpace, fullPath);
        return item == null ? false : fileItemManager.delete(nameSpace, item);
    }

}
