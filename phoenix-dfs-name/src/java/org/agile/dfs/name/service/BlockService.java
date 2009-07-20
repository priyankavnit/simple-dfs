package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.BlockItem;

public interface BlockService {

    public BlockItem locate(String schema, String fileId);

    public void commit(String schema, String fileId, String blockId);

}
