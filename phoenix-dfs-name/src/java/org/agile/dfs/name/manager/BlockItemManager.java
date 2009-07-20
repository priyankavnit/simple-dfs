package org.agile.dfs.name.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.BlockItem;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.dao.IBatisTemplate;
import org.agile.dfs.util.ObjectUtil;
import org.agile.dfs.util.ServiceFactory;

public class BlockItemManager { 
    private static final IBatisTemplate template = ServiceFactory.findService(IBatisTemplate.class);
    private static final UuidHexGenerator generator = ServiceFactory.findService(UuidHexGenerator.class);
    private static final TableLocator locator = ServiceFactory.findService(TableLocator.class);

    public BlockItem create(DfsSchema schema, BlockItem item) {
        item.setStatus(BlockItem.STATUS_READY);
        item.setId(generator.generate());
        Map<String, Object> map = ObjectUtil.toMap(item);
        String table = locator.blockTable(schema);
        map.put("table", table);
        template.insert("dfs.block.insert", map);
        return item;
    }
    
    public void update(DfsSchema schema, BlockItem block) {
        Map<String, Object> map = ObjectUtil.toMap(block);
        String table = locator.blockTable(schema);
        map.put("table", table);
        template.update("dfs.block.update", map); 
        
    }
    @SuppressWarnings("unchecked")
    public List<BlockItem> findByFileId(DfsSchema schema, String fileId) {
        String table = locator.blockTable(schema);
        Map<String, String> map = new HashMap<String, String>();
        map.put("table", table);
        map.put("fileId", fileId);
        return template.findListByParameter("dfs.block.select.fileId", map);
    }
 
    public  BlockItem findById(DfsSchema schema, String id) {
        String table = locator.blockTable(schema);
        Map<String, String> map = new HashMap<String, String>();
        map.put("table", table);
        map.put("id", id);
        return (BlockItem) template.findByParameter("dfs.block.select.id", map);
    }
    
}
