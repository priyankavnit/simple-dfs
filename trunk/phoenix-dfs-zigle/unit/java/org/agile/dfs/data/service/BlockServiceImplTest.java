package org.agile.dfs.data.service;

import java.io.IOException;

import org.agile.dfs.client.BaseDfsClientTestCase;
import org.agile.dfs.rpc.client.AsyncProxyFactory;
import org.agile.dfs.rpc.piple.RpcAttachment;
import org.agile.dfs.rpc.util.AttachmentHelper;

public class BlockServiceImplTest extends BaseDfsClientTestCase {

    private AsyncProxyFactory factory = new AsyncProxyFactory();

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWrite() throws IOException {
        byte[] b = "some body， 中国!".getBytes();
        BlockService blockService = factory.findService(BlockService.class, "tcp://localhost:45200");
        blockService.write("sdfd", b.length);
        RpcAttachment attachment = AttachmentHelper.getAttachment();
        attachment.write(b);
        attachment.flush();
    }

}
