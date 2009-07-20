package org.agile.dfs.name.manager;

import org.agile.dfs.core.entity.DfsSchema;

public class TableLocator {

    private static final String FILE_PREFIX = "TBL_DFS_FILEITEM_";
    private static final String BLOCK_PREFIX = "TBL_DFS_BLOCK_";

    public String fileTable(DfsSchema ns) {
        return FILE_PREFIX + ns.getName().toUpperCase();
    }

    public String fileTable(String schema) {
        return FILE_PREFIX + schema.toUpperCase();
    }

    public String blockTable(DfsSchema ns) {
        return BLOCK_PREFIX + ns.getName().toUpperCase();
    }

    public String blockTable(String schema) {
        return BLOCK_PREFIX + schema.toUpperCase();
    }
}
