package org.agile.dfs.name.service;

import junit.framework.Assert;

import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.name.manager.NameSpaceManager;

public class FileServiceImplTest extends BaseNameNodeTestCase {

    private static FileService fileService = (FileService) ServiceFactory.findService(FileServiceImpl.class);
    private static NameSpaceManager nameManager = (NameSpaceManager) ServiceFactory.findService(NameSpaceManager.class);

    private static String ns = "phoenix";
    static {
        nameManager.build(new NameSpace(ns, "http://www.zigle.com/"));
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateNewFile() {
        String dir = "/home/testCreateNewFile";
        fileService.mkdir(ns, dir, true);
        fileService.createNewFile(ns, dir + "/" + "soms.dfd");
    }

    public void testExists() {
        String dir = "/home/testExists";
        fileService.mkdir(ns, dir, true);
        fileService.createNewFile(ns, dir + "/" + "soms.dfd");
        boolean flag = fileService.exists(ns, dir + "/" + "soms.dfd");
        Assert.assertTrue(flag);
        boolean flag2 = fileService.exists(ns, dir + "/" + "soms.dfd.2");
        Assert.assertTrue(!flag2);
    }

    public void testMkdir() {
        String dir = "/home/testMkdir";
        fileService.mkdir(ns, dir, true);
        boolean flag = fileService.exists(ns, dir);
        Assert.assertTrue(flag);
    }

}
