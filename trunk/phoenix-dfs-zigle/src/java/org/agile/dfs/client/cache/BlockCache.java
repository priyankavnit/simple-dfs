package org.agile.dfs.client.cache;

import java.io.IOException;

import org.agile.dfs.client.DfsFile;
import org.agile.dfs.client.ServiceLocator;
import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.data.service.BlockService;
import org.agile.dfs.name.service.SpaceService;
import org.agile.dfs.rpc.piple.RpcAttachment;
import org.agile.dfs.rpc.util.AttachmentHelper;

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
        RpcAttachment attachment = AttachmentHelper.getAttachment();
        int free = block.getCapacity() - block.getSize();
        if (free > len) {
            blockService.write(block.getId(), len);
            attachment.write(b, off, len);
        } else {
            // write part byte into cache's remain space
            blockService.write(block.getId(), free);
            attachment.write(b, off, len);
            spaceService.commit(dfsFile.getId(), block.getId());
            spaceService.locate(dfsFile.getId());
            this.write(b, off + free, len - free);
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
