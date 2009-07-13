package org.agile.dfs.rpc.serialize;

import java.util.Date;

import org.agile.dfs.rpc.exception.RpcSerializeException;

public class ObjectSimpleBuilder {

    private static final ObjectSimpleBuilder _instance = new ObjectSimpleBuilder();

    private ObjectSimpleBuilder() {

    }

    public static ObjectSimpleBuilder instance() {
        return _instance;
    }

    // * @see java.lang.Boolean#TYPE
    // * @see java.lang.Character#TYPE
    // * @see java.lang.Byte#TYPE
    // * @see java.lang.Short#TYPE
    // * @see java.lang.Integer#TYPE
    // * @see java.lang.Long#TYPE
    // * @see java.lang.Float#TYPE
    // * @see java.lang.Double#TYPE
    // * @see java.lang.Void#TYPE
    public Object build(String type, String data) {
        if ("string".equals(type)) {
            return data;
        } else if ("date".equals(type)) {
            return new Date(Long.parseLong(data));
        } else if ("null".equals(type)) {
            return null;
        } else if ("int".equals(type)) {
            return Integer.valueOf(data);
        } else if ("long".equals(type)) {
            return Long.valueOf(data);
        } else if ("boolean".equals(type)) {
            return Boolean.parseBoolean(data);
        } else if ("char".equals(type)) {
            return data.charAt(0);
        } else if ("byte".equals(type)) {
            return Byte.valueOf(data);
        } else if ("short".equals(type)) {
            return Short.valueOf(data);
        } else if ("float".equals(type)) {
            return Float.valueOf(data);
        } else if ("double".equals(type)) {
            return Double.valueOf(data);
        } else {
            throw new RpcSerializeException("Can't build " + type);
        }

    }
}
