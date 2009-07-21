package org.agile.dfs.client;

import java.io.IOException;

import org.agile.dfs.client.service.DfsServiceLocator;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.name.service.SchemaService;

public class DfsOutputStreamTest extends BaseDfsClientTestCase {
    private static final SchemaService schemaService = DfsServiceLocator.lookup(SchemaService.class);

    private String schema = "phoenix";

    private DfsOutputStream output;

    protected void setUp() throws Exception {
        super.setUp();
        if (!schemaService.exists(schema)) {
            DfsSchema ns = new DfsSchema();
            ns.setName(schema);
            ns.setUrl("http://www.agile.org/dfs");
            schemaService.build(ns);
        }
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
        byte[] b = " hello one! hello two! hello 中国！\n".getBytes();
        // output.write(b, 0, b.length);
        for (int i = 0; i < 1 * 1024 * 4; i++) {
            output.write(Integer.toString(i).getBytes());
            output.write(b);
        }
        output.close();
    }

}
