package org.agile.dfs.util;

import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unchecked")
public class MulValueThreadLocal {

    private final static MulValueThreadLocal instance = new MulValueThreadLocal();
    private final static ThreadLocal local = new ThreadLocal();

    private MulValueThreadLocal() {

    }

    /* use map, be care memory leak */
    public static MulValueThreadLocal newInstance() {
        return instance;
    }

    public Object get(String key) {
        Map map = getValueContainer();
        return map.get(key);
    }

    public void set(String key, Object value) {
        Map map = getValueContainer();
        map.put(key, value);
    }

    public void clear(String key) {
        Map map = getValueContainer();
        if (key != null) {
            map.remove(key);
        }
    }

    public void clear() {
        local.set(null);
    }

    private Map getValueContainer() {
        Map map = (Map) local.get();
        if (map != null) {
            return map;
        } else {
            map = new HashMap();
            local.set(map);
        }
        return map;
    }
}
