package org.agile.dfs.name.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.agile.dfs.name.jdbc.JdbcTemplate;
import org.agile.dfs.name.jdbc.JdbcTemplate.ConnectionAction;
import org.agile.dfs.name.jdbc.JdbcTemplate.StatementAction;
import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableManager {
    private static final Logger logger = LoggerFactory.getLogger(TableManager.class);
    private final JdbcTemplate template = new JdbcTemplate();
    private final TableLocator tlocator = new TableLocator();

    public boolean existsTable(String tableName) {
        final String sql = new StringBuffer("SELECT * FROM ").append(tableName).append(" WHERE 1=2").toString();
        try {
            template.doInStatement(new StatementAction() {
                public Object excute(Statement stat) throws Exception {
                    logger.info("Check table exists sql {}", sql);
                    stat.execute(sql);
                    return null;
                }
            }, false);
            return true;
        } catch (Throwable e) {
            // ignore
            return false;
        }
    }

    public boolean dropTable(String tableName) {
        final String sql = new StringBuffer("DROP TABLE ").append(tableName).toString();
        try {
            template.doInConnection(new ConnectionAction() {
                public Object excute(Connection conn) throws Exception {
                    logger.info("Drop table sql {}", sql);
                    Statement state = conn.createStatement();
                    state.execute(sql);
                    return null;
                }
            }, false);
            logger.info("Drop table {} success. ", tableName);
            return true;
        } catch (RuntimeException e) {
            logger.info("Drop table {} exception. {}", tableName, e);
            return false;
        }
    }

    // public void createDirTable(String schema) {
    // final String tableName = tlocator.dirTableName(schema);
    // final List list = readAllStatement("/dfs-sql-dir.sql");
    // if (!existsTable(tableName)) {
    // template.doInStatement(new StatementAction() {
    // public Object excute(Statement stat) throws Exception {
    // for (int i = 0, len = list.size(); i < len; i++) {
    // String sql = (String) list.get(i);
    // sql = StringUtil.simpleReplace(sql, "tableName", tableName);
    // logger.info(sql);
    // stat.execute(sql);
    // }
    // return null;
    // }
    // }, false);
    // logger.info("Create table {} success. ", tableName);
    // } else {
    // logger.info("Table {} exists, not create. ", tableName);
    // }
    // }

    public void createFileTable(String schema) {
        final String tableName = tlocator.fileTable(schema);
        final List list = readAllStatement("/dfs-sql-file.sql");
        if (!existsTable(tableName)) {
            template.doInStatement(new StatementAction() {
                public Object excute(Statement stat) throws Exception {
                    for (int i = 0, len = list.size(); i < len; i++) {
                        String sql = (String) list.get(i);
                        sql = StringUtil.simpleReplace(sql, "tableName", tableName);
                        logger.info(sql);
                        stat.execute(sql);
                    }
                    return null;
                }
            }, false);
            logger.info("Create table {} success. ", tableName);
        } else {
            logger.info("Table {} exists, not create. ", tableName);
        }
    }

    public void createSchemaTable() {
        final List list = readAllStatement("/dfs-sql-ns.sql");
        if (!existsTable("TBL_DFS_NAMESPACE")) {
            template.doInStatement(new StatementAction() {
                public Object excute(Statement stat) throws Exception {
                    for (int i = 0, len = list.size(); i < len; i++) {
                        String sql = (String) list.get(i);
                        logger.info(sql);
                        stat.execute(sql);
                    }
                    return null;
                }
            }, false);
            logger.info("Create table {} success. ", "TBL_DFS_NAMESPACE");
        } else {
            logger.info("Table {} exists, not create. ", "TBL_DFS_NAMESPACE");
        }
    }

    private List readAllStatement(String fileName) {
        List list = new ArrayList();
        InputStream input = TableManager.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        StringBuffer sb = new StringBuffer();
        try {
            String line = reader.readLine();
            while (line != null) {
                String gline = line.trim();
                if (gline.endsWith(";")) {
                    if (gline.length() > 1) {
                        sb.append(gline.substring(0, gline.length() - 1));
                    }
                    if (sb.length() > 0) {
                        list.add(sb.toString());
                    }
                    sb = new StringBuffer();
                } else {
                    sb.append(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to read sql from file!", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                reader = null;
                logger.error("Close stream reader error!", e);
            }
        }
        return list;
    }
}
