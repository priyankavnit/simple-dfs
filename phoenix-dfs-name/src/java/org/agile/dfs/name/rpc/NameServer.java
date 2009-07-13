package org.agile.dfs.name.rpc;

import org.agile.dfs.core.common.Configuration;
import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.rpc.server.RpcHandler;
import org.agile.dfs.rpc.server.RpcServer;

public class NameServer extends RpcServer {

    public NameServer() {
        ip = Configuration.getProperty("name.server.ip", "0.0.0.0");
        port = Configuration.getPropertyForInt("name.server.port", 45100);
    }

    @Override
    public String getNodeType() {
        return NodeItem.NODE_TYPE_NAME;
    }

    @Override
    public RpcHandler getRpcHandler() {
        return new NameHandler();
    }

    public static void main(String[] args) {
        NameServer ns = new NameServer();
        ns.startup();
    }
}
