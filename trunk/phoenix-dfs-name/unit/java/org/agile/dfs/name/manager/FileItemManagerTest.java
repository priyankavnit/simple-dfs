package org.agile.dfs.name.manager;

import junit.framework.Assert;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.name.service.SchemaService;
import org.agile.dfs.name.service.SchemaServiceImpl;
import org.agile.dfs.util.ServiceFactory;

public class FileItemManagerTest extends BaseNameNodeTestCase {

    FileItemManager fileMgr;
    UuidHexGenerator idGen;
    TableLocator tblLoc;

    SchemaService schemaService;
    String schema = "phoenix";

    protected void setUp() throws Exception {
        super.setUp();
        fileMgr = ServiceFactory.findService(FileItemManager.class);
        schemaService = ServiceFactory.findService(SchemaServiceImpl.class);
        idGen = ServiceFactory.findService(UuidHexGenerator.class);
        tblLoc = ServiceFactory.findService(TableLocator.class);
        if (!schemaService.exists(schema)) {
            schemaService.build(new DfsSchema(schema, "http://www.agile.org/dfs"));
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        schemaService.destory(schema);
    }

    public void testFindById() {
        fail("Not yet implemented");
    }

    public void testFindByPathSchemaString() {
        fail("Not yet implemented");
    }

    public void testFindByPathSchemaStringBoolean() {
        fail("Not yet implemented");
    }

    public void testCreate() {
        String table = tblLoc.fileTable(schema);
        FileItem item = new FileItem();
        item.setParentId("pid");
        item.setName("name" + new  java.util.Random().nextLong());
        item.setType(FileItem.TYPE_FILE);
        item.setStatus(FileItem.STATUS_NORMAL);
        fileMgr.create(table, item);
        Assert.assertNotNull(item.getId());
    }

    public void testDelete() {
        String table = tblLoc.fileTable(schema);
        FileItem item = new FileItem();
        item.setParentId("pid");
        item.setName("name" + new  java.util.Random().nextLong());
        item.setType(FileItem.TYPE_FILE);
        item.setStatus(FileItem.STATUS_NORMAL);
        fileMgr.create(table, item);
        Assert.assertNotNull(item.getId());

        fileMgr.deleteById(table, item.getId());
    }
}
