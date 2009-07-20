package org.agile.dfs.name.service;

import org.agile.dfs.core.entity.DfsSchema;

public interface SchemaService {

    public boolean exists(final String schema);

    public DfsSchema build(final DfsSchema dfsSchema);

    public void destroy(final String schema);
}
