package org.agile.dfs.core.entity;

import java.util.Date;
import java.util.Map;

public class NameSpace {
    public static final String STATUS_INIT = "INIT";
    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_TRASH = "TRASH";
    public static final String STATUS_DELETE = "DELETE";

    private String id;
    private String name;
    private String url;
    private String status;
    private Date createTime;
    private String description;

    public NameSpace() {
        this.status = STATUS_INIT;
    }

    public NameSpace(String name, String url) {
        this.name = name;
        this.url = url;
        this.status = STATUS_INIT;
    }

    public String toString() {
        return "name:" + name + ",url:" + url + ", status:" + status;
    }

    public static NameSpace fromMap(Map map) {
        if (map == null) {
            return null;
        }
        NameSpace ns = new NameSpace();
        ns.setId(map.get("id") == null ? null : map.get("id").toString());
        ns.setName(map.get("name") == null ? null : map.get("name").toString());
        ns.setUrl(map.get("url") == null ? null : map.get("url").toString());
        ns.setStatus(map.get("status") == null ? null : map.get("status").toString());
        // not set create time and description properties
        return ns;
    }

    /* getter and setter */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
