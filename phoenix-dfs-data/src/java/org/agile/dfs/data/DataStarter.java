package org.agile.dfs.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.agile.dfs.config.Configuration;
import org.agile.dfs.core.common.UuidHexGenerator;
import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.data.rpc.DataServer;
import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStarter extends Thread {
    private final Logger logger = LoggerFactory.getLogger(DataStarter.class);
    private static final UuidHexGenerator generator = new UuidHexGenerator();
    private DataServer dataServer;
    private DataHeartBeat heartBeat;

    public DataStarter() {
        dataServer = new DataServer();
        heartBeat = new DataHeartBeat(dataServer);
        init();
    }

    private void init() {
        String home = Configuration.getDfsHome();
        String idPath = home + "/config/id.data";
        File idFile = new File(idPath);
        boolean idFlag = false;
        // care resource leak
        if (idFile.exists()) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(idFile));
                String id = p.getProperty("id");
                if (!StringUtil.isBlank(id)) {
                    dataServer.self().setId(id);
                    idFlag = true;
                }
            } catch (IOException e) {
                throw new DfsException("Can't find id from file! " + idPath);
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
                throw new DfsException("Can't store id to  file! " + idPath);
            }
            dataServer.self().setId(id);
        }
    }

    public void run() {
        logger.info("Run data node starter ...... ");
        // System.out.println("------ -------------------------------------------------- ");
        heartBeat.start();
        dataServer.startup();
    }

    public static void main(String[] args) {
        Configuration.init();
        DataStarter starter = new DataStarter();
        starter.start();

    }
}
