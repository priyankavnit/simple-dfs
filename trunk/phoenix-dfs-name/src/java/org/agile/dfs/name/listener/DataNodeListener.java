package org.agile.dfs.name.listener;

import org.agile.dfs.config.Configuration;
import org.agile.dfs.core.entity.DataNode;
import org.agile.dfs.name.manager.DataNodeManager;
import org.agile.dfs.rpc.serialize.RpcDeSerializer;
import org.agile.dfs.rpc.serialize.SerializerFactory;
import org.agile.dfs.server.MulticastHandler;
import org.agile.dfs.server.MulticastServer;
import org.agile.dfs.util.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNodeListener extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(DataNodeListener.class);
    private static final RpcDeSerializer deserializer = SerializerFactory.instance().getRpcDeSerializer();
    private static final DataNodeManager dataNodeManager = ServiceFactory.findService(DataNodeManager.class);
    private static final DataNodeListener _instance = new DataNodeListener();

    private DataNodeListener() {

    }

    public static DataNodeListener instance() {
        return _instance;
    }

    public void run() {
        String ip = Configuration.getProperty("name.multicast.client.ip", null);
        int port = Configuration.getPropertyForInt("name.multicast.client.port", 0);
        logger.info("Startup multicast service {}:{}.", ip, String.valueOf(port));
        MulticastServer ms = new MulticastServer(ip, port);
        ms.addHandler(new MulticastHandler() {
            public void handle(String msg) {
                DataNode item = (DataNode) deserializer.read(msg);
                dataNodeManager.add(item);
            }
        });
        ms.start();
    }
}
