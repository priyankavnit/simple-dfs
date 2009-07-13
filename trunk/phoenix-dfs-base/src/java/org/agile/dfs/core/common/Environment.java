package org.agile.dfs.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment {
    private static final Logger logger = LoggerFactory.getLogger(Environment.class);
    private static boolean flag = false;

    public static void init() {
        if (!flag) {
            String home = System.getProperty("user.dir");
            logger.info("Set dfs home at {} ", home);
            System.setProperty("DFS_HOME", home);
            flag = true;
        }
    }
}
