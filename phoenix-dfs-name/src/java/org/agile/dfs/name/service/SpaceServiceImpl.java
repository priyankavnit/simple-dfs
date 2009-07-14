package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.name.manager.BlockItemManager;

public class SpaceServiceImpl implements SpaceService {

    private BlockItemManager blockManager;

    public void setBlockManager(BlockItemManager blockManager) {
        this.blockManager = blockManager;
    }

    public void commit(String fileId, String blockId) {
        // TODO Auto-generated method stub

    }

    public BlockItem locate(String fileId) {
        // TODO Auto-generated method stub
        return null;
    }

}
