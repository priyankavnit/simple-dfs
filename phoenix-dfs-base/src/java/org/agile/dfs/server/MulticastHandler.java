package org.agile.dfs.server;

import java.net.InetAddress;

public interface MulticastHandler {

    public void handle(InetAddress addr, String msg);
}
