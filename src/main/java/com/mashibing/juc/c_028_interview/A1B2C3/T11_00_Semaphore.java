package com.mashibing.juc.c_028_interview.A1B2C3;

/**
 * 解法不好，都是黑马王子说的，哈哈哈
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Semaphore;

public class T11_00_Semaphore {
    public static void main(String[] args) throws Exception {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        Semaphore semaphore = new Semaphore(1, true);

        new Thread(() -> {

                for(char c : aI) {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(c);
                    semaphore.release();
                }

        }, "t1").start();

        new Thread(() -> {
            for(char c : aC) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(c);
                semaphore.release();
            }
        }, "t2").start();
    }
}


