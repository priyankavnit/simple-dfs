package org.agile.dfs.rpc.client;

import java.io.IOException;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.rpc.piple.RpcCallBase;
import org.agile.dfs.rpc.piple.RpcCallHelper;
import org.agile.dfs.rpc.piple.RpcRequest;

public class AsyncCaller extends RpcCallBase {
    // private static final Logger logger = LoggerFactory.getLogger(AsyncCaller.class);
    private static final RpcCallHelper helper = RpcCallHelper.instance();
    private static final String PROTOCOL_FLAG = "agile.chen";

    public AsyncCaller(Endpointable endpoint) {
        super(endpoint);
    }

    public void call(RpcRequest req) throws IOException {
        this.send(req);
    }

    private void send(RpcRequest req) throws IOException {
        byte[] bs = helper.marshall(req).getBytes();
        endpoint.write(PROTOCOL_FLAG.getBytes());
        endpoint.write(' ');
        String len = String.valueOf(bs.length);
        endpoint.write(len.getBytes());
        endpoint.write(' ');
        endpoint.write(bs);
        endpoint.flush();
    }

    // private RpcResponse receive() throws IOException {
    // String flag = this.read((byte) ' ');
    // String lens = this.read((byte) ' ');
    // if (PROTOCOL_FLAG.equals(flag)) {
    // int len = Integer.parseInt(lens);
    // String body = readFixed(len);
    // RpcResponse resp = helper.buildResponse(body);
    // return resp;
    // } else {
    // logger.error("Protocol header error! " + flag + " " + lens);
    // }
    // return null;
    // }

    // private String read(byte stop) throws IOException {
    // ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
    // for (int i = 0; i < 1 * 50; i++) {
    // int k = endpoint.read();
    // if (k == -1) {
    // throw new IOException("Connection reset");
    // }
    // byte b = (byte) k;
    // if (b != ' ') {
    // bos.write(b);
    // } else {
    // break;
    // }
    // }
    // return bos.toString();
    // }
    //
    // private String readFixed(int len) throws IOException {
    // byte[] buf = new byte[len];
    // int num = endpoint.read(buf, 0, len);
    // while (num != -1 && num < len) {
    // int tmp = endpoint.read(buf, num, len - num);
    // if (tmp != -1) {
    // num += tmp;
    // } else {
    // break;
    // }
    // }
    // return num == len ? new String(buf) : null;
    // }
}
