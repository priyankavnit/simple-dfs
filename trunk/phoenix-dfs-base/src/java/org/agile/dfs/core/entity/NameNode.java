package org.agile.dfs.core.entity;

public class NameNode extends NodeItem {

    // only for serializble
    public NameNode() {

    }

    public NameNode(String ip, int port) {
        super(NODE_TYPE_NAME, ip, port);
    }

}
