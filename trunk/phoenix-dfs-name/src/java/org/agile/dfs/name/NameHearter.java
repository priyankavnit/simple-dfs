package org.agile.dfs.name;

import org.agile.dfs.config.Configuration;
import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.multicast.MulticastClient;
import org.agile.dfs.name.rpc.NameServer;
import org.agile.dfs.rpc.serialize.RpcSerializer;
import org.agile.dfs.rpc.serialize.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameHearter extends Thread {
    protected final static Logger logger = LoggerFactory.getLogger(NameHearter.class);
    private RpcSerializer serializer = SerializerFactory.instance().getRpcSerializer();
    private NodeItem node;

    public NameHearter(NameServer server) {
        this.node = server.self();
    }

    public void run() {
        String multicastIp = Configuration.getProperty("name.multicast.client.ip", null);
        int multicastPort = Configuration.getPropertyForInt("name.multicast.client.port", 0);
        int interval = Configuration.getPropertyForInt("name.multicast.client.interval", 0);
        MulticastClient client = new MulticastClient(multicastIp, multicastPort);
        while (true) {
            try {
                String msg = serializer.write(node);
                client.send(msg);
                Thread.sleep(interval);
            } catch (Exception e) {
                logger.error("Send heart beat data packet error!", e);
            }
        }
    }
}
