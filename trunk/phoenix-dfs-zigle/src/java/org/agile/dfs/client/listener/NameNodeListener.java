package org.agile.dfs.client.listener;

import org.agile.dfs.client.config.Configuration;
import org.agile.dfs.client.service.DfsInitializer;
import org.agile.dfs.client.service.NameNodeService;
import org.agile.dfs.core.entity.NameNode;
import org.agile.dfs.rpc.serialize.RpcDeSerializer;
import org.agile.dfs.rpc.serialize.SerializerFactory;
import org.agile.dfs.server.MulticastHandler;
import org.agile.dfs.server.MulticastServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameNodeListener extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(DfsInitializer.class);
    private static NameNodeService nameNodeService = NameNodeService.instance();
    private RpcDeSerializer deserializer = SerializerFactory.instance().getRpcDeSerializer();

    private static final NameNodeListener _instance = new NameNodeListener();

    private NameNodeListener() {

    }

    public static NameNodeListener instance() {
        return _instance;
    }

    public void run() {
        String ip = Configuration.getProperty("name.multicast.client.ip", null);
        int port = Configuration.getPropertyForInt("name.multicast.client.port", 0);
        logger.info("Startup multicast service {}:{}.", ip, String.valueOf(port));
        MulticastServer ms = new MulticastServer(ip, port);
        ms.addHandler(new MulticastHandler() {
            public void handle(String msg) {
                NameNode item = (NameNode) deserializer.read(msg);
                nameNodeService.add(item);
            }
        });
        ms.start();
    }
}
