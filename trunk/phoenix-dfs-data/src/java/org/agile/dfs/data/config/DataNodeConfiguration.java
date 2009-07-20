package org.agile.dfs.data.config;

import org.agile.dfs.util.PropertiesReader;
import org.agile.dfs.util.StringUtil;

public class DataNodeConfiguration {

    private static String dataStorePath = null;

    public String dataStorePath() {
        if (dataStorePath == null) {
            String path = PropertiesReader.getProperty("/dfs-default.properties", "data.store", null);
            if (StringUtil.isBlank(path)) {
                dataStorePath = System.getProperty("user.dir") + "/store";
            } else {
                dataStorePath = path;
            }
        }
        return dataStorePath;
    }
}
