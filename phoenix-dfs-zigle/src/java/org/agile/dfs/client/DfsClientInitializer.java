package org.agile.dfs.client;

import org.agile.dfs.client.service.MulticastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsClientInitializer {
    private final static Logger logger = LoggerFactory.getLogger(DfsClientInitializer.class);

    private static boolean initFlag;

    public static void init() {
        if (!initFlag) {
            logger.info("Ready to init dfs envirment...");
            MulticastService.instance().start();
            initFlag = true; 
        }
    }
}
