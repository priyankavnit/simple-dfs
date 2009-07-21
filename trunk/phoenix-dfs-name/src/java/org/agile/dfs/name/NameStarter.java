package org.agile.dfs.name;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.agile.dfs.config.Configuration;
import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.name.exception.NameNodeException;
import org.agile.dfs.name.listener.DataNodeListener;
import org.agile.dfs.name.rpc.NameServer;
import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameStarter extends Thread {
    private final Logger logger = LoggerFactory.getLogger(NameStarter.class);
    private static final UuidHexGenerator generator = new UuidHexGenerator();
    private NameServer nameServer;
    private NameHearter heartBeat;
    private DataNodeListener dataNodeListener;

    public NameStarter() {
        nameServer = new NameServer();
        heartBeat = new NameHearter(nameServer);
        dataNodeListener = DataNodeListener.instance();
        init();
    }

    private void init() {
        String home = Configuration.getDfsHome();
        String idPath = home + "/config/id.name";
        File idFile = new File(idPath);
        boolean idFlag = false;
        // care resource leak
        if (idFile.exists()) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(idFile));
                String id = p.getProperty("id");
                if (!StringUtil.isBlank(id)) {
                    nameServer.self().setId(id);
                    idFlag = true;
                }
            } catch (IOException e) {
                throw new NameNodeException("Can't find id from file! " + idPath);
            }
        }
        if (!idFlag) {
            String id = generator.generate();
            Properties p = new Properties();
            p.setProperty("id", id);
            try {
                logger.info("Generate node's id {} into {} . ", id, idPath);
                idFile.getParentFile().mkdirs();
                idFile.createNewFile();
                p.store(new FileOutputStream(idFile), "agile.chen@gmail.com");
            } catch (IOException e) {
                throw new NameNodeException("Can't store id to  file! " + idPath);
            }
            nameServer.self().setId(id);
        }
    }

    public void run() {
        logger.info("Run name node starter ...... "); 
        heartBeat.start();
        dataNodeListener.start();
        nameServer.startup();
    }

    public static void main(String[] args) {
        Configuration.init();
        NameStarter starter = new NameStarter();
        starter.start();

    }
}
