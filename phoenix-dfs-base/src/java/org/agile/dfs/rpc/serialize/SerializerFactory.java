package org.agile.dfs.rpc.serialize;

public class SerializerFactory {
    private static final SerializerFactory _instance = new SerializerFactory();

    private SerializerFactory() {

    }

    public static final SerializerFactory instance() {
        return _instance;
    }

    public RpcSerializer getRpcSerializer() {
        return new ReflectSerializer();
    }

    public RpcDeSerializer getRpcDeSerializer() {
        return new ReflectDeSerializer();
    }
}
