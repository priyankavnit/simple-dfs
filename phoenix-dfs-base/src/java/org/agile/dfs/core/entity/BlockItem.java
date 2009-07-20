package org.agile.dfs.core.entity;

public class BlockItem implements java.io.Serializable {
    private static final long serialVersionUID = 1978;
    public static final String STATUS_READY = "READY";
    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_TRASH = "TRASH";
    public static final String STATUS_DELETE = "DELETE";

    private String id;
    private String nodeId;
    private String fileId;
    private String status;
    private int blockNo;
    private int copyNo;
    private int capacity;
    private int size;

    public int free() {
        return capacity - size;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("id:").append(id).append(",");
        sb.append("blockNo:").append(blockNo).append(",");
        sb.append("copyNo:").append(copyNo).append(",");
        sb.append("capacity:").append(capacity).append(",");
        sb.append("size:").append(size);
        return sb.toString();
    }

    /* getter and setter */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(int blockNo) {
        this.blockNo = blockNo;
    }

    public int getCopyNo() {
        return copyNo;
    }

    public void setCopyNo(int copyNo) {
        this.copyNo = copyNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
