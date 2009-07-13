package org.agile.dfs.name.rpc;

import org.agile.dfs.rpc.server.RpcHandler;
import org.agile.dfs.rpc.server.RpcInvoker;
import org.agile.dfs.rpc.server.SpringInvoker;

public class NameHandler extends RpcHandler {

    @Override
    public RpcInvoker getRpcInvoker() {
        return new SpringInvoker();
    }

}
