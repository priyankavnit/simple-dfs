package org.agile.dfs.client;

import junit.framework.TestCase;

public class BaseDfsClientTestCase extends TestCase {

    static {
        DfsClientInitializer.init();
        // multicast time interval
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void setUp() throws Exception {
        super.setUp();

    }

    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

}
