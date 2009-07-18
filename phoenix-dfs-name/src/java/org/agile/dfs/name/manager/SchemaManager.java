package org.agile.dfs.name.manager;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.DfsSchema;
import org.agile.dfs.dao.IBatisTemplate;
import org.agile.dfs.util.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaManager {
    private static final Logger logger = LoggerFactory.getLogger(SchemaManager.class);
    private static final IBatisTemplate template = ServiceFactory.findService(IBatisTemplate.class);
    private final UuidHexGenerator idGen = (UuidHexGenerator) ServiceFactory.findService(UuidHexGenerator.class);

    public DfsSchema create(DfsSchema dfsSchema) {
        dfsSchema.setId(idGen.generate());
        template.insert("dfs.schema.insert", dfsSchema);
        return dfsSchema;
    }

    public void deleteByName(String name) {
        template.delete("dfs.schema.delete.name", name);
    }

    public DfsSchema findByName(final String name) {
        return (DfsSchema) template.findByParameter("dfs.schema.select.name", name);
    }

    // public DfsSchema save(final DfsSchema ns) {
    // final String nsql = "insert into " + table
    // + " (name,url,status,createTime,description,id) values (?,?,?,?,?,?)";
    // template.doInStatement(nsql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement nstat) throws SQLException {
    // String id = idGen.generate().toString();
    // nstat.setString(1, ns.getName());
    // nstat.setString(2, ns.getUrl());
    // nstat.setString(3, DfsSchema.STATUS_NORMAL);
    // nstat.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
    // nstat.setString(5, ns.getDescription());
    // nstat.setString(6, id);
    // nstat.executeUpdate();
    // ns.setId(id);
    // ResultSet rs = nstat.getGeneratedKeys();
    // if (rs != null && rs.next()) {
    // Object r = rs.getObject(1);
    // if (r != null) {
    // ns.setId(r.toString());
    // }
    // }
    // return null;
    // }
    // }, true);
    //
    // return ns;
    // }
    //
    // /**
    // * Build a new schema, save schema row,create dir table,create file table, init root dir. Notice: total
    // * operation not in same transication
    // *
    // * @param ns
    // */
    // public DfsSchema build(final DfsSchema ns) {
    // this.save(ns);
    // // tblMgr.createDirTable(ns.getName());
    // tblMgr.createFileTable(ns.getName());
    // FileItem root = new FileItem();
    // root.setParentId("0000");
    // root.setName("ROOT");
    // root.setStatus(FileItem.STATUS_NORMAL);
    // root.setType(FileItem.TYPE_DIR);
    // fileMgr.save(ns, root);
    // return ns;
    // }
    //
    // public void destory(final DfsSchema ns) {
    // final String nsql = "delete from " + table + " where id=?";
    // boolean flag = (Boolean) template.doInStatement(nsql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement nstat) throws SQLException {
    // nstat.setString(1, ns.getId());
    // int num = nstat.executeUpdate();
    // return num == 1;
    // }
    // }, true);
    // if (flag) {
    // tblMgr.dropTable(tblLoc.fileTableName(ns));
    // cache.del(ns.getName());
    // }
    // }
    //
    // public List queryAll() {
    // // TODO impl
    // return null;
    // }
    //
    // public List queryAllForMap() {
    // final String sql = "select * from " + table + " order by createTime desc";
    // List list = (List) template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws Exception {
    // pstat.execute();
    // ResultSet rs = pstat.getResultSet();
    // if (rs != null) {
    // SqlResultSetWrapper wrapper = new SqlResultSetWrapper(sql, rs);
    // return wrapper.wrapToMapList();
    // }
    // return null;
    // }
    // }, true);
    // return list == null ? new ArrayList(0) : list;
    // }
    //
    // public DfsSchema findByName(final String ns) {
    // DfsSchema item = (DfsSchema) cache.get(ns);
    // if (item != null) {
    // return item;
    // }
    // final String sql = "select * from " + table + " where name=?";
    // DfsSchema obj = (DfsSchema) template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws Exception {
    // pstat.setString(1, ns);
    // pstat.execute();
    // ResultSet rs = pstat.getResultSet();
    // if (rs != null) {
    // // SqlResultSetWrapper wrapper = new SqlResultSetWrapper(sql, rs);
    // // Map map = wrapper.wrapToMap();
    // if (rs.next()) {
    // DfsSchema ns = new DfsSchema();
    // ns.setId(rs.getString("id"));
    // ns.setName(rs.getString("name"));
    // ns.setUrl(rs.getString("url"));
    // ns.setStatus(rs.getString("status"));
    // return ns;
    // }
    // // return DfsSchema.fromMap(map);
    // }
    // return null;
    // }
    // }, false);
    // if (obj != null) {
    // cache.put(ns, obj);
    // }
    // return obj;
    // }
}
