package org.agile.dfs.client.cache;

import java.io.IOException;

import org.agile.dfs.client.DfsFile;
import org.agile.dfs.client.DfsLocator;
import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.data.service.DataService;
import org.agile.dfs.name.service.BlockService;
import org.agile.dfs.rpc.client.AsyncAttachment;
import org.agile.dfs.rpc.client.RpcContext;

public class BlockCache {
    private static final DataService dataService = DfsLocator.async(DataService.class);
    private static final BlockService blockService = DfsLocator.lookup(BlockService.class);
    private DfsFile dfsFile; // current cache's dfs file
    private BlockItem block; // current writeable block
    private String schema;

    public BlockCache(DfsFile file) throws IOException {
        dfsFile = file;
        schema = file.getSchema();
        block = blockService.locate(schema, dfsFile.getId());
    }

    public void write(byte[] b, int off, int len) throws IOException {
        int free = block.getCapacity() - block.getSize();
        if (free > len) {
            dataService.write(block.getId(), len);
            AsyncAttachment.getAttachment().write(b, off, len);
        } else {
            // write part byte into cache's remain space
            dataService.write(block.getId(), free);
            AsyncAttachment.getAttachment().write(b, off, len);
            blockService.commit(schema, dfsFile.getId(), block.getId());
            block = blockService.locate(schema, dfsFile.getId());
            this.write(b, off + free, len - free);
        }
    }

    public void close() throws IOException {
        // TODO use context, insted endpoint helper
        RpcContext.current().close();
    }

    public void flush() throws IOException {
        RpcContext.current().flush();
    }

    protected void finalize() throws Throwable {
        this.close();
    }
}
