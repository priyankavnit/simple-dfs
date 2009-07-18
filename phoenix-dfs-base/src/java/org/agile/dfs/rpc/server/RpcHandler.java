package org.agile.dfs.rpc.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.piple.RpcCallHelper;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.piple.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);
    private static final RpcCallHelper helper = RpcCallHelper.instance();

    private static final String PROTOCOL_FLAG = "agile.chen";

    public void handle(Endpointable endpoint) throws IOException {
        try {
            RpcRequest req = receive(endpoint);
            if (req == null) {
                RpcResponse resp = new RpcResponse();
                resp.setStatus(RpcResponse.STATUS_EXCEPTION);
                resp.setResult("Can't build request from header!");
                send(endpoint, resp);
                logger.error("Can't build request from header!");
                endpoint.close();
            } else {
                Object result = getRpcInvoker().invoke(req);
                RpcResponse resp = new RpcResponse();
                resp.setStatus(RpcResponse.STATUS_SUCCESS);
                resp.setResult(result);
                send(endpoint, resp);
            }
        } catch (DfsException e) {
            RpcResponse resp = new RpcResponse();
            resp.setStatus(RpcResponse.STATUS_EXCEPTION);
            resp.setResult(e.toString());
            send(endpoint, resp);
            logger.error("Fail to handle " + endpoint, e);
        }
    }

    protected void send(Endpointable endpoint, RpcResponse resp) throws IOException {
        byte[] bs = helper.marshall(resp).getBytes();
        endpoint.write(PROTOCOL_FLAG.getBytes());
        endpoint.write(' ');
        String len = String.valueOf(bs.length);
        endpoint.write(len.getBytes());
        endpoint.write(' ');
        endpoint.write(bs);
        endpoint.flush();
    }

    protected RpcRequest receive(Endpointable endpoint) throws IOException {
        String flag = this.read(endpoint, (byte) ' ');
        String lens = this.read(endpoint, (byte) ' ');
        if (PROTOCOL_FLAG.equals(flag)) {
            int len = Integer.parseInt(lens);
            String body = readFixed(endpoint, len);
            RpcRequest req = helper.buildRequest(body);
            return req;
        } else {
            if (!endpoint.isClose()) {
                logger.error("Protocol header error! " + flag + " " + lens);
            }
        }
        return null;
    }

    private String read(Endpointable endpoint, byte stop) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
        for (int i = 0; i < 1 * 50; i++) {
            int k = endpoint.read();
            if (k == -1) {
                endpoint.close();
            }
            byte b = (byte) k;
            if (b != ' ') {
                bos.write(b);
            } else {
                break;
            }
        }
        return bos.toString();
    }

    private String readFixed(Endpointable endpoint, int len) throws IOException {
        byte[] buf = new byte[len];
        int num = endpoint.read(buf);
        if (num == -1) {
            endpoint.close();
        }
        while (num != -1 && num < len) {
            int tmp = endpoint.read(buf, num, len - num);
            if (tmp != -1) {
                num += tmp;
            } else {
                throw new IOException("Connection reset");
            }
        }
        return num == len ? new String(buf) : null;
    }

    public abstract RpcInvoker getRpcInvoker();
}
