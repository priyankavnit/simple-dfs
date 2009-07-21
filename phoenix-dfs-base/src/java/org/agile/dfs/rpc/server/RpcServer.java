package org.agile.dfs.rpc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.endpoint.TcpEndpoint;
import org.agile.dfs.server.SimpleTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RpcServer extends SimpleTcpServer {
    private final static Logger logger = LoggerFactory.getLogger(RpcServer.class);

    protected NodeItem _self;;

    public final void startup() {
        ServerSocket server;
        try {
            self().setStatus(NodeItem.NODE_STATUS_INIT);
            server = new ServerSocket();
            server.bind(new InetSocketAddress(ip, port));
            self().setStatus(NodeItem.NODE_STATUS_RUN);
        } catch (IOException e) {
            self().setStatus(NodeItem.NODE_STATUS_ERROR);
            logger.info("Startup rpc server error!", e);
            throw new RuntimeException("Can't startup tcp server! ", e);
        }

        logger.info("Start rpc server {} as {} node ", self(), getNodeType());
        while (true) {
            Socket socket;
            try {
                socket = server.accept();
                socket.setKeepAlive(true);
                Endpointable endpoint = new TcpEndpoint(socket);
                RpcRunnable run = new RpcRunnable(endpoint, this.getRpcHandler());
                this.execute(run);
            } catch (IOException ioe) {

            } catch (Exception e) {

            }
            logger.info("Wait for next call ... ");
        }

    }

    public NodeItem self() {
        if (_self == null) {
            _self = new NodeItem(this.getNodeType(), this.getIp(), this.getPort());
            _self.setStatus(NodeItem.NODE_STATUS_INIT);
        }
        return _self;
    }

    public abstract RpcHandler getRpcHandler();

    public abstract String getNodeType();

}
