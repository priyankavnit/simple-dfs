package org.agile.dfs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesReader {
    private final static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    public static String getProperty(String fileName, String key, String defaultValue) {
        InputStream is = PropertiesReader.class.getResourceAsStream(fileName);
        Properties p = new Properties();
        try {
            if (is != null) {
                p.load(is);
                String val = p.getProperty(key);
                // if (!StringUtil.isBlank(val)) {
                // return mayReplaceBySystemProperty(val);
                // }
                return val;
            }
        } catch (IOException e) {
            logger.error("Load properties {} error! {}", fileName, e);
        }
        return defaultValue;
    }

    // private static String mayReplaceBySystemProperty(String val) {
    // String value = val.trim();
    // StringBuffer newValue = new StringBuffer();
    // StringBuffer kk = new StringBuffer();
    // boolean kf = false;
    // for (int i = 0; i < value.length(); i++) {
    // char c = value.charAt(i);
    // if (c == '{') {
    // kf = true;
    // } else if (c == '}') {
    // kf = false;
    // String sk = kk.toString();
    // String skv = System.getProperty(sk);
    // newValue.append(skv);
    // } else {
    // if (kf) {
    // kk.append(c);
    // } else {
    // newValue.append(c);
    // }
    // }
    // }
    // return newValue.toString();
    // }

    public static int getPropertyForInt(String fileName, String key, int defaultValue) {
        String v = getProperty(fileName, key, null);
        if (v != null) {
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException e) {
                logger.error("Properties format exception!" + key, e);
            }
        }
        return defaultValue;
    }

    public static boolean getPropertyForBoolean(String fileName, String key, boolean defaultValue) {
        String v = getProperty(fileName, key, null);
        if (v != null) {
            Boolean f = Boolean.valueOf(v);
            return f.booleanValue();
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getPropertyByPrefix(String fileName, String prefix) {
        InputStream is = PropertiesReader.class.getResourceAsStream(fileName);
        Properties p = new Properties();
        List<String> list = new ArrayList<String>();
        try {
            if (is != null) {
                p.load(is);
                Iterator keyIt = p.keySet().iterator();
                while (keyIt.hasNext()) {
                    String key = (String) keyIt.next();
                    if (key.startsWith(prefix)) {
                        list.add(p.getProperty(key));
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Load properties {} error! {}", fileName, e);
        }
        return list;
    }
}
