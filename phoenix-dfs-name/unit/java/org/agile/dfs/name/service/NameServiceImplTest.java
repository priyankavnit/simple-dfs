package org.agile.dfs.name.service;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.util.ServiceFactory;

public class NameServiceImplTest extends TestCase {
    private static SchemaService schemaService = (SchemaService) ServiceFactory.findService(SchemaServiceImpl.class);

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBuild() {
        String ns = "phoenix";
        DfsSchema dfsSchema = new DfsSchema(ns, "http://www.agile.org/dfs");
        schemaService.destory(ns);
        Assert.assertTrue(!schemaService.exists(ns));
        schemaService.build(dfsSchema);
        Assert.assertTrue(schemaService.exists(ns));
    }

    public void testExists() {
        fail("Not yet implemented");
    }

    public void testDestory() {
        fail("Not yet implemented");
    }

}
