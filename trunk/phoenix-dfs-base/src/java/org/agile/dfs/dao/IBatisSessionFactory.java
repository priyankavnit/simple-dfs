package org.agile.dfs.dao;

import java.io.IOException;
import java.io.Reader;

import org.agile.dfs.util.MulValueThreadLocal;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IBatisSessionFactory {
    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();
    private static final String LOCAL_SESSION_KEY = "dfs.SqlMapSession";
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
        IBatisSessionHolder holder = (IBatisSessionHolder) local.get("dfs.SqlMapSession");
        if (holder != null) {
            holder.setBound(true);
            return holder;
        } else {
            holder = new IBatisSessionHolder();
            holder.setSession(client.openSession());
            holder.setBound(false);
            local.set("dfs.SqlMapSession", holder);
            return holder;
        }
    }

    public void clearSqlMapSession(IBatisSessionHolder holder) {
        IBatisSessionHolder bound = (IBatisSessionHolder) local.get(LOCAL_SESSION_KEY);
        if (bound != null && holder != null && holder.equals(bound)) {
            local.clear(LOCAL_SESSION_KEY);
        }
    }
}
