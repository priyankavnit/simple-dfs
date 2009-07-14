package org.agile.dfs.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.agile.dfs.client.cache.BlockCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsOutputStream extends OutputStream {
    private static final Logger logger = LoggerFactory.getLogger(DfsOutputStream.class);
    private BlockCache cache;

    public DfsOutputStream(String nameSpace, String fileFullPath) throws IOException {
        this(new DfsFile(nameSpace, fileFullPath));
    }

    public DfsOutputStream(DfsFile dfile) throws IOException {
        // TODO l3 , validate file path format
        if (!dfile.exists()) {
            throw new FileNotFoundException(dfile.toString());
        }
        cache = new BlockCache(dfile);
    }

    public void write(int b) throws IOException {
        this.write(new byte[] { (byte) b }, 0, 1);
    }

    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        cache.write(b, off, len);
    }

    /* override parent class method */
    public void close() throws IOException {
        if (cache != null) {
            cache.flush();
            cache.close();
        }
        logger.info("Close dfs output stream.");
    }

    public void flush() throws IOException {
        cache.flush();
    }

    protected void finalize() throws Throwable {
        this.close();
    }
}
