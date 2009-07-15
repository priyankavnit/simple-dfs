package org.agile.dfs.data.service;

import java.io.IOException;

import org.agile.dfs.rpc.piple.RpcAttachment;
import org.agile.dfs.rpc.server.AttachmentHelper;

public class BlockServiceImpl implements BlockService {

    public void write(String blockId, int len) throws IOException {
        String s = readFixed(len);
        System.out.println(s);
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
