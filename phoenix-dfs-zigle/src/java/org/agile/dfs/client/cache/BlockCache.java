package org.agile.dfs.client.cache;

import java.io.IOException;

import org.agile.dfs.client.DfsFile;
import org.agile.dfs.client.ServiceLocator;
import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.data.service.BlockService;
import org.agile.dfs.name.service.SpaceService;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.util.EndpointHelper;

public class BlockCache {
    private static final SpaceService spaceService = ServiceLocator.lookup(SpaceService.class);
    private static final BlockService blockService = ServiceLocator.async(BlockService.class);
    private DfsFile dfsFile; // current cache's dfs file
    private BlockItem block; // current writeable block

    public BlockCache(DfsFile file) throws IOException {
        dfsFile = file;
        block = spaceService.locate(dfsFile.getId());
        block = new BlockItem();
        block.setCapacity(100000);
        block.setSize(200);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        Endpointable endpoint = EndpointHelper.current();
        int free = block.getCapacity() - block.getSize();
        if (free > len) {
            blockService.write(block.getId(), len);
            // streamCaller.stream = buf[];
            
            endpoint.write(b, off, len);
            endpoint.flush();
        } else {
            // write part byte into cache's remain space
            blockService.write(block.getId(), free);
            endpoint.write(b, off, free); 
            spaceService.commit(dfsFile.getId(), block.getId());
            spaceService.locate(dfsFile.getId());
            this.write(b, off + free, len - free);
            endpoint.flush();
        }
    }

    public void close() throws IOException {

    }

    public void flush() throws IOException {

    }

    protected void finalize() throws Throwable {
        this.close();
    }
}
