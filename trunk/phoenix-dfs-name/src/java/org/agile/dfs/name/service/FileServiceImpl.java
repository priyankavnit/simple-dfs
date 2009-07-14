package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.NameSpaceManager;

public class FileServiceImpl implements FileService {

    private FileItemManager fileItemManager;
    private NameSpaceManager nameSpaceManager;

    public FileServiceImpl() {
        this.fileItemManager = (FileItemManager) ServiceFactory.findService(FileItemManager.class);
        this.nameSpaceManager = (NameSpaceManager) ServiceFactory.findService(NameSpaceManager.class);
    }

    public boolean createNewFile(String nameSpace, String fullPath) {
        NameSpace ns = nameSpaceManager.findByName(nameSpace);
        FileItem item = fileItemManager.mkfile(ns, fullPath);
        return item != null ? item.getId() != null ? true : false : false;
    }

    public boolean exists(String nameSpace, String fullPath) {
        NameSpace ns = nameSpaceManager.findByName(nameSpace);
        FileItem item = fileItemManager.findByPath(ns, fullPath, false);
        if (item == null) {
            item = fileItemManager.findByPath(ns, fullPath, true);
        }
        return item != null;
    }

    public boolean mkdir(String nameSpace, String fullPath, boolean parent) {
        // TODO handle same name
        NameSpace ns = nameSpaceManager.findByName(nameSpace);
        fileItemManager.mkdir(ns, fullPath, parent);
        return true;
    }

}
