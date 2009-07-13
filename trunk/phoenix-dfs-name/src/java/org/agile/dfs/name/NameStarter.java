package org.agile.dfs.name;

import org.agile.dfs.core.common.Environment;
import org.agile.dfs.name.rpc.NameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameStarter extends Thread {
    private final Logger logger = LoggerFactory.getLogger(NameStarter.class);

    private NameServer nameServer;
    private NameHeartBeat heartBeat;

    public NameStarter() {
        nameServer = new NameServer();
        heartBeat = new NameHeartBeat(nameServer);
    }

    public void run() {
        logger.info("Run name node starter ...... ");
        // System.out.println("------ -------------------------------------------------- ");
        heartBeat.start();
        nameServer.startup();
    }

    public static void main(String[] args) {
        Environment.init();
        NameStarter starter = new NameStarter();
        starter.start();

        // while (true) {
        // try {
        // Thread.sleep(60 * 1000);
        // } catch (InterruptedException e) {
        // // do nothing
        // }
        // }
    }
}
