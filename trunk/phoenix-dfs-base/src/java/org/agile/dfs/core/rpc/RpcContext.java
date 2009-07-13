package org.agile.dfs.core.rpc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.agile.dfs.core.action.DfsBaseAction;
import org.agile.dfs.core.exception.DfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcContext {
    private final static Logger logger = LoggerFactory.getLogger(RpcContext.class);

    public static final char PT_STOP_CHAR = '\0';
    public static final int PT_STOP_NUM = 4;
    public static final String RESP_SUCCESS = "suc";
    public static final String RESP_EXCEPTION = "exp";
    public static final String RESP_SPLIT = ":";

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public RpcContext(Socket socket) throws IOException {
        this.socket = socket;
        // default use 16k in/out stream cache
        // TODO impl custom BufferedOutputStream, do more helper
        output = new BufferedOutputStream(socket.getOutputStream(), 32 * 1024);
        input = new BufferedInputStream(socket.getInputStream(), 32 * 1024);
    }

    public String call(DfsBaseAction action) {
        try {
            this.write(action.toString());
            writeProtocolEnd();
            String r = this.receive();
            if (r.startsWith(RESP_SUCCESS)) {
                return r.substring(4);
            } else if (r.startsWith(RESP_EXCEPTION)) {
                throw new DfsException(r.substring(4));
            } else {
                throw new DfsException("Unknown node response :  " + r);
            }
        } catch (IOException e) {
            throw new RuntimeException("Dfs rpc call exception! ", e);
        }
    }

    public String receive() throws IOException {
        int hl = 1 * 1024;
        byte[] sb = new byte[hl];
        int num = 0;
        int stopNum = 0;
        boolean ef = false;
        boolean pf = false;
        for (int i = 0; i < hl; i++) {
            int b = input.read();
            // stream closed
            if (b == -1) {
                this.close();
                ef = true;
                break;
            }

            sb[num++] = (byte) b;
            // protocol end
            if (b == PT_STOP_CHAR) {
                stopNum++;
                if (stopNum >= PT_STOP_NUM) {
                    pf = true;
                    break;
                }
            }
        }
        if (pf) {
            String r = new String(sb, 0, num - PT_STOP_NUM);
            logger.debug("in : {}", r);
            return r;
        } else if (ef) {
            String r = new String(sb, 0, num);
            logger.debug("in : {}", r);
            return r;
        } else {
            throw new DfsException("Received 1k bytes,not include protocol end (\0)!");
        }
    }

    public void success(String msg) {
        try {
            this.write(RESP_SUCCESS);
            this.write(RESP_SPLIT);
            this.write(msg);
            writeProtocolEnd();
        } catch (IOException e) {
            logger.error("Response success msg error!", e);
        }
    }

    public void exception(String msg) {
        try {
            this.write(RESP_EXCEPTION);
            this.write(RESP_SPLIT);
            this.write(msg);
            writeProtocolEnd();
        } catch (IOException e) {
            if (!this.isClosed()) {
                logger.error("Response exception msg error!", e);
            }
        }
    }

    private void writeProtocolEnd() throws IOException {
        for (int i = 0; i < PT_STOP_NUM; i++) {
            output.write(PT_STOP_CHAR);
        }
        output.flush();
    }

    /* getter and setter method */
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInput() {
        return input;
    }

    public OutputStream getOutput() {
        return output;
    }

    /* some helper method */
    public void destory() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            socket = null;
            logger.error("Fail to colse  socket " + socket, e);
        }
    }

    public void close() {
        this.destory();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public boolean isInputClosed() {
        return socket.isInputShutdown();
    }

    public boolean isOutputClosed() {
        return socket.isOutputShutdown();
    }

    public InetAddress getRemoteAddress() {
        return socket.getInetAddress();
    }

    // some method can be packaged into request and response
    public void write(String c) throws IOException {
        logger.debug("out: {}", c);
        output.write(c.getBytes());
    }

    public void write(byte[] b) throws IOException {
        output.write(b);
    }

    public void write(int b) throws IOException {
        output.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        output.write(b, off, len);
    }

    public void flush() throws IOException {
        output.flush();
    }

    public byte[] read(int num) throws IOException {
        byte[] sb = new byte[num];
        for (int i = 0; i < num; i++) {
            int b = input.read();
            sb[num++] = (byte) b;
        }
        return sb;
    }

    public int read() throws IOException {
        int b = input.read();
        return b;
    }
}
