package org.agile.dfs.core.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private final static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    private static final Map map = new HashMap();

    public static synchronized Object findService(Class clz) {
        Object obj = map.get(clz);
        if (obj == null) {
            try {
                obj = clz.newInstance();
            } catch (Exception e) {
                logger.error("Fail to find service!", e);
            }
        }
        return obj;
    }
}
