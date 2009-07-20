package org.agile.dfs.data.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.agile.dfs.data.config.DataNodeConfiguration;
import org.agile.dfs.rpc.piple.RpcAttachment;
import org.agile.dfs.rpc.server.AttachmentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    private static final DataNodeConfiguration config = new DataNodeConfiguration();

    public void write(String blockId, int len) throws IOException { 
        File file = new File(config.dataStorePath() + "/" + blockId);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream stream = new FileOutputStream(file,true);

        RpcAttachment attachment = AttachmentHelper.getAttachment();
        try {
            byte[] buf = new byte[len];
            int num = attachment.read(buf);
            while (num != -1 && num < len) {
                int tmp = attachment.read(buf, num, len - num);
                if (tmp != -1) {
                    num += tmp;
                } else {
                    // throw new IOException("Connection is closed!");
                    break;
                }
            }
            if (num != len) {
                logger.error("Read size:" + num + ", but request size:" + len);
            } else {
                stream.write(buf);
            }
        } finally {
            stream.close();
            stream = null;
        }
    }

    
    
    private String readFixed(int len) throws IOException {
        RpcAttachment attachment = AttachmentHelper.getAttachment();
        byte[] buf = new byte[len];
        int num = attachment.read(buf);
        if (num == -1) {
            // throw new IOException("Connection is closed!");
        }
        while (num != -1 && num < len) {
            int tmp = attachment.read(buf, num, len - num);
            if (tmp != -1) {
                num += tmp;
            } else {
                throw new IOException("Connection is closed!");
            }
        }
        return num == len ? new String(buf) : null;
    }
}
