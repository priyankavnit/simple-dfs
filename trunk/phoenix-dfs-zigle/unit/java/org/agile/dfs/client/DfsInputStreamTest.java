package org.agile.dfs.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

public class DfsInputStreamTest extends TestCase {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DfsInitializer.init();
        String name = "/agile/" + new java.util.Random().nextDouble() + "/as.jpg";
        OutputStream dos = new DfsOutputStream("ns", name);
        byte[] b = "hello one! hello two! hello 中国！".getBytes();
        for (int i = 0; i < 102400; i++) {
            dos.write(b);
        }
        dos.close();
        System.out.println(dos);

        System.out.println("READ DATA... ");
        // InputStream ios = new DfsInputStream(name);
        // byte[] buf = new byte[1024];
        // int len = 0;
        // while ((len = ios.read(buf)) >= 0) {
        // System.out.println(new String(buf, 0, len));
        // }
    }
}
