package org.agile.dfs.rpc.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface RpcSerializer {

    public void write(Object obj, OutputStream out);

    public void write(Object obj, Writer out) throws IOException;

    public String write(Object obj);
}
