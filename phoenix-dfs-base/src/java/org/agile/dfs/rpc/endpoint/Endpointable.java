package org.agile.dfs.rpc.endpoint;

import java.io.IOException;

public interface Endpointable {

    public void write(int b) throws IOException;

    public void write(byte b[]) throws IOException;

    public void write(byte b[], int off, int len) throws IOException;

    public void flush() throws IOException;

    public int read() throws IOException;

    public int read(byte b[]) throws IOException;

    public int read(byte b[], int off, int len) throws IOException;

    public void close();

    public boolean isClose();
}
