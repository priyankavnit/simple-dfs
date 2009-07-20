package org.agile.dfs.client.config;

import java.net.URL;

public class Configuration extends PropertiesReader {

    private static String config;

    static {
        URL userCfg = Configuration.class.getResource("/dfs.properties");
        if (userCfg == null) {
            config = "/dfs-default.properties";
        } else {
            config = "/dfs.properties";
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
}
