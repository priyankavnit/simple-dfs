package org.agile.dfs.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DfsServerContext {

    private Socket socket;
    private InputStream request;
    private OutputStream response;

    public DfsServerContext(Socket socket) throws IOException {
        this.socket = socket;
        response = new BufferedOutputStream(socket.getOutputStream(), 4 * 1024);
        request = new BufferedInputStream(socket.getInputStream(), 4 * 1024);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getRequest() {
        return request;
    }

    public OutputStream getResponse() {
        return response;
    }

    public void destory() throws IOException {
        request.close();
        // response.flush();
        response.close();
        if (!socket.isClosed()) {
            socket.close();
        }
    }

    // some method can be packaged into request and response
    public void write(String content) throws IOException {
        response.write(content.getBytes());
    }

    public void write(byte[] b) throws IOException {
        response.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        response.write(b, off, len);
    }

    public void flush() throws IOException {
        response.flush();
    }

    public int read(byte[] buf) throws IOException {
        int num = request.read(buf);
        return num;
    }

    public byte[] read() throws IOException {
        int hl = 4 * 1024;
        byte[] sb = new byte[hl];
        int num = request.read(sb);
        if (hl != num) {
            byte[] nb = new byte[num];
            System.arraycopy(sb, 0, nb, 0, num);
            return nb;
        }
        return sb;
    }

    public byte[] read(int num) throws IOException {
        byte[] sb = new byte[num];
        for (int i = 0; i < num; i++) {
            int b = request.read();
            sb[num++] = (byte) b;
        }
        return sb;
    }

    public String readLine() throws IOException {
        int hl = 1 * 1024;
        byte[] sb = new byte[hl];
        int num = 0;
        for (int i = 0; i < hl; i++) {
            int b = request.read();
            if (b == -1 || b == '\n' || b == '\r') {
                break;
            } else {
                sb[num++] = (byte) b;
            }
        }
        return new String(sb, 0, num);
    }
}
