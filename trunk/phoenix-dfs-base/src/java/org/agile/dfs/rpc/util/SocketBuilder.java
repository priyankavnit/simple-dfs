package org.agile.dfs.rpc.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.agile.dfs.core.entity.NodeItem;

public class SocketBuilder {

    private static SocketBuilder _instance = new SocketBuilder();

    private SocketBuilder() {

    }

    public static SocketBuilder instance() {
        return _instance;
    }

    public Socket build(NodeItem item) throws IOException {
        return build(item.getIp(), item.getPort());

    }

    public Socket build(String ip, int port) throws IOException {
        // TODO l1, cache socket
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 3 * 1000);
        socket.setSoTimeout(3 * 1000);
        return socket;
    }
}
