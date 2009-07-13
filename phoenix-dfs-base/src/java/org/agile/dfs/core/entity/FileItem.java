package org.agile.dfs.core.entity;

public class FileItem {
    public static final String TYPE_DIR = "dir";
    public static final String TYPE_FILE = "file";
    public static final String STATUS_INIT = "INIT";
    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_TRASH = "TRASH";
    public static final String STATUS_DELETE = "DELETE";
    
    private String nsId;
    private String parentId;
    private String id;
    private String name;
    private String status;
    private int blockNum;
    private int copyNum;
    private String type;

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("nsId:").append(nsId).append(",");
        sb.append("id:").append(id).append(",");
        sb.append("parentId:").append(parentId).append(",");
        sb.append("name:").append(name).append(",");
        sb.append("status:").append(status);
        return sb.toString();
    }

    public static FileItem fromString(String s) {
        if (s == null || s.equalsIgnoreCase("null")) {
            return null;
        } else {
            FileItem item = new FileItem();
            // TODO impl
            return item;
        }
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

    public int getCopyNum() {
        return copyNum;
    }

    public void setCopyNum(int copyNum) {
        this.copyNum = copyNum;
    }

    public String getNsId() {
        return nsId;
    }

    public void setNsId(String nsId) {
        this.nsId = nsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
