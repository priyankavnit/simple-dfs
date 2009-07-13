package org.agile.dfs.rpc.piple;

import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.rpc.endpoint.Endpointable;

public class RpcCallBase {
    protected static final String PROTOCOL_FLAG = "agile.chen";
    protected Endpointable endpoint;

    public RpcCallBase(Endpointable endpoint) {
        this.endpoint = endpoint;
    }

    public Map readHeader() {
        return new HashMap();
    }
}
