package org.agile.dfs.name.manager;

import junit.framework.Assert;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.util.ServiceFactory;
import org.agile.dfs.util.StringUtil;

public class SchemaMamagerTest extends BaseNameNodeTestCase {

    private SchemaManager manager;

    protected void setUp() throws Exception {
        super.setUp();
        manager = ServiceFactory.findService(SchemaManager.class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateDeleteFindByName() {
        String name = "testing";
        manager.deleteByName(name);

        DfsSchema ns = new DfsSchema();
        ns.setName(name);
        ns.setUrl("http://www.testing.com/dfs");
        manager.create(ns);
        Assert.assertNotNull(ns.getId());

        DfsSchema ns2 = manager.findByName(name);
        Assert.assertNotNull(ns2);
        Assert.assertEquals(name, ns2.getName());

        manager.deleteByName(name);
        DfsSchema ns3 = manager.findByName(name);
        Assert.assertNull(ns3);
    }

    public void testBuild() {
        DfsSchema ns = new DfsSchema();
        ns.setName("testing2");
        ns.setUrl("http://www.testing.com/dfs2");
        // manager.build(ns);
        Assert.assertNotNull(ns.getId());
        Assert.assertTrue(!"-1".equals(ns.getId()));
    }

    public void testQueryAll() {

    }

    public static void main(String[] args) {
        String s = "some name {name}, some age {age}, and more";
        s = StringUtil.simpleReplace(s, "name", "chenbin");
        s = StringUtil.simpleReplace(s, "age", "32");
        System.out.println(s);

    }
}
