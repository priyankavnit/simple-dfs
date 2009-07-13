package org.agile.dfs.name.manager;

import org.agile.dfs.core.entity.NameSpace;

public class TableLocator {

    private static final String DIR_PREFIX = "TBL_DFS_DIRITEM_";
    private static final String FILE_PREFIX = "TBL_DFS_FILEITEM_";

    public String dirTableName(NameSpace ns) {
        return DIR_PREFIX + ns.getName().toUpperCase();
    }

    public String dirTableName(String namespace) {
        return DIR_PREFIX + namespace.toUpperCase();
    }

    public String fileTableName(NameSpace ns) {
        return FILE_PREFIX + ns.getName().toUpperCase();
    }

    public String fileTableName(String namespace) {
        return FILE_PREFIX + namespace.toUpperCase();
    }
}
