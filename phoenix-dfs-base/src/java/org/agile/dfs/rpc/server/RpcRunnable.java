package org.agile.dfs.rpc.server;

import java.io.IOException;

import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.rpc.endpoint.Endpointable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RpcRunnable.class);
    private RpcHandler handler;
    private Endpointable endpoint;

    public RpcRunnable(Endpointable endpoint, RpcHandler handler) {
        this.endpoint = endpoint;
        this.handler = handler;
    }

    public void run() {
        logger.info("Request from {} ", endpoint);
        // long connection
        while (true) {
            try {
                handler.handle(endpoint);
            } catch (DfsException de) {
                logger.error("Fail to handle  " + endpoint, de);
            } catch (IOException se) {
                String msg = se.getMessage();
                if ("Connection reset".equalsIgnoreCase(msg)) {
                    logger.error("Connection {} is reset!", endpoint);
                } else {
                    logger.error(msg, se);
                }
                break;
            } catch (Throwable t) {
                logger.error("Handle request error!", t);
                break;
            }
            // if connect is closed, break handle loop

        }
        // last clear resource
        endpoint.close();
        logger.info("Close socket  {}", endpoint);
    }
}
