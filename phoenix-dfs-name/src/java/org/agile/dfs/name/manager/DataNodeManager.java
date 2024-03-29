package org.agile.dfs.name.manager;

import java.util.ArrayList;
import java.util.List;

import org.agile.dfs.core.entity.DataNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNodeManager {
    private final static Logger logger = LoggerFactory.getLogger(DataNodeManager.class);
    private DataNode node = null;
    private long lastSelectTime = 0;

    private static final List<DataNode> nodes = new ArrayList<DataNode>(500);

    // ip 10.10.10.5 -> first: 10.10.10.*, second: 10.10.*, third: 10.*
    public List<DataNode> findDataNode(String ip, int num) { 
        return null;
    }

    public DataNode findDataNode() {
        // cache selectable node for 1s
        if ((System.currentTimeMillis() - lastSelectTime) < 1000) {
            if (node != null) {
                return node;
            }
        }
        int loadNo = 0, minload = -1, connNo = 0, minConn = -1;
        for (int i = 0, len = nodes.size(); i < len; i++) {
            DataNode node = nodes.get(i);
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
            DataNode minLoadNode = nodes.get(loadNo);
            DataNode minConnNode = nodes.get(connNo);
            if (minLoadNode == minConnNode) {
                // node = minConnNode;
            } else {
                int ll = countPoint(minLoadNode);
                int cl = countPoint(minConnNode);
                if (ll > cl) {
                    // node = minConnNode;
                } else {
                    // node = minLoadNode;
                }
            }
        }
        lastSelectTime = System.currentTimeMillis();
        return null;
    }

    public void add(DataNode node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
            logger.info("Welcome new node " + node);
        }
    }

    private int countPoint(DataNode node) {
        // TODO impl better method
        // max load = 100
        int load = node.getJvmLoad();
        // max load = 100
        int conn = node.getConnNum();
        return load + conn;
    }
}
