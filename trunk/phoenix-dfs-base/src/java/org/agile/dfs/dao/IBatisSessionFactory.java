package org.agile.dfs.dao;

import java.io.IOException;
import java.io.Reader;

import org.agile.dfs.util.MulValueThreadLocal;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.SqlMapSession;

public class IBatisSessionFactory {
    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();
    private static SqlMapClient client;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("sql-map-config.xml");
            client = SqlMapClientBuilder.buildSqlMapClient(reader);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException when build SqlMapClient instance.", e);
        }
    }

    public IBatisSessionHolder getSqlMapSession() {
        IBatisSessionHolder holder = new IBatisSessionHolder();
        SqlMapSession session = (SqlMapSession) local.get("dfs.SqlMapSession");
        if (session != null) {
            holder.setSession(session);
            holder.setBound(true);
            return holder;
        } else {
            session = client.openSession();
            local.set("dfs.SqlMapSession", session);
            holder.setSession(session);
            holder.setBound(false);
            return holder;
        }
    }
}
