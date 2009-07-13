package org.agile.dfs.core.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUuidGenerator {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractUuidGenerator.class);

    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private static short counter = (short) 0;
    private static final String IP;
    static {
        long ipadd;
        try {
            InetAddress addr = getRawIpAddress();
            logger.info("Use {} as ip address for UuidGenerator.", addr);
            byte[] bs = addr == null ? new byte[] { 127, 0, 0, 1 } : addr.getAddress();
            ipadd = toInt(bs); 
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = superFormat(ipadd, 8);
    }

    private static InetAddress getRawIpAddress() {
        Enumeration allNetInterfaces = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            // ignore
        }
        while (allNetInterfaces != null && allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = (InetAddress) addresses.nextElement();
                if (addr != null && addr instanceof Inet4Address) {
                    String ip = addr.getHostAddress();
                    if (!StringUtil.startsWith(ip, "127.")) {
                        return addr;
                    }
                }
            }
        }
        return null;
    }

    public AbstractUuidGenerator() {
    }

    public static long toInt(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    protected static String superFormat(long val, int len) {
        String src = Long.toString(val, 32);
        String s = new String("0000000000");
        s = s + src;
        return s.substring(s.length() - len);
    }

    /**
     * Unique across JVMs on this machine (unless they load this class in the same quater second - very unlikely)
     */
    protected int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are > Short.MAX_VALUE instances created in a
     * millisecond)
     */
    protected short getCount() {
        synchronized (AbstractUuidGenerator.class) {
            if (counter < 0)
                counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected String getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }

}
