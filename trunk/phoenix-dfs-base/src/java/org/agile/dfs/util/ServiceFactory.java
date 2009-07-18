package org.agile.dfs.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private final static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    private static final Map<String, Object> map = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public static synchronized <T> T findService(Class<T> clz) {
        String clzName = clz.getName();
        Object obj = map.get(clzName);
        if (obj == null) {
            try {
                obj = clz.newInstance();
                map.put(clzName, obj);
            } catch (Exception e) {
                logger.error("Fail to find service!", e);
            }
        }
        return (T) obj;
    }
}
