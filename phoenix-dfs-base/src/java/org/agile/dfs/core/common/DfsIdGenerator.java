package org.agile.dfs.core.common;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DfsIdGenerator.class);
    private static Random random = new Random();
    private static DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
    private static Map class2id = new HashMap();
    private static String jvmId = "JID";
    private static boolean flag = false;

    public DfsIdGenerator() {
        if (!flag) {
            init();
        }
    }

    private synchronized void init() {
        String id = System.getProperty("JVMID");
        if (id != null && id.trim().length() > 0) {
            jvmId = id;
        } else {
            jvmId = this.getFixedAddressSuffix();
        }
        flag = true;
    }

    /** 080808,152356,00008,jvmid,3576 total 24 bit */
    public Serializable generate(Class iclz) {
        Calendar calendar = Calendar.getInstance();
        String clz = iclz.getName();
        int clzSeq;
        synchronized (class2id) {
            // dcl, should be modified
            SequenceItem si = (SequenceItem) class2id.get(clz);
            if (si == null) {
                si = new SequenceItem();
                si.sequence = 1;
                si.second = calendar.get(Calendar.SECOND);
                if (!class2id.containsKey(clz)) {
                    class2id.put(clz, si);
                } else {
                    si = (SequenceItem) class2id.get(clz);
                }
            }
            clzSeq = si.getFixedSequence(calendar);
        }
        StringBuffer sb = new StringBuffer(32);
        // time section
        sb.append(dateFormat.format(calendar.getTime()));
        // sb.append("-");
        // sequence section
        sb.append(getFixedNumber(clzSeq, 5));
        // sb.append("-");
        // jvmid section
        sb.append(jvmId);
        // random section
        sb.append(this.getFixedRandomNumber(4));
        return sb.toString();
    }

    private String getFixedAddressSuffix() {
        String addrs = getLocalHostAddress();
        if (!addrs.startsWith("127")) {
            String sip = addrs.substring(addrs.lastIndexOf(".") + 1);
            sip = "00" + sip;
            return sip.substring(sip.length() - 3, sip.length());
        } else {
            return "255";
        }
    }

    private String getLocalHostAddress() {
        List addrs = this.getLocalHostAllAddress();
        if (addrs.isEmpty()) {
            // throw new RuntimeException("Can't get localhost address!");
            return "127.0.0.1";
        } else {
            return (String) addrs.get(0);
        }
    }

    private List getLocalHostAllAddress() {
        List ret = new ArrayList();

        Enumeration allNetInterfaces = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            logger.error("Get localhost netinterface error!", e);
        }
        while (allNetInterfaces != null && allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = (InetAddress) addresses.nextElement();
                if (addr != null && addr instanceof Inet4Address) {
                    String ip = addr.getHostAddress();
                    if (!StringUtil.startsWith(ip, "127.")) {
                        ret.add(ip);
                    }
                }
            }
        }
        return ret;
    }

    private String getFixedRandomNumber(int len) {
        int max = (int) Math.round(Math.pow(10, len));
        int num = random.nextInt(max);
        String s = new StringBuffer().append(max).append(num).toString();
        return s.substring(s.length() - len, s.length());
    }

    private String getFixedNumber(int num, int len) {
        int max = (int) Math.round(Math.pow(10, len));
        String s = new StringBuffer().append(max).append(num).toString();
        return s.substring(s.length() - len, s.length());
    }

    class SequenceItem {
        int sequence;
        int second;
        int lastSecond;

        public synchronized int getFixedSequence(Calendar calendar) {
            int sec = calendar.get(Calendar.SECOND);
            if (sec != lastSecond) {
                sequence = 1;
            } else {
                sequence++;
            }
            lastSecond = sec;
            return sequence;
        }
    }

    public static void main(String[] args) throws Exception {
        DfsIdGenerator aig = new DfsIdGenerator();
        System.out.println(">" + aig.getFixedAddressSuffix() + "<");
        System.out.println(">" + System.currentTimeMillis() + "<");
        for (int i = 0; i < 5; i++) {
            System.out.println(">" + aig.generate(Object.class) + "<");
        }
    }
}
