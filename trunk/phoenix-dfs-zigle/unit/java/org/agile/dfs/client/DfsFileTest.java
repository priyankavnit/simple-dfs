package org.agile.dfs.client;

import java.io.IOException;

public class DfsFileTest extends BaseDfsClientTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateNewFile() throws IOException {
        DfsFile file = new DfsFile("phoenix", "/home/dfs/one.jpg");
        file.createNewFile();
    }

    public void testExists() throws IOException {
        DfsFile file = new DfsFile("http://www.phoenix.org/dfs", "/home/dfs/one.jpg");
        file.exists();
    }

    public void testMkdir() {
        fail("Not yet implemented");
    }

    public void testMkdirs() {
        fail("Not yet implemented");
    }

}
