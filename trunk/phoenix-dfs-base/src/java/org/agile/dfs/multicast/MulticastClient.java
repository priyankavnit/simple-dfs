package org.agile.dfs.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastClient {
    protected final static Logger logger = LoggerFactory.getLogger(MulticastClient.class);
    public static final int MAX_PACKET_LEN = 1 * 1024; // 1k
    
    protected String multicastIp;
    protected InetAddress multicastAddress;
    protected int multicastPort;
    protected MulticastSocket multicastSocket;

    public MulticastClient(String multicastIp, int multicastPort) {
        this.multicastPort = multicastPort;
        try {
            multicastAddress = InetAddress.getByName(multicastIp);
            multicastSocket = new MulticastSocket();
        } catch (IOException e) {
            logger.error("Parse ip to inetaddress error!", e);
            throw new RuntimeException("Generate multicast client error!", e);
        }
    }

    // TODO handle message security
    public void send(String msg) {
        if (msg.length() > MAX_PACKET_LEN) {
            throw new RuntimeException("Datagrampacket length is over " + MAX_PACKET_LEN);
        }
        byte[] msgBytes = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(msgBytes, msgBytes.length, multicastAddress, multicastPort);
        // logger.debug("Ready to send msg {} ", msg);
        try {
            multicastSocket.send(datagramPacket);
        } catch (IOException e) {
            logger.error("Send datagrampacket error!", e);
            throw new RuntimeException("Send datagrampacket error!", e);
        }
    }

    public static void main(String[] arstring) {
        try {
            // Create a datagram package and send it to the multicast
            // group at239.0.0.1, port 7892.
            MulticastClient mc = new MulticastClient("239.0.0.1", 7892);
            for (int i = 0; i < 500; i++) {
                mc.send("hello world!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}