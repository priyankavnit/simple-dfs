package org.agile.dfs.core.common;

import java.util.Hashtable;
import java.util.Map;

public class MultiValueThreadLocal {

    private static Map map = new Hashtable();

    public static void put(String key, Object value) {
        map.put(key, value);
    }

    public static Object get(String key) {
        return map.get(key);
    }
}
