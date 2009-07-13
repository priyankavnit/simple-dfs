package org.agile.dfs.rpc.endpoint;

import java.io.IOException;

public interface Endpointable {

    // public void write(byte c) throws IOException;
    //
    // public void write(char[] cbuf, int offset, int len) throws IOException;
    //
    // public void flush() throws IOException;
    //
    // public char read() throws IOException;
    //
    // public int read(char[] cbuf, int offset, int len) throws IOException;

    public void write(int b) throws IOException;

    public void write(byte b[]) throws IOException;

    public void write(byte b[], int off, int len) throws IOException;

    public void flush() throws IOException;

    public int read() throws IOException;

    public int read(byte b[]) throws IOException;

    public int read(byte b[], int off, int len) throws IOException;

    public void close();
}
