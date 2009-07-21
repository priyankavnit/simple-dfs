package org.agile.dfs.config;

import java.net.URL;

import org.agile.dfs.util.PropertiesReader;
import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration extends PropertiesReader {
    private final static Logger logger = LoggerFactory.getLogger(Configuration.class);

    private static String config;
    private static boolean flag = false;

    static {
        URL userCfg = Configuration.class.getResource("/dfs.properties");
        if (userCfg == null) {
            config = "/dfs-default.properties";
        } else {
            config = "/dfs.properties";
        }
    }

    public static void init() {
        if (!flag) {
            String home = System.getProperty("user.dir");
            logger.info("Set dfs home at {} ", home);
            System.setProperty("DFS_HOME", home);
            flag = true;
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return getProperty(config, key, defaultValue);
    }

    public static int getPropertyForInt(String key, int defaultValue) {
        return getPropertyForInt(config, key, defaultValue);
    }

    public static boolean getPropertyForBoolean(String key, boolean defaultValue) {
        return getPropertyForBoolean(config, key, defaultValue);
    }

    public static String getDfsHome() {
        String home = System.getProperty("DFS_HOME");
        if (StringUtil.isBlank(home)) {
            home = System.getProperty("user.dir");
            logger.warn("DFS_HOME is null, use 'user.dir' property as dfs home.");
        }
        return home;

    }
}
