package org.agile.dfs.client;

import java.io.IOException;

import junit.framework.Assert;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.service.SchemaService;

public class DfsFileTest extends BaseDfsClientTestCase {

    private static final SchemaService schemaService = ServiceLocator.lookup(SchemaService.class);

    private String schema = "phoenix";

    protected void setUp() throws Exception {
        super.setUp();
        if (!schemaService.exists(schema)) {
            DfsSchema ns = new DfsSchema();
            ns.setName(schema);
            ns.setUrl("http://www.agile.org/dfs");
            schemaService.build(ns);
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateNewFile() throws IOException {
        DfsFile file = new DfsFile(schema, "/home/dfs/one.jpg");
        file.delete();
        DfsFile file2 = new DfsFile(schema, "/home/dfs/one.jpg");
        file2.getParentFile().mkdirs();
        file2.createNewFile();
        Assert.assertTrue(file2.exists());
    }

    public void testExists() throws IOException {
        DfsFile file = new DfsFile(schema, "/home/dfs/one.jpg");
        file.delete();
        Assert.assertTrue(!file.exists());
        DfsFile file2 = new DfsFile(schema, "/home/dfs/one.jpg");
        file2.getParentFile().mkdirs();
        file2.createNewFile();
        Assert.assertTrue(file2.exists());
    }

    public void testMkdir() {
        DfsFile file = new DfsFile(schema, "/home/dfs/dir");
        file.delete();
        Assert.assertTrue(!file.exists());
        DfsFile file2 = (DfsFile) file.getParentFile();
        file2.delete();
        Assert.assertTrue(!file.mkdir());
        Assert.assertTrue(file2.mkdir());
        Assert.assertTrue(file.mkdir());
    }

    public void testMkdirs() {
        DfsFile file = new DfsFile(schema, "/home/dfs/dir");
        file.delete();
        Assert.assertTrue(!file.exists());
        DfsFile file2 = (DfsFile) file.getParentFile();
        Assert.assertTrue(file.mkdirs());
        Assert.assertTrue(file2.exists());
    }
}
