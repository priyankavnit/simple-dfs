package org.agile.dfs.name.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.core.entity.NameSpace;
import org.agile.dfs.core.factory.ServiceFactory;
import org.agile.dfs.name.jdbc.JdbcTemplate;
import org.agile.dfs.name.jdbc.SqlResultSetWrapper;
import org.agile.dfs.name.jdbc.JdbcTemplate.PreparedStatementAction;

public class NameSpaceManager {
    private final JdbcTemplate template = (JdbcTemplate) ServiceFactory.findService(JdbcTemplate.class);
    private final FileItemManager fileMgr = (FileItemManager) ServiceFactory.findService(FileItemManager.class);
    private final TableManager tblMgr = (TableManager) ServiceFactory.findService(TableManager.class);
    private final UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);
    private String table = "TBL_DFS_NAMESPACE";
    private static final Map names = new HashMap();

    public NameSpace save(final NameSpace ns) {
        final String nsql = "insert into " + table
                + " (name,url,status,createTime,description,id) values (?,?,?,?,?,?)";
        template.doInStatement(nsql, new PreparedStatementAction() {
            public Object excute(PreparedStatement nstat) throws SQLException {
                String id = idGen.generate().toString();
                nstat.setString(1, ns.getName());
                nstat.setString(2, ns.getUrl());
                nstat.setString(3, NameSpace.STATUS_NORMAL);
                nstat.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                nstat.setString(5, ns.getDescription());
                nstat.setString(6, id);
                nstat.executeUpdate();
                ns.setId(id);
                ResultSet rs = nstat.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    Object r = rs.getObject(1);
                    if (r != null) {
                        ns.setId(r.toString());
                    }
                }
                return null;
            }
        }, true);

        return ns;
    }

    /**
     * Build a new namespace, save namespace row,create dir table,create file table, init root dir. Notice: total
     * operation not in same transication
     * 
     * @param ns
     */
    public NameSpace build(final NameSpace ns) {
        this.save(ns);
        tblMgr.createDirTable(ns.getName());
        tblMgr.createFileTable(ns.getName());
        FileItem root = new FileItem();
        root.setParentId("0000");
        root.setName("ROOT");
        root.setStatus(FileItem.STATUS_NORMAL);
        root.setType(FileItem.TYPE_DIR);
        fileMgr.save(ns, root);
        return ns;
    }

    public List queryAll() {
        // TODO impl
        return null;
    }

    public List queryAllForMap() {
        final String sql = "select * from " + table + " order by createTime desc";
        List list = (List) template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws Exception {
                pstat.execute();
                ResultSet rs = pstat.getResultSet();
                if (rs != null) {
                    SqlResultSetWrapper wrapper = new SqlResultSetWrapper(sql, rs);
                    return wrapper.wrapToMapList();
                }
                return null;
            }
        }, true);
        return list == null ? new ArrayList(0) : list;
    }

    public NameSpace findByName(final String ns) {
        // TODO l2, use total cache control
        NameSpace cache = (NameSpace) names.get(ns);
        if (cache != null) {
            return cache;
        }
        final String sql = "select * from " + table + " where name=?";
        NameSpace obj = (NameSpace) template.doInStatement(sql, new PreparedStatementAction() {
            public Object excute(PreparedStatement pstat) throws Exception {
                pstat.setString(1, ns);
                pstat.execute();
                ResultSet rs = pstat.getResultSet();
                if (rs != null) {
                    // SqlResultSetWrapper wrapper = new SqlResultSetWrapper(sql, rs);
                    // Map map = wrapper.wrapToMap();
                    if (rs.next()) {
                        NameSpace ns = new NameSpace();
                        ns.setId(rs.getString("id"));
                        ns.setName(rs.getString("name"));
                        ns.setUrl(rs.getString("url"));
                        ns.setStatus(rs.getString("status"));
                        return ns;
                    }
                    // return NameSpace.fromMap(map);
                }
                return null;
            }
        }, false);
        if (obj != null) {
            names.put(ns, obj);
        }
        return obj;
    }
}
