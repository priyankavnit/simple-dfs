package org.agile.dfs.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {

    private ExecutorService pool;

    public SimpleThreadPool(int maxSize) {
        pool = Executors.newFixedThreadPool(maxSize);
    }

    public void execute(Runnable run) {
        pool.execute(run);
    }
}
