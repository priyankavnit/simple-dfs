package org.agile.dfs.rpc.serialize;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.rpc.exception.ReflectOperateException;
import org.agile.dfs.rpc.util.ArrayHelper;

@SuppressWarnings("unchecked")
public class ObjectReflectHelper {

    private static final Map<String, ObjectReflectHelper> cache = new HashMap<String, ObjectReflectHelper>();

    private Class clazz;
    private char[] clzNameChars;
    private Field[] fields;

    private ObjectReflectHelper(Class clz) {
        this.clazz = clz;
        clzNameChars = clz.getName().toCharArray();
        fields = clz.getDeclaredFields();
        ArrayHelper<Field> as = new ArrayHelper<Field>();
        for (int i = 0, l = fields.length; i < l; i++) {
            if (Modifier.isFinal(fields[i].getModifiers())) {
                continue;
            }
            fields[i].setAccessible(true);
            as.add(fields[i]);
        }
        fields = as.array();
    }

    public static final ObjectReflectHelper instance(Object obj) {
        Class clz = obj.getClass();
        String name = clz.getName();
        ObjectReflectHelper helper = cache.get(name);
        if (helper == null) {
            helper = new ObjectReflectHelper(clz);
            cache.put(name, helper);
        }
        return helper;
    }

    public static final ObjectReflectHelper instance(String clzName) {
        ObjectReflectHelper helper = cache.get(clzName);
        try {
            Class clz = Class.forName(clzName);
            if (helper == null) {
                helper = new ObjectReflectHelper(clz);
                cache.put(clzName, helper);
            }
        } catch (ClassNotFoundException e) {
            throw new ReflectOperateException(e);
        }
        return helper;
    }

    public char[] getClassNameChars() {
        return clzNameChars;
    }

    public Field[] getTotalFields() {
        return fields;
    }

    public void setValue(Object obj, String field, Object value) {
        for (int i = 0, l = fields.length; i < l; i++) {
            if (field.equals(fields[i].getName())) {
                try {
                    fields[i].set(obj, value);
                } catch (Exception e) {
                    throw new ReflectOperateException("Fail to set value for " + clazz.getName(), e);
                }
                break;
            }
        }
    }

    public Object getValue(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (Exception e) {
            throw new ReflectOperateException("Fail to get value from " + field.getName(), e);
        }
    }

    public Object buildObject() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ReflectOperateException("Fail to build object for " + clazz.getName(), e);
        }
    }
}
