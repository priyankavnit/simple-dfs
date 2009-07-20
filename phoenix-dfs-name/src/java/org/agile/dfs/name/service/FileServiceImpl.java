package org.agile.dfs.name.service;

import java.util.List;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.name.exception.NameNodeException;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.SchemaManager;
import org.agile.dfs.util.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private FileItemManager fileItemManager;
    private SchemaManager schemaManager;

    public FileServiceImpl() {
        this.fileItemManager = ServiceFactory.findService(FileItemManager.class);
        this.schemaManager = ServiceFactory.findService(SchemaManager.class);
    }

    public String createNewFile(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        FileItem item = fileItemManager.mkfile(ds, fullPath, false);
        return item != null ? item.getId() != null ? item.getId() : null : null;
    }

    public String createNewFiles(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        FileItem item = fileItemManager.mkfile(ds, fullPath, true);
        return item != null ? item.getId() != null ? item.getId() : null : null;
    }

    public boolean exists(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        FileItem item = fileItemManager.findByPath(ds, fullPath);
        return item != null;
    }

    public boolean mkdir(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        if (!exists(schema, fullPath)) {
            try {
                FileItem item = fileItemManager.mkdir(ds, fullPath, false);
                return item != null ? item.getId() != null : false;
            } catch (Exception e) {
                logger.error("Fail to mkdir " + schema + ":" + fullPath, e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean mkdirs(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        if (!exists(schema, fullPath)) {
            try {
                FileItem item = fileItemManager.mkdir(ds, fullPath, true);
                return item != null ? item.getId() != null : false;
            } catch (Exception e) {
                logger.error("Fail to mkdir " + schema + ":" + fullPath, e);
                return false;
            }
        } else {
            return false;
        }
    }

    public void delete(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        FileItem item = fileItemManager.findByPath(ds, fullPath);
        if (item != null) {
            deleteById(ds, item.getId());
        }
    }

    private void deleteById(DfsSchema schema, String fileId) {
        List<FileItem> items = fileItemManager.findByParentId(schema, fileId);
        if (items != null && items.size() > 0) {
            for (int i = 0, l = items.size(); i < l; i++) {
                FileItem it = items.get(i);
                if (it.isDirectory()) {
                    this.deleteById(schema, it.getId());
                }
            }
        }
        fileItemManager.deleteByParentId(schema, fileId);
        fileItemManager.deleteById(schema, fileId);
    }

    public FileItem findByPath(String schema, String fullPath) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            throw new NameNodeException("Schema " + schema + " not exist! ");
        }
        FileItem item = fileItemManager.findByPath(ds, fullPath);
        return item;
    }
}
