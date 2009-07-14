package org.agile.dfs.client;

import java.io.IOException;
import java.io.InputStream;

import org.agile.dfs.client.service.NameNodeService;
import org.agile.dfs.core.entity.NodeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsInputStream extends InputStream {
    private static final Logger logger = LoggerFactory.getLogger(DfsInputStream.class);
    private static final NameNodeService nameNodeService = NameNodeService.instance();

    // private FileReadInvoker reader;

    public DfsInputStream(String fileFullName) {
        // this.fileFullName = fileFullName;
        try {
            init();
        } catch (IOException e) {
            logger.error("Init dfs input stream error!", e);
            // throw new DfsIOException("Init dfs input stream error!", e); 
        }
    }

    private void init() throws IOException {
        NodeItem name = nameNodeService.findNameNode();
    }

    public int read() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }
}
