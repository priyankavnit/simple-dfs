package org.agile.dfs;

import org.agile.dfs.core.common.Environment;
import org.agile.dfs.name.NameStarter;

public class DfsServerStarter {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Environment.init();

        // DataStarter ds = new DataStarter();
        NameStarter ns = new NameStarter();
        ns.start();
        Thread.sleep(1 * 1000);
        System.out.println("\n#################################################\n");
        // ds.start();

    }

}
