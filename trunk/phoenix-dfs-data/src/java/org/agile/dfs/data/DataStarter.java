package org.agile.dfs.data;

import org.agile.dfs.core.common.Environment;
import org.agile.dfs.data.rpc.DataServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStarter extends Thread {
    private final Logger logger = LoggerFactory.getLogger(DataStarter.class);

    private DataServer dataServer;
    private DataHeartBeat heartBeat;

    public DataStarter() {
        dataServer = new DataServer();
        heartBeat = new DataHeartBeat(dataServer);
    }

    public void run() {
        logger.info("Run data node starter ...... ");
        // System.out.println("------ -------------------------------------------------- ");
        heartBeat.start();
        dataServer.startup();
    }

    public static void main(String[] args) {
        Environment.init();
        DataStarter starter = new DataStarter();
        starter.start();
 
    }
}
