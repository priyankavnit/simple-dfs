package org.agile.dfs.name.service;

import junit.framework.Assert;

import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.util.ServiceFactory;

public class SchemaServiceImplTest extends BaseNameNodeTestCase {
    private static SchemaService schemaService = ServiceFactory.findService(SchemaServiceImpl.class);

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExistsBuildDestory() {
        String ns = "phoenix";
        DfsSchema dfsSchema = new DfsSchema(ns, "http://www.agile.org/dfs");
        schemaService.destory(ns);
        Assert.assertTrue(!schemaService.exists(ns));
        schemaService.build(dfsSchema);
        Assert.assertTrue(schemaService.exists(ns));
        schemaService.destory(ns);
        Assert.assertTrue(!schemaService.exists(ns));
    }

}
