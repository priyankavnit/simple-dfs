package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.SchemaManager;
import org.agile.dfs.name.manager.TableManager;
import org.agile.dfs.util.ServiceFactory;

public class SchemaServiceImpl implements SchemaService {
    private SchemaManager schemaManager = ServiceFactory.findService(SchemaManager.class);
    private FileItemManager fileItemManager = ServiceFactory.findService(FileItemManager.class);
    private TableManager tableManager = ServiceFactory.findService(TableManager.class);

    public DfsSchema build(DfsSchema dfsSchema) {
        // save schema
        schemaManager.create(dfsSchema);
        // create name space's file table
        tableManager.createFileTable(dfsSchema.getName());
        // create name space's root file
        FileItem root = new FileItem();
        root.setParentId("0000");
        root.setName("ROOT");
        root.setStatus(FileItem.STATUS_NORMAL);
        root.setType(FileItem.TYPE_DIR);
        // fileItemManager.save(ns, root);
        return dfsSchema;
    }

    public boolean exists(String schema) {
        DfsSchema ns = schemaManager.findByName(schema);
        return ns != null;
    }

    public void destory(String schema) {
        // DfsSchema ns = schemaManager.findByName(schema);
        // if (ns != null) {
        // schemaManager.destory(ns);
        // }
    }

}
