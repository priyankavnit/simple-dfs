package org.agile.dfs.name.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.exception.NameNodeException;
import org.agile.dfs.name.jdbc.JdbcTemplate;
import org.agile.dfs.name.jdbc.JdbcTemplate.PreparedStatementAction;
import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileItemManager {
    private static final Logger logger = LoggerFactory.getLogger(FileItemManager.class);
    private final JdbcTemplate template = (JdbcTemplate) ServiceFactory.findService(JdbcTemplate.class);
    private final TableLocator tlocator = (TableLocator) ServiceFactory.findService(TableLocator.class);
    private final UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);
    private static final NameSpaceManager nsMgr = (NameSpaceManager) ServiceFactory.findService(NameSpaceManager.class);

    // TODO l3, improve cache impl, support clear
    private static final Map roots = new HashMap();
    private static final Map path2Items = new HashMap();
    private static final Map idName2Items = new HashMap();

    public FileItem findRoot(NameSpace ns) {
        // query from cache
        FileItem cache = (FileItem) roots.get(ns.getName());
        if (cache != null) {
            return cache;
        }
        // TODO impl, locate table by namespace.
        String sql = "select * from " + tlocator.dirTableName(ns) + " where parentId=? and name=?";
        FileItem item = (FileItem) template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws SQLException {
                pstat.setString(1, "0000");
                pstat.setString(2, "ROOT");
                pstat.execute();
                ResultSet rs = pstat.getResultSet();
                if (rs.next()) {
                    return wrapToDir(rs);
                } else {
                    return null;
                }
            }
        }, false);
        if (item == null) {
            throw new DfsException("NameSpace {" + ns.getName() + "}, root is null!");
        }
        // fill to cache
        roots.put(ns.getName(), item);
        return item;
    }

    public FileItem findById(String id) {
        // TODO impl
        return null;
    }

    public FileItem findByPath(String ns, String path) {
        NameSpace obj = nsMgr.findByName(ns);
        return this.findByPath(obj, path, false);
    }

    public FileItem findByPath(NameSpace ns, String path) {
        return this.findByPath(ns, path, false);
    }

    public FileItem findByPath(NameSpace ns, String path, boolean isDir) {
        FileItem cache = (FileItem) path2Items.get(path);
        if (cache != null) {
            return cache;
        }
        String[] paths = StringUtil.simpleSplit(path, '/');
        FileItem parent = this.findRoot(ns);
        String base = "";
        for (int i = 0, len = paths.length; i < len; i++) {
            String name = paths[i];
            String p = base + "/" + name;
            boolean flag = true;
            if (i == len - 1) {
                if (!isDir) {
                    flag = false;
                }
            }
            FileItem curr = this.findByParentIdAndName(ns, parent.getId(), name, flag);
            if (curr == null) {
                return null;
            }
            base = p;
            parent = curr;
        }
        path2Items.put(path, parent);
        return parent;
    }

    private FileItem wrapToDir(ResultSet rs) throws SQLException {
        FileItem it = new FileItem();
        // TODO level 5, use index instead column name.
        it.setId(rs.getString("id"));
        // it.setNsId(rs.getString("nsId"));
        it.setParentId(rs.getString("parentId"));
        it.setName(rs.getString("name"));
        it.setStatus(rs.getString("status"));
        return it;
    }

    // private FileItem wrapToFileItem(ResultSet rs) throws SQLException {
    // FileItem it = new FileItem();
    // // TODO level 5, use index instead column name.
    // it.setId(rs.getString("id"));
    // // it.setNsId(rs.getString("nsId"));
    // it.setParentId(rs.getString("parentId"));
    // it.setName(rs.getString("name"));
    // it.setType(rs.getString("type"));
    // it.setStatus(rs.getString("status"));
    // it.setBlockNum(rs.getInt("blockNum"));
    // it.setCopyNum(rs.getInt("copyNum"));
    // return it;
    // }

    private FileItem findByParentIdAndName(final NameSpace ns, final String pid, final String name, boolean isDir) {
        // TODO l2, use management cache impl.
        String key = pid + "$" + name;
        FileItem cache = (FileItem) idName2Items.get(key);
        if (cache != null) {
            return cache;
        }
        String sql = "";
        if (isDir) {
            sql = "select * from " + tlocator.dirTableName(ns) + " where";
        } else {
            sql = "select * from " + tlocator.fileTableName(ns) + " where";
        }
        sql += " parentId=? and name=?";
        FileItem item = (FileItem) template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws SQLException {
                pstat.setString(1, pid);
                pstat.setString(2, name);
                pstat.execute();
                ResultSet rs = pstat.getResultSet();
                if (rs.next()) {
                    return wrapToDir(rs);
                } else {
                    return null;
                }
            }
        }, false);
        // fill cache
        if (item != null) {
            idName2Items.put(key, item);
        }
        return item;
    }

    public FileItem save(NameSpace ns, FileItem item) {
        if (FileItem.TYPE_DIR.equals(item.getType())) {
            return saveDir(ns, item);
        } else if (FileItem.TYPE_FILE.equals(item.getType())) {
            return saveFile(ns, item);
        }
        logger.error("File item {} type is null!", item);
        return null;
    }

    private FileItem saveFile(NameSpace ns, final FileItem item) {
        final String sql = "insert into " + tlocator.fileTableName(ns) + "(parentId,name,status,id) values (?,?,?,?)";
        template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws Exception {
                String id = idGen.generate().toString();
                pstat.setString(1, item.getParentId());
                pstat.setString(2, item.getName());
                pstat.setString(3, FileItem.STATUS_NORMAL);
                pstat.setString(4, id);
                pstat.executeUpdate();
                item.setId(id);
                return null;
            }
        }, true);

        return item;

    }

    private FileItem saveDir(NameSpace ns, final FileItem item) {
        final String sql = "insert into " + tlocator.dirTableName(ns) + "(parentId,name,status,id) values (?,?,?,?)";
        template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws Exception {
                String id = idGen.generate().toString();
                pstat.setString(1, item.getParentId());
                pstat.setString(2, item.getName());
                pstat.setString(3, FileItem.STATUS_NORMAL);
                pstat.setString(4, id);
                pstat.executeUpdate();
                item.setId(id);
                return null;
            }
        }, true);
        return item;
    }

    public FileItem mkfile(NameSpace ns, String path) {
        int p = path.lastIndexOf("/");
        String d = path.substring(0, p);
        String n = path.substring(p + 1);
        FileItem parent = this.findByPath(ns, d, true);
        if (parent == null) {
            throw new NameNodeException("Parent directory not exists!");
        }
        FileItem item = new FileItem();
        item.setName(n);
        item.setParentId(parent.getId());
        item.setNsId(ns.getId());
        item.setType(FileItem.TYPE_FILE);
        return save(ns, item);
    }

    public FileItem mkdir(NameSpace ns, String dir, boolean mkp) {
        if (mkp) {
            String[] paths = StringUtil.simpleSplit(dir, '/');
            FileItem parent = this.findRoot(ns);
            String base = "";
            for (int i = 0, len = paths.length; i < len; i++) {
                String name = paths[i];
                String p = base + "/" + name;
                FileItem curr = findByParentIdAndName(ns, parent.getId(), name, true);
                if (curr == null) {
                    curr = new FileItem();
                    curr.setNsId(ns.getId());
                    curr.setParentId(parent.getId());
                    curr.setName(name);
                    curr.setType(FileItem.TYPE_DIR);
                    this.save(ns, curr);
                }
                base = p;
                parent = curr;
            }
            return parent;
        } else {
            int p = dir.lastIndexOf("/");
            String d = dir.substring(0, p);
            String n = dir.substring(p + 1);
            FileItem parent = this.findByPath(ns, d);
            if (parent == null) {
                throw new NameNodeException("Parent directory not exists!");
            }
            FileItem curr = new FileItem();
            curr.setNsId(ns.getId());
            curr.setParentId(parent.getId());
            curr.setName(n);
            curr.setType(FileItem.TYPE_DIR);
            this.save(ns, curr);
            return curr;
        }
    }

}
