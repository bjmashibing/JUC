package com.mashibing.juc.c_015;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicVsSync {
    static long count2 = 0L;
    static AtomicLong count = new AtomicLong(0L);

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[500];

        for(int i=0; i<threads.length; i++) {
            threads[i] =
                    new Thread(()-> {
                        count.incrementAndGet();
                    });
        }

        long start = System.currentTimeMillis();

        for(Thread t : threads ) t.start();

        for (Thread t : threads) t.join();

        long end = System.currentTimeMillis();

        TimeUnit.SECONDS.sleep(10);

        System.out.println("Atomic: " + count.get() + " time " + (end-start));
        //-----------------------------------------------------------
        Object lock = new Object();

        for(int i=0; i<threads.length; i++) {
            threads[i] =
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (lock) {
                            count2++;
                        }
                    }
                });
        }

        start = System.currentTimeMillis();

        for(Thread t : threads ) t.start();

        for (Thread t : threads) t.join();

        end = System.currentTimeMillis();


        System.out.println("Sync: " + count2 + " time " + (end-start));

    }

    static void microSleep(int m) {
        try {
            TimeUnit.MICROSECONDS.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
