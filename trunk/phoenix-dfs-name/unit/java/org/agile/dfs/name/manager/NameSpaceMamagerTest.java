package org.agile.dfs.name.manager;

import java.util.List;

import junit.framework.Assert;

import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.util.StringUtil;

public class NameSpaceMamagerTest extends BaseNameNodeTestCase {

    private NameSpaceManager manager;

    protected void setUp() throws Exception {
        super.setUp();
        manager = (NameSpaceManager) ServiceFactory.findService(NameSpaceManager.class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreate() {
        NameSpace ns = new NameSpace();
        ns.setName("testing");
        ns.setUrl("http://www.testing.com/dfs");
        manager.save(ns);
        Assert.assertNotNull(ns.getId());
        Assert.assertTrue(!"-1".equals(ns.getId()));
    }

    public void testBuild() {
        NameSpace ns = new NameSpace();
        ns.setName("testing2");
        ns.setUrl("http://www.testing.com/dfs2");
        manager.build(ns);
        Assert.assertNotNull(ns.getId());
        Assert.assertTrue(!"-1".equals(ns.getId()));
    }

    public void testQueryAll() {
        List list = manager.queryAllForMap();
        for (int i = 0; i < 10 && i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main(String[] args) {
        String s = "some name {name}, some age {age}, and more";
        s = StringUtil.simpleReplace(s, "name", "chenbin");
        s = StringUtil.simpleReplace(s, "age", "32");
        System.out.println(s);

    }
}
