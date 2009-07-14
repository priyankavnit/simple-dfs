package org.agile.dfs.data;

import org.agile.dfs.core.common.Configuration;
import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.data.rpc.DataServer;
import org.agile.dfs.multicast.MulticastClient;
import org.agile.dfs.rpc.serialize.RpcSerializer;
import org.agile.dfs.rpc.serialize.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataHeartBeat extends Thread {
    protected final static Logger logger = LoggerFactory.getLogger(DataHeartBeat.class);
    private RpcSerializer serializer = SerializerFactory.instance().getRpcSerializer();
    private NodeItem node;

    public DataHeartBeat(DataServer server) {
        this.node = server.self();
    }

    public void run() {
        String multicastIp = Configuration.getProperty("data.multicast.name.ip", null);
        int multicastPort = Configuration.getPropertyForInt("data.multicast.name.port", 0);
        int interval = Configuration.getPropertyForInt("data.multicast.name.interval", 0);
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
