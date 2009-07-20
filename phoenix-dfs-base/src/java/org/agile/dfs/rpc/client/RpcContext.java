package org.agile.dfs.rpc.client;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.util.MulValueLocal;

public class RpcContext {

    private static final MulValueLocal local = MulValueLocal.newInstance();

    public static Endpointable current() {

        return (Endpointable) local.get("dfs.endpoint");
    }
}
