package org.agile.dfs.name.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlResultSetWrapper {
    private static final Logger logger = LoggerFactory.getLogger(SqlResultSetWrapper.class);

    public static int MAX_ROW_NUM = 100;
    private static final Map metas = new HashMap();
    private String sql;
    private ResultSet rs;
    private ResultMeta rm;

    public SqlResultSetWrapper(String sql, ResultSet rs) {
        if (sql == null || rs == null) {
            throw new NullPointerException("Sql or ResultSet is null! ");
        }
        this.sql = sql;
        this.rs = rs;
        rm = (ResultMeta) metas.get(sql);
        if (rm == null) {
            this.init();
        }
    }

    private void init() {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int cn = rsmd.getColumnCount();
            ResultMeta rm = new ResultMeta(cn);
            for (int i = 1; i <= cn; i++) {
                String name = rsmd.getColumnName(i);
                rm.setColName(i, name);
            }
            this.rm = rm;
            synchronized (metas) {
                metas.put(sql, rm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List wrapToMapList() throws SQLException {
        if (rs == null) {
            return new ArrayList(0);
        } else {
            List list = new ArrayList();
            while (rs.next()) {
                if (list.size() >= MAX_ROW_NUM) {
                    logger.warn("Result rows over {}, lost some data!", String.valueOf(MAX_ROW_NUM));
                    break;
                }
                Map map = wrapCurrentRowToMap();
                list.add(map);
            }
            return list;
        }
    }

    public Map wrapToMap() throws SQLException {
        if (rs == null) {
            return null;
        } else {
            while (rs.next()) {
                Map map = wrapCurrentRowToMap();
                return map;
            }
            return null;
        }
    }

    private Map wrapCurrentRowToMap() throws SQLException {
        // impl high performance and simple map
        Map map = new HashMap();
        for (int i = 1; i <= rm.colSize(); i++) {
            Object obj = rs.getObject(i);
            map.put(rm.getColName(i), obj);
        }
        return map;
    }
}
