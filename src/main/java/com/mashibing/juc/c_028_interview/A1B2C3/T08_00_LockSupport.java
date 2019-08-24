package com.mashibing.juc.c_028_interview.A1B2C3;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.locks.LockSupport;

public class T08_00_LockSupport {


    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws Exception {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {

                for(char c : aI) {
                    System.out.print(c);
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }

        }, "t1");

        t2 = new Thread(() -> {

            for(char c : aC) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }

        }, "t2");

        t1.start();
        t2.start();
    }
}


