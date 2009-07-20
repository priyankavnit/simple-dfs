package org.agile.dfs.core.entity;


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
        if (item == this) {
            return true;
        }
        if (item.port == port && (item.ip != null && item.ip.equals(ip))) {
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
        sb.append("type:").append(type);
        return sb.toString();
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
