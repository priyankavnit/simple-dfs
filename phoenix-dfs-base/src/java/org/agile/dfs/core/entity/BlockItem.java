package org.agile.dfs.core.entity;

public class BlockItem {
    private String id;
    private FileItem file;
    private NodeItem node;
    private String status;
    private int blockNo;
    private int copyNo;
    private int capacity;
    private int size;

    public static BlockItem fromString(String s) {
        BlockItem item = new BlockItem();
        // TODO impl
        return item;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("id:").append(id).append(",");
        sb.append("file:").append(file).append(",");
        sb.append("node:").append(node).append(",");
        sb.append("status:").append(status).append(",");
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

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }

    public NodeItem getNode() {
        return node;
    }

    public void setNode(NodeItem node) {
        this.node = node;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
