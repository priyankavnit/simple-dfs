package org.agile.dfs.rpc.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.piple.RpcCallHelper;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.piple.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);
    private static final RpcCallHelper helper = RpcCallHelper.instance();
    private static final ReflectInvoker invoker = new ReflectInvoker();
    private static final String PROTOCOL_FLAG = "agile.chen";

    public RpcHandler() {
    }

    public void handle(Endpointable endpoint) throws IOException {
        RpcRequest req = receive(endpoint);
        if (req == null) {
            // TODO impl, notice client
            endpoint.close();
        } else {
            Object result = invoker.invoke(req);
            RpcResponse resp = new RpcResponse();
            resp.setResult(result);
            send(endpoint, resp);
        }
    }

    private void send(Endpointable endpoint, RpcResponse resp) throws IOException {
        byte[] bs = helper.marshall(resp).getBytes();
        endpoint.write(PROTOCOL_FLAG.getBytes());
        endpoint.write(' ');
        String len = String.valueOf(bs.length);
        endpoint.write(len.getBytes());
        endpoint.write(' ');
        endpoint.write(bs);
        endpoint.flush();
    }

    private RpcRequest receive(Endpointable endpoint) throws IOException {
        String flag = this.read(endpoint, (byte) ' ');
        String lens = this.read(endpoint, (byte) ' ');
        if (PROTOCOL_FLAG.equals(flag)) {
            int len = Integer.parseInt(lens);
            String body = readFixed(endpoint, len);
            RpcRequest req = helper.buildRequest(body);
            return req;
        } else {
            logger.error("Protocol header error! " + flag + " " + lens);
        }
        return null;
    }

    private String read(Endpointable endpoint, byte stop) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
        for (int i = 0; i < 1 * 50; i++) {
            int k = endpoint.read();
            if (k == -1) {
                throw new IOException("Connection reset");
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
            throw new IOException("Connection reset");
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
}
