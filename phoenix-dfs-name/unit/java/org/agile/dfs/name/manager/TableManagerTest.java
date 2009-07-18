package org.agile.dfs.name.manager;

import junit.framework.Assert;

import org.agile.dfs.name.jdbc.JdbcTemplate;
import org.agile.dfs.name.manager.TableLocator;
import org.agile.dfs.name.manager.TableManager;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.util.ServiceFactory;

public class TableManagerTest extends BaseNameNodeTestCase {

    private TableManager manager = (TableManager) ServiceFactory.findService(TableManager.class);
    private JdbcTemplate template = (JdbcTemplate) ServiceFactory.findService(JdbcTemplate.class);
    private TableLocator tlocator = (TableLocator) ServiceFactory.findService(TableLocator.class);

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExistsTable() {
        String tn = "T" + idGen.generate();
        manager.dropTable(tn);
        boolean flag = manager.existsTable(tn);
        Assert.assertTrue(!flag);
        template.execute("create table " + tn + "(id int)");
        flag = manager.existsTable(tn);
        Assert.assertTrue(flag);
    }

    public void testDropTable() {
        String tn = "T" + idGen.generate();
        template.execute("create table " + tn + "(id int)");
        boolean flag = manager.existsTable(tn);
        Assert.assertTrue(flag);
        manager.dropTable(tn);
        flag = manager.existsTable(tn);
        Assert.assertTrue(!flag);
    }
 
    public void testCreateFileTable() {
        String ns = "phoenix";
        String tableName = tlocator.fileTable(ns);
        manager.dropTable(tableName);
        boolean flag = manager.existsTable(tableName);
        Assert.assertTrue(!flag);
        manager.createFileTable(ns);
        flag = manager.existsTable(tableName);
        Assert.assertTrue(flag);
    }

}
