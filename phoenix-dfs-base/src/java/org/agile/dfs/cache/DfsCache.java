package org.agile.dfs.cache;

import org.agile.dfs.cache.map.LRUMap;

/**
 * Simple lru cache impl by apache's lru map
 */
public class DfsCache {

    private LRUMap map;

    public DfsCache(int capacity) {
        map = new LRUMap(capacity);
    }

    public synchronized void put(String key, Object val) {
        map.put(key, val);
    }

    public synchronized Object get(String key) {
        return map.get(key);
    }

    public synchronized Object del(String key) {
        return map.remove(key);
    }

    public static void main(String[] args) {
        final DfsCache cache = new DfsCache(10000);
        Thread[] ths = new Thread[10];
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread() {
                public void run() {
                    // System.out.println("M:" + Runtime.getRuntime().freeMemory());
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < 100 * 10000; i++) {
                        String k = "k" + i;
                        String v = "v" + i;
                        cache.put(k, v);
                    }
                    long t2 = System.currentTimeMillis();
                    System.out.println("T:P" + (t2 - t1));

                    long t3 = System.currentTimeMillis();
                    for (int i = 0; i < 100 * 10000; i++) {
                        String k = "k" + i;
                        cache.get(k);
                    }
                    long t4 = System.currentTimeMillis();
                    System.out.println("T:G" + (t4 - t3));
                    // System.out.println("M:" + Runtime.getRuntime().freeMemory());
                }
            };
            ths[i].start();
        }
        for (int i = 0; i < ths.length; i++) {
            try {
                ths[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time:" + (t2 - t1));

    }

}
