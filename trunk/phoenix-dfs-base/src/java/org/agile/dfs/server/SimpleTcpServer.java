package org.agile.dfs.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTcpServer {
    protected static final Logger logger = LoggerFactory.getLogger(SimpleTcpServer.class);

    protected String ip;
    protected int port;

    private SimpleThreadPool simpleThreadPool;

    public SimpleTcpServer() {
        // TODO l3, thread number to configurable.
        simpleThreadPool = new SimpleThreadPool(10);
    }

    public void execute(final Runnable task) {
        simpleThreadPool.execute(task);
    }

    // private String getLocalHostAddress() {
    // List addrs = this.getLocalHostAllAddress();
    // if (addrs.isEmpty()) {
    // throw new RuntimeException("Can't get localhost address!");
    // } else {
    // return (String) addrs.get(0);
    // }
    // }
    //
    // private List getLocalHostAllAddress() {
    // List ret = new ArrayList();
    //
    // Enumeration allNetInterfaces = null;
    // try {
    // allNetInterfaces = NetworkInterface.getNetworkInterfaces();
    // } catch (SocketException e) {
    // logger.error("Get localhost netinterface error!", e);
    // }
    // while (allNetInterfaces != null && allNetInterfaces.hasMoreElements()) {
    // NetworkInterface netInterface = (NetworkInterface)
    // allNetInterfaces.nextElement();
    // Enumeration addresses = netInterface.getInetAddresses();
    // while (addresses.hasMoreElements()) {
    // InetAddress addr = (InetAddress) addresses.nextElement();
    // if (addr != null && addr instanceof Inet4Address) {
    // String ip = addr.getHostAddress();
    // if (!StringUtil.startsWith(ip, "127.")) {
    // ret.add(ip);
    // }
    // }
    // }
    // }
    // return ret;
    // }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
