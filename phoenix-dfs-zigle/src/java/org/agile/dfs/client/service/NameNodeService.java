package org.agile.dfs.client.service;

import java.util.ArrayList;
import java.util.List;

import org.agile.dfs.core.entity.NameNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameNodeService {
    private final static Logger logger = LoggerFactory.getLogger(NameNodeService.class);
    private static NameNodeService _instance = new NameNodeService();
    private long lastSelectTime = 0;
    private NameNode node = null;

    private NameNodeService() {

    }

    public static NameNodeService instance() {
        return _instance;
    }

    private List<NameNode> nodes = new ArrayList<NameNode>(5);

    public NameNode findNameNode() {
        // cache name node for 2s
        if ((System.currentTimeMillis() - lastSelectTime) < 1000) {
            if (node != null) {
                return node;
            }
        }
        int loadNo = 0, minload = -1, connNo = 0, minConn = -1;
        for (int i = 0, len = nodes.size(); i < len; i++) {
            NameNode node = nodes.get(i);
            if (minload > node.getJvmLoad()) {
                loadNo = i;
                minload = node.getJvmLoad();
            }
            if (minConn > node.getConnNum()) {
                connNo = i;
                minConn = node.getConnNum();
            }
        }

        if (nodes.size() == 0) {
            throw new RuntimeException("Can't discover any name node!");
        } else {
            NameNode minLoadNode = nodes.get(loadNo);
            NameNode minConnNode = nodes.get(connNo);
            if (minLoadNode == minConnNode) {
                node = minConnNode;
            } else {
                int ll = countMixedLoad(minLoadNode);
                int cl = countMixedLoad(minConnNode);
                if (ll > cl) {
                    node = minConnNode;
                } else {
                    node = minLoadNode;
                }
            }
        }
        lastSelectTime = System.currentTimeMillis();
        return node;
    }

    public void add(NameNode node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
            logger.info("Welcome new node " + node);
        }
    }

    private int countMixedLoad(NameNode node) {
        // TODO impl better method
        // max load = 100
        int load = node.getJvmLoad();
        // max load = 100
        int conn = node.getConnNum();

        return load + conn;
    }
}
