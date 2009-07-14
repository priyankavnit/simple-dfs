package org.agile.dfs.client.service;

import java.util.ArrayList;
import java.util.List;

import org.agile.dfs.core.entity.NodeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameNodeService {
    private final static Logger logger = LoggerFactory.getLogger(NameNodeService.class);
    private static NameNodeService _instance = new NameNodeService();

    private NameNodeService() {

    }

    public static NameNodeService instance() {
        return _instance;
    }

    private List<NodeItem> nodes = new ArrayList<NodeItem>(5);

    public NodeItem findNameNode() {
        // TODO impl select rule, cache node for a little time
        int no = 0, minload = 0;
        for (int i = 0, len = nodes.size(); i < len; i++) {
            NodeItem node = (NodeItem) nodes.get(i);
            if (minload > node.getLoad()) {
                no = i;
                minload = node.getLoad();
            }
        }
        if (nodes.size() == 0) {
            throw new RuntimeException("Can't discover any name node!");
        } else {
            return (NodeItem) nodes.get(no);
        }
    }

    public void add(NodeItem node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
            logger.info("Welcome new node " + node);

        }
    }
}
