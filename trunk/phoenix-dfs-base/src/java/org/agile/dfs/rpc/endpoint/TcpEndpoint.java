package org.agile.dfs.rpc.endpoint;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.agile.dfs.rpc.exception.InvalidServerException;
import org.agile.dfs.rpc.util.SocketBuilder;

public class TcpEndpoint extends AbstractEndpoint {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public TcpEndpoint(String ip, int port) {
        try {
            socket = SocketBuilder.instance().build(ip, port);
            in = new BufferedInputStream(socket.getInputStream(), 32 * 1024);
            out = new BufferedOutputStream(socket.getOutputStream(), 32 * 1024);
        } catch (IOException e) {
            throw new InvalidServerException("Rpc server ip:" + ip + ", port:" + port + " is invalid!", e);
        }
    }

    public TcpEndpoint(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedInputStream(socket.getInputStream(), 32 * 1024);
            out = new BufferedOutputStream(socket.getOutputStream(), 32 * 1024);
        } catch (IOException e) {
            throw new InvalidServerException("Rpc server ip:" + socket.getInetAddress() + ", port:" + socket.getPort()
                    + " is invalid!", e);
        }
    }

    @Override
    public String toString() {
        return "Endpoint " + socket;
    }

    @Override
    public void close() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            // ignore
        }
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        out.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

}
