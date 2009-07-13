package org.agile.dfs.core.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.entity.NodeItem;

public class SocketProcessor {
    // private static ObjectXmlMapper mapper = new ObjectXmlMapper();
    public static final char PT_STOP_CHAR = '\0';
    public static final int PT_STOP_NUM = 4;
    private Socket socket;
    // private OutputStream output;
    // private InputStream input;
    private RpcContext context;

    public SocketProcessor(NodeItem target) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(target.getIp(), target.getPort()), 2 * 1000);
        socket.setSoTimeout(5 * 1000);
        // output = new BufferedOutputStream(socket.getOutputStream(), 4 *
        // 1024);
        // input = new BufferedInputStream(socket.getInputStream(), 4 * 1024);
        context = new RpcContext(socket);
    }

    public String call(DfsBaseAction action) throws IOException {
        this.send(action);
        String resp = this.receive();
        return resp;
    }

    public void send(DfsBaseAction action) throws IOException {
        // String xml = mapper.objectToXML(action);
        // output.write(action.toString());
        context.write(action.toString());
        for (int i = 0; i < PT_STOP_NUM; i++) {
            context.write("\n");
        }
        context.flush();
    }

    public String receive() throws IOException {
        // String xml = null;// this.read();
        // if (xml != null) {
        // Object obj = mapper.xmlToObject(xml);
        // if (obj != null && obj instanceof DfsException) {

        // }
        // }
        // return null;

        int hl = 1 * 1024;
        byte[] sb = new byte[hl];
        int num = 0;
        int stopNum = 0;
        for (int i = 0; i < hl; i++) {
            int b = context.read();
            // stream closed
            if (b == -1) {
                context.close();
                break;
            }
            // protocol end
            if (b == PT_STOP_CHAR) {
                stopNum++;
                if (stopNum >= PT_STOP_NUM) {
                    break;
                }
            }
            sb[num++] = (byte) b;
        }
        return new String(sb, 0, num);
    }

    // private String read() throws IOException {
    // BufferedReader reader = new BufferedReader(new InputStreamReader(input,
    // "UTF-8"));
    // StringBuffer data = new StringBuffer(1024);
    // int c = 0;
    // int n = 0;
    // while ((c = reader.read()) != -1) {
    // if (c != '\0') {
    // data.append((char) c);
    // n++;
    // if (n > 8096) {
    // throw new
    // IOException("Read bytes over 8K, but not include \\0 ,  please close connection! ");
    // }
    // } else {
    // break;
    // }
    // }
    // return data.toString();
    // }

    public void write(byte[] b, int off, int len) throws IOException {
        // output.write(b, off, len);
    }

    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (Throwable t) {
                socket = null;
            }
        }

    }

    protected void finalize() throws Throwable {
        close();
    }
}
