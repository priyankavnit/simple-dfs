package org.agile.dfs.name.service;

import java.util.List;

import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.name.manager.BlockItemManager;
import org.agile.dfs.util.ServiceFactory;

public class BlockServiceImpl implements BlockService {

    private BlockItemManager blockManager;

    public BlockServiceImpl() {
        blockManager = ServiceFactory.findService(BlockItemManager.class);
    }

    public void commit(String fileId, String blockId) {
        // TODO Auto-generated method stub
        // set block status to normal
    }

    public BlockItem locate(String fileId) {
        List<BlockItem> items;
        // get file's last block ( blockNo is max )
        // if block is null, create first block
        // else return the block
        // return block status is INIT
        return null;
    }

}
