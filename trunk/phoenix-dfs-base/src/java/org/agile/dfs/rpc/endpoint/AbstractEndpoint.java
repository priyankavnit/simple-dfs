package org.agile.dfs.rpc.endpoint;

import java.io.IOException;

public class AbstractEndpoint implements Endpointable {

    public boolean isClose() {
        return false;
    }

    public void close() {

    }

    public void flush() throws IOException {

    }

    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] b) throws IOException {
        return 0;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return 0;
    }

    public void write(int b) throws IOException {

    }

    public void write(byte[] b) throws IOException {

    }

    public void write(byte[] b, int off, int len) throws IOException {
    }

}
