package org.agile.dfs.name.manager;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.dao.IBatisTemplate;
import org.agile.dfs.util.ServiceFactory;

public class SchemaManager {
    // private static final Logger logger = LoggerFactory.getLogger(SchemaManager.class);
    private static final IBatisTemplate template = ServiceFactory.findService(IBatisTemplate.class);
    private final UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);

    public DfsSchema create(DfsSchema dfsSchema) {
        dfsSchema.setId(idGen.generate());
        template.insert("dfs.schema.insert", dfsSchema);
        return dfsSchema;
    }

    public void deleteByName(String name) {
        template.delete("dfs.schema.delete.name", name);
    }

    public DfsSchema findByName(final String name) {
        return (DfsSchema) template.findByParameter("dfs.schema.select.name", name);
    }

}
