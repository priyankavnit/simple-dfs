package org.agile.dfs.name.manager;

import org.agile.dfs.core.entity.DfsSchema;

public class TableLocator {

    private static final String FILE_PREFIX = "TBL_DFS_FILEITEM_";

    public String fileTable(DfsSchema ns) {
        return FILE_PREFIX + ns.getName().toUpperCase();
    }

    public String fileTable(String schema) {
        return FILE_PREFIX + schema.toUpperCase();
    }
}
