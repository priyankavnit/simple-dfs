package org.agile.dfs.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
 
public class ObjectUtil {
    private static Map<String, Field[]> cache = new HashMap<String, Field[]>();

    private static Field[] fields(Object obj) {
        String name = obj.getClass().getName();
        Field[] fields = cache.get(name);
        if (fields != null) {
            return fields;
        }
        fields = obj.getClass().getDeclaredFields();
        ArrayHelper<Field> as = new ArrayHelper<Field>();
        for (int i = 0, l = fields.length; i < l; i++) {
            if (Modifier.isFinal(fields[i].getModifiers())) {
                continue;
            }
            fields[i].setAccessible(true);
            as.add(fields[i]);
        }
        fields = as.array(Field.class);
        cache.put(name, fields);
        return fields;
    }

    public static Map<String, Object> toMap(Object obj) {
        Field[] fields = fields(obj);
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0, l = fields.length; i < l; i++) {
            String key = fields[i].getName();
            Object val = null;
            try {
                val = fields[i].get(obj);
            } catch (Exception e) {
                throw new RuntimeException("Failt to fetch field to map!", e);
            }
            map.put(key, val);
        }
        return map;
    }

}
