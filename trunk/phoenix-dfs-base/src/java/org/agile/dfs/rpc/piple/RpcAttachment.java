package org.agile.dfs.rpc.piple;

import java.io.IOException;

import org.agile.dfs.rpc.endpoint.Endpointable;
import org.agile.dfs.util.MulValueThreadLocal;

public class RpcAttachment {
    private static final MulValueThreadLocal local = MulValueThreadLocal.newInstance();
    private Endpointable endpoint;

    /**
     * Max attachement size 1M (1024 * 1024)
     */

    public RpcAttachment() {
        endpoint = (Endpointable) local.get("dfs.endpoint");
    }

    public void write(byte[] b) throws IOException {
        endpoint.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        endpoint.write(b, off, len);
    }

    public void flush() throws IOException {
        endpoint.flush();
    }

    public int read(byte[] b) throws IOException {
        return endpoint.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return endpoint.read(b, off, len);
    }

}
