package org.agile.dfs.client;

import java.io.IOException;

public class DfsOutputStreamTest extends BaseDfsClientTestCase {

    private DfsOutputStream output;

    protected void setUp() throws Exception {
        super.setUp();
        // DfsSchema ns = new DfsSchema("phoenix", "/home/test/test.jpg");
        DfsFile file = new DfsFile("phoenix", "/home/test/test.jpg");
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            file.createNewFile();
        }
        output = new DfsOutputStream(file);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if (output != null) {
            output.close();
        }
    }

    public void testWriteByteArray() throws IOException, InterruptedException {
        // DfsClientInitializer.init();
        // OutputStream dos = new DfsOutputStreamImpl("ns", "/agile/" + new
        // java.util.Random().nextDouble() + "/as.jpg");
        byte[] b = "hello one! hello two! hello 中国！".getBytes();
        output.write(b, 0, b.length);
        // for (int i = 0; i < 1024 * 1024 * 8; i++) {
        // output.write(b);
        // }
        // output.close();
        output.close(); 
        // System.out.println(dos);
    }

}
