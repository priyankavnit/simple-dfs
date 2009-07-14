package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.BlockItem;

public interface SpaceService {

    public BlockItem locate(String fileId);

    public void commit(String fileId, String blockId);

}
