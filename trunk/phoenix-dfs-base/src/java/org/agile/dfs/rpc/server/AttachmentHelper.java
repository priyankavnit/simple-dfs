package org.agile.dfs.rpc.server;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.piple.RpcAttachment;
import org.agile.dfs.util.MulValueThreadLocal;

public class AttachmentHelper {
    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();

    public static RpcAttachment getAttachment() {
        Endpointable endpoint = (Endpointable) local.get("dfs.endpoint");
        return new RpcAttachment(endpoint);
    }
}
