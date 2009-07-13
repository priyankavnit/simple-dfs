package org.agile.dfs.core.entity;

import org.agile.dfs.util.StringUtil;

public class NodeItem {
    public static final String NODE_TYPE_NAME = "NAME";
    public static final String NODE_TYPE_DATA = "DATA";
    public static final String NODE_TYPE_CLIENT = "CLIENT";

    public static final String NODE_STATUS_INIT = "INIT";
    public static final String NODE_STATUS_RUN = "RUN";
    public static final String NODE_STATUS_DOWN = "DOWN";
    public static final String NODE_STATUS_ERROR = "ERROR";

    private String type;
    private String ip;
    private int port;
    private String status;
    private int load;

    public NodeItem() {

    }

    public NodeItem(String type, String ip, int port) {
        this.type = type;
        this.ip = ip;
        this.port = port;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NodeItem)) {
            return false;
        }
        NodeItem item = (NodeItem) obj;
        if (StringUtil.equals(this.type, item.type) && StringUtil.equals(this.ip, item.ip) && this.port == item.port) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String s = this.type == null ? "TYPE" : this.type;
        s += this.ip == null ? "IP" : this.ip;
        s += this.port;
        return s.hashCode();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("ip:").append(ip).append(",");
        sb.append("port:").append(port);
        // sb.append("type:").append(type).append(",");
        // sb.append("status:").append(status);
        return sb.toString();
    }

    public static NodeItem fromString(String str) {
        NodeItem item = new NodeItem();
        String[] ps = str.split(",");
        item.setIp(ps[0].substring(3));
        item.setPort(Integer.parseInt(ps[1].substring(5)));
        item.setType(ps[2].substring(5));
        item.setStatus(ps[3].substring(7));
        return item;
    }

    /* getter and setter */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

}
