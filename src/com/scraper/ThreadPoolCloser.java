package com.scraper;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadPoolCloser implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(ThreadPoolCloser.class.getName());
    private final ThreadPoolExecutor pool;
    private final long time;

    public ThreadPoolCloser(ThreadPoolExecutor pool, long time) {
        this.pool = pool;
        this.time = time;
    }

    @Override
    public void run() {
        while(pool.getActiveCount() > 1) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                LOGGER.log(Level.SEVERE, "ThreadPoolCloser");
            }
        }
        pool.shutdown();
        System.out.println("Completed in (seconds): " + (System.currentTimeMillis()-time ) / 1000);
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch(InterruptedException e) {
            LOGGER.log(Level.SEVERE, "ThreadPoolCloser");
        }
    }
}
