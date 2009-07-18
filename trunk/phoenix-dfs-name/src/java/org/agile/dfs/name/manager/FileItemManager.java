package org.agile.dfs.name.manager;

import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.dao.IBatisTemplate;
import org.agile.dfs.util.ObjectUtil;
import org.agile.dfs.util.ServiceFactory;

public class FileItemManager {
    private static final IBatisTemplate template = ServiceFactory.findService(IBatisTemplate.class);
    private static final UuidHexGenerator idGen = ServiceFactory.findService(UuidHexGenerator.class);

    public FileItem create(String table, FileItem item) {
        item.setId(idGen.generate());
        Map<String, Object> map = ObjectUtil.toMap(item);
        map.put("table", table);
        template.insert("dfs.fs.insert", map);
        return item;
    }

    public void deleteById(String table, String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("table", table);
        map.put("id", id);
        template.delete("dfs.fs.delete.id", map);
    }

    // public FileItem findRoot(DfsSchema ns) {
    // // query from cache
    // FileItem cache = (FileItem) roots.get(ns.getName());
    // if (cache != null) {
    // return cache;
    // }
    // // TODO impl, locate table by schema.
    // String sql = "select * from " + tlocator.fileTableName(ns) + " where parentId=? and name=?";
    // FileItem item = (FileItem) template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws SQLException {
    // pstat.setString(1, "0000");
    // pstat.setString(2, "ROOT");
    // pstat.execute();
    // ResultSet rs = pstat.getResultSet();
    // if (rs.next()) {
    // return wrap(rs);
    // } else {
    // return null;
    // }
    // }
    // }, false);
    // if (item == null) {
    // throw new DfsException("DfsSchema {" + ns.getName() + "}, root is null!");
    // }
    // // fill to cache
    // roots.put(ns.getName(), item);
    // return item;
    // }
    //
    // public FileItem findById(String id) {
    // // TODO impl
    // return null;
    // }
    //
    // public FileItem findByPath(String ns, String path) {
    // DfsSchema obj = nsMgr.findByName(ns);
    // return this.findByPath(obj, path);
    // }
    //
    // public FileItem findByPath(DfsSchema ns, String path) {
    // FileItem cache = (FileItem) path2Items.get(path);
    // if (cache != null) {
    // return cache;
    // }
    // String[] paths = StringUtil.simpleSplit(path, '/');
    // FileItem parent = this.findRoot(ns);
    // String base = "";
    // for (int i = 0, len = paths.length; i < len; i++) {
    // String name = paths[i];
    // String p = base + "/" + name;
    // FileItem curr = this.findByParentIdAndName(ns, parent.getId(), name);
    // if (curr == null) {
    // return null;
    // }
    // base = p;
    // parent = curr;
    // }
    // path2Items.put(path, parent);
    // return parent;
    // }
    //
    // // public FileItem findByPath(DfsSchema ns, String path, String type) {
    // // FileItem cache = (FileItem) path2Items.get(path);
    // // if (cache != null) {
    // // return cache;
    // // }
    // // String[] paths = StringUtil.simpleSplit(path, '/');
    // // FileItem parent = this.findRoot(ns);
    // // String base = "";
    // // for (int i = 0, len = paths.length; i < len; i++) {
    // // String name = paths[i];
    // // String p = base + "/" + name;
    // // String tt = FileItem.TYPE_DIR;
    // // if (i == len - 1) {
    // // if (!FileItem.TYPE_DIR.equals(type)) {
    // // tt = FileItem.TYPE_FILE;
    // // }
    // // }
    // // FileItem curr = this.findByParentIdAndName(ns, parent.getId(), name, tt);
    // // if (curr == null) {
    // // return null;
    // // }
    // // base = p;
    // // parent = curr;
    // // }
    // // path2Items.put(path, parent);
    // // return parent;
    // // }
    //
    // private FileItem wrap(ResultSet rs) throws SQLException {
    // FileItem it = new FileItem();
    // // TODO level 5, use index instead column name.
    // it.setId(rs.getString("id"));
    // // it.setNsId(rs.getString("nsId"));
    // it.setParentId(rs.getString("parentId"));
    // it.setName(rs.getString("name"));
    // it.setStatus(rs.getString("status"));
    // return it;
    // }
    //
    // private FileItem findByParentIdAndName(final DfsSchema ns, final String pid, final String name) {
    // // TODO l2, use management cache impl.
    // String key = pid + "$" + name;
    // FileItem cache = (FileItem) idName2Items.get(key);
    // if (cache != null) {
    // return cache;
    // }
    // String sql = "select * from " + tlocator.fileTableName(ns) + " where parentId=? and name=?";
    // FileItem item = (FileItem) template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws SQLException {
    // pstat.setString(1, pid);
    // pstat.setString(2, name);
    // pstat.execute();
    // ResultSet rs = pstat.getResultSet();
    // if (rs.next()) {
    // return wrap(rs);
    // } else {
    // return null;
    // }
    // }
    // }, false);
    // // fill cache
    // if (item != null) {
    // idName2Items.put(key, item);
    // }
    // return item;
    // }
    //
    // public FileItem save(DfsSchema ns, final FileItem item) {
    // final String sql = "insert into " + tlocator.fileTableName(ns) + "(parentId,name,status,id) values (?,?,?,?)";
    // template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws Exception {
    // String id = idGen.generate().toString();
    // pstat.setString(1, item.getParentId());
    // pstat.setString(2, item.getName());
    // pstat.setString(3, FileItem.STATUS_NORMAL);
    // pstat.setString(4, id);
    // pstat.executeUpdate();
    // item.setId(id);
    // return null;
    // }
    // }, true);
    //
    // return item;
    //
    // }
    //
    // public FileItem mkfile(DfsSchema ns, String path) {
    // int p = path.lastIndexOf("/");
    // String d = path.substring(0, p);
    // String n = path.substring(p + 1);
    // FileItem parent = this.findByPath(ns, d);
    // if (parent == null) {
    // throw new NameNodeException("Parent directory not exists!");
    // }
    // FileItem item = new FileItem();
    // item.setName(n);
    // item.setParentId(parent.getId());
    // item.setType(FileItem.TYPE_FILE);
    // return save(ns, item);
    // }
    //
    // public FileItem mkdir(DfsSchema ns, String dir, boolean mkp) {
    // if (mkp) {
    // String[] paths = StringUtil.simpleSplit(dir, '/');
    // FileItem parent = this.findRoot(ns);
    // String base = "";
    // for (int i = 0, len = paths.length; i < len; i++) {
    // String name = paths[i];
    // String p = base + "/" + name;
    // FileItem curr = findByParentIdAndName(ns, parent.getId(), name);
    // if (curr == null) {
    // curr = new FileItem();
    // curr.setParentId(parent.getId());
    // curr.setName(name);
    // curr.setType(FileItem.TYPE_DIR);
    // this.save(ns, curr);
    // }
    // base = p;
    // parent = curr;
    // }
    // return parent;
    // } else {
    // int p = dir.lastIndexOf("/");
    // String d = dir.substring(0, p);
    // String n = dir.substring(p + 1);
    // FileItem parent = this.findByPath(ns, d);
    // if (parent == null) {
    // throw new NameNodeException("Parent directory not exists!");
    // }
    // FileItem curr = new FileItem();
    // curr.setParentId(parent.getId());
    // curr.setName(n);
    // curr.setType(FileItem.TYPE_DIR);
    // this.save(ns, curr);
    // return curr;
    // }
    // }
    //
    // public boolean delete(final String nameSpace, final FileItem item) {
    // String table = tlocator.fileTableName(nameSpace);
    // final String sql = "delete from " + table + " where id=?";
    // boolean flag = (Boolean) template.doInStatement(sql, new PreparedStatementAction() {
    // public Object excute(PreparedStatement pstat) throws Exception {
    // pstat.setString(1, item.getId());
    // int num = pstat.executeUpdate();
    // return num == 1;
    // }
    // }, true);
    // logger.info("Sucess to delete file ns:{},name:{}", nameSpace, item.getName());
    // return flag;
    // }
}
