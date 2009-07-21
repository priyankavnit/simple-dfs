package org.agile.dfs.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastServer {
    protected final static Logger logger = LoggerFactory.getLogger(MulticastServer.class);

    public static final int MAX_PACKET_LEN = 1 * 1024; // 1k

    protected String multicastIp;
    protected int multicastPort;
    protected MulticastSocket multicastSocket;
    protected String status;

    private List<MulticastHandler> handlers = new ArrayList<MulticastHandler>();

    public MulticastServer(String multicastIp, int multicastPort) {
        this.multicastIp = multicastIp;
        this.multicastPort = multicastPort;
        buildMulticastSocket();
    }

    private void buildMulticastSocket() {
        try {
            multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(multicastIp));
        } catch (IOException e) {
            logger.error("Parse ip to inetaddress error!", e);
            throw new RuntimeException("Build multicast socket error!", e);
        }
    }

    public void addHandler(MulticastHandler handler) {
        this.handlers.add(handler);
    }

    public void setHandlers(List<MulticastHandler> handlers) {
        this.handlers = handlers;
    }

    public void start() {
        logger.info("Multicast server {}:{} started!", multicastIp, String.valueOf(multicastPort));
        while (true) {
            byte[] arb = new byte[4 * 1024];
            DatagramPacket datagramPacket = new DatagramPacket(arb, arb.length);
            try {
                multicastSocket.receive(datagramPacket);
                this.handle(datagramPacket);
            } catch (IOException e) {
                logger.error("Receive multicast message error!", e);
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException ei) {
                    // do nothing
                }
                buildMulticastSocket();
            }
        }
    }

    // TODO with client, handle msg security
    private void handle(DatagramPacket packet) {
        int len = packet.getLength();
        String msg = new String(packet.getData(), 0, len);
        for (int i = 0; i < handlers.size(); i++) {
            MulticastHandler handle = handlers.get(i);
            try {
                handle.handle(msg);
            } catch (Exception e) {
                logger.error("Use {} handle msg {} error!", handle.toString(), msg);
                logger.error(" - ", e);
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void main(String[] arstring) {
        try {
            MulticastServer ms = new MulticastServer("239.1.1.9", 45300);
            ms.addHandler(new MulticastHandler() {
                public void handle(String msg) {
                    System.out.println(msg);
                }
            });
            ms.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}