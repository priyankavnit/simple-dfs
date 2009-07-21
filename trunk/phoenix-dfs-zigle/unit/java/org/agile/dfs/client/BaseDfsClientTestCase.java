package org.agile.dfs.client;

import org.agile.dfs.client.service.DfsInitializer;

import junit.framework.TestCase;

public class BaseDfsClientTestCase extends TestCase {

    static {
        DfsInitializer.init();
        // multicast time interval
        try {
            Thread.sleep(2 * 1000);
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
