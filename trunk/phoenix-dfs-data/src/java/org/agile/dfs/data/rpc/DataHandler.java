package org.agile.dfs.data.rpc;

import java.io.IOException;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.piple.RpcResponse;
import org.agile.dfs.rpc.server.ReflectInvoker;
import org.agile.dfs.rpc.server.RpcHandler;
import org.agile.dfs.rpc.server.RpcInvoker;

public class DataHandler extends RpcHandler {

    @Override
    public RpcInvoker getRpcInvoker() {
        return new ReflectInvoker();
    }

    @Override
    public void handle(Endpointable endpoint) throws IOException {
        RpcRequest req = receive(endpoint);
        if (req == null) {
            // TODO impl, notice client
            endpoint.close();
        } else {
            Object result = getRpcInvoker().invoke(req);
            // RpcResponse resp = new RpcResponse();
            // resp.setResult(result);
            // send(endpoint, resp);
        }
    }

}
