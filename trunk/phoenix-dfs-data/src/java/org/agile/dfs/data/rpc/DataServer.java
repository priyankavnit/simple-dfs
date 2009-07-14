package org.agile.dfs.data.rpc;

import org.agile.dfs.core.common.Configuration;
import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.rpc.server.RpcHandler;
import org.agile.dfs.rpc.server.RpcServer;

public class DataServer extends RpcServer {

    public DataServer() {
        ip = Configuration.getProperty("data.server.ip", "0.0.0.0");
        port = Configuration.getPropertyForInt("data.server.port", 45100);
    }

    @Override
    public String getNodeType() {
        return NodeItem.NODE_TYPE_DATA;
    }

    @Override
    public RpcHandler getRpcHandler() {
        return new DataHandler();
    }

    public static void main(String[] args) {
        DataServer ns = new DataServer();
        ns.startup();
    }
}
