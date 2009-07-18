package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.SchemaManager;
import org.agile.dfs.name.manager.TableLocator;
import org.agile.dfs.name.manager.TableManager;
import org.agile.dfs.util.ServiceFactory;

public class SchemaServiceImpl implements SchemaService {
    private SchemaManager schemaManager = ServiceFactory.findService(SchemaManager.class);
    private FileItemManager fileItemManager = ServiceFactory.findService(FileItemManager.class);
    private TableManager tableManager = ServiceFactory.findService(TableManager.class);
    private TableLocator tableLocator = ServiceFactory.findService(TableLocator.class);

    public DfsSchema build(DfsSchema dfsSchema) {
        // save schema entity
        schemaManager.create(dfsSchema);
        // create schema's file table
        tableManager.createFileTable(dfsSchema.getName());
        // create schema's root file
        FileItem root = new FileItem();
        root.setParentId("0000");
        root.setName("ROOT");
        root.setStatus(FileItem.STATUS_NORMAL);
        root.setType(FileItem.TYPE_DIR);
        fileItemManager.create(dfsSchema, root);
        return dfsSchema;
    }

    public boolean exists(String schema) {
        DfsSchema ds = schemaManager.findByName(schema);
        if (ds == null) {
            return false;
        }
        String table = tableLocator.fileTable(schema);
        boolean tflag = tableManager.existsTable(table);
        if (!tflag) {
            return false;
        }
        FileItem root = fileItemManager.findRoot(ds);
        if (root == null) {
            return false;
        }
        return true;
    }

    public void destory(String schema) {
        schemaManager.deleteByName(schema);
        tableManager.dropTable(tableLocator.fileTable(schema));
    }

}
