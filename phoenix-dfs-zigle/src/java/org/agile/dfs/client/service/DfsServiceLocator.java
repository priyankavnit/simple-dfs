package org.agile.dfs.client.service;

import org.agile.dfs.core.entity.NodeItem;
import org.agile.dfs.rpc.client.AsyncProxyFactory;
import org.agile.dfs.rpc.client.RpcProxyFactory;

public class DfsServiceLocator {

    private static final RpcProxyFactory proxyFactory = new RpcProxyFactory();
    private static final AsyncProxyFactory asyncFactory = new AsyncProxyFactory();
    private static final NameNodeService nodeLocator = NameNodeService.instance();

    public static <T> T lookup(Class<T> clz) {
        String pn = clz.getPackage().getName();
        if (pn.startsWith("org.agile.dfs.name")) {
            NodeItem node = nodeLocator.findNameNode();
            return proxyFactory.findService(clz, "tcp://" + node.getIp() + ":" + node.getPort());
        } else if (pn.startsWith("org.agile.dfs.data")) {
            return proxyFactory.findService(clz, "tcp://localhost:45200");
        }
        // last method
        T obj = proxyFactory.findService(clz, null);
        return obj;
    }

    public static <T> T async(Class<T> clz) {
        String pn = clz.getPackage().getName();
        if (pn.startsWith("org.agile.dfs.name")) {
            NodeItem node = nodeLocator.findNameNode();
            return asyncFactory.findService(clz, "tcp://" + node.getIp() + ":" + node.getPort());
        } else if (pn.startsWith("org.agile.dfs.data")) {
            return asyncFactory.findService(clz, "tcp://localhost:45200");
        }
        // last method
        T obj = asyncFactory.findService(clz, null);
        return obj;
    }
}
