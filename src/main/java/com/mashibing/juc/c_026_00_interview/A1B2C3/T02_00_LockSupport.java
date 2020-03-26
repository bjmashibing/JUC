package com.mashibing.juc.c_026_00_interview.A1B2C3;


import java.util.concurrent.locks.LockSupport;

//Locksupport park 褰撳墠绾跨▼闃诲锛堝仠姝級
//unpark(Thread t)

public class T02_00_LockSupport {


    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws Exception {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {

                for(char c : aI) {
                    System.out.print(c);
                    LockSupport.unpark(t2); //鍙啋T2
                    LockSupport.park(); //T1闃诲
                }

        }, "t1");

        t2 = new Thread(() -> {

            for(char c : aC) {
                LockSupport.park(); //t2闃诲
                System.out.print(c);
                LockSupport.unpark(t1); //鍙啋t1
            }

        }, "t2");

        t1.start();
        t2.start();
    }
}


