package org.agile.dfs.name.manager;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.BaseNameNodeTestCase;
import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.NameSpaceManager;

public class FileItemManagerTest extends BaseNameNodeTestCase {

    FileItemManager fileMgr;
    NameSpaceManager nsMgr;
    UuidHexGenerator idGen;

    protected void setUp() throws Exception {
        super.setUp();
        fileMgr = (FileItemManager) ServiceFactory.findService(FileItemManager.class);
        nsMgr = (NameSpaceManager) ServiceFactory.findService(NameSpaceManager.class);
        idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFindById() {
        fail("Not yet implemented");
    }

    public void testFindByPathNameSpaceString() {
        fail("Not yet implemented");
    }

    public void testFindByPathNameSpaceStringBoolean() {
        fail("Not yet implemented");
    }

    public void testCreate() {
        fail("Not yet implemented");
    }

    public void testCreateFile() {
        NameSpace ns = nsMgr.findByName("phoenix");
        if (ns == null) {
            ns = nsMgr.build(new NameSpace("phoenix", "http://www.g.cn/phoenix/photo"));
        }
        String rand = idGen.generate().toString();
        fileMgr.mkdir(ns, "/home/agile/" + rand, true);
        fileMgr.mkfile(ns, "/home/agile/" + rand + "/some.jpg");
    }

    public void testBenchCreateFile() {
        NameSpace ns = nsMgr.findByName("phoenix");
        if (ns == null) {
            ns = nsMgr.build(new NameSpace("phoenix", "http://www.g.cn/phoenix/photo"));
        }
        String rand = idGen.generate().toString();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String dir = "/home/" + rand + "/photo/Dir" + i;
            fileMgr.mkdir(ns, dir, true);
            // System.out.println("---------------------------------");
            for (int j = 0; j < 100; j++) {
                String file = dir + "/onetwothreefive" + j + ".jpg";
                fileMgr.mkfile(ns, file);
                // System.out.println("..................................");
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time:" + (t2 - t1));
    }

    public void testMulThreadBenchCreateFile() {
        Thread[] ths = new Thread[100];
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread() {
                public void run() {
                    testBenchCreateFile();
                }
            };
            ths[i].start();
        }
        for (int i = 0; i < ths.length; i++) {
            try {
                ths[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time:" + (t2 - t1));
    }

    public void testCreateDir() {
        fail("Not yet implemented");
    }

}
