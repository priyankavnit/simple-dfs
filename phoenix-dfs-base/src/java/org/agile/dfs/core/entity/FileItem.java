package org.agile.dfs.core.entity;

public class FileItem implements java.io.Serializable { 
    private static final long serialVersionUID = 1978;
    public static final String TYPE_DIR = "DIR";
    public static final String TYPE_FILE = "FILE";
    public static final String STATUS_INIT = "INIT";
    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_TRASH = "TRASH";
    public static final String STATUS_DELETE = "DELETE";

    private String parentId;// not null
    private String id;// not null
    private String name;// not null
    private String status;// not null
    private int blockNum;// not null, default 0
    private int minCopyNum;// not null, default 3
    private int maxCopyNum;// not null, default 3
    private int nowCopyNum;// not null, default 0
    private String type;// not null
    private int version;// not null

    public boolean isDirectory() {
        return TYPE_DIR.equals(type);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("id:").append(id).append(",");
        sb.append("parentId:").append(parentId).append(",");
        sb.append("name:").append(name).append(",");
        sb.append("status:").append(status);
        return sb.toString();
    }

    /* getter and setter */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String directoryId) {
        this.parentId = directoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public int getNowCopyNum() {
        return nowCopyNum;
    }

    public void setNowCopyNum(int copyNum) {
        this.nowCopyNum = copyNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinCopyNum() {
        return minCopyNum;
    }

    public void setMinCopyNum(int minCopyNum) {
        this.minCopyNum = minCopyNum;
    }

    public int getMaxCopyNum() {
        return maxCopyNum;
    }

    public void setMaxCopyNum(int maxCopyNum) {
        this.maxCopyNum = maxCopyNum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
