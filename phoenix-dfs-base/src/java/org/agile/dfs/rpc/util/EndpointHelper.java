package org.agile.dfs.rpc.util;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.util.MulValueThreadLocal;

public class EndpointHelper {

    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();

    public static Endpointable current() {

        return (Endpointable) local.get("dfs.endpoint");
    }
}
