package org.agile.dfs.client.service;

import org.agile.dfs.client.listener.NameNodeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsInitializer {
    private final static Logger logger = LoggerFactory.getLogger(DfsInitializer.class);

    private static boolean initFlag;

    public static void init() {
        if (!initFlag) {
            logger.info("Ready to init dfs envirment...");
            NameNodeListener.instance().start();
            initFlag = true; 
        }
    }
}
