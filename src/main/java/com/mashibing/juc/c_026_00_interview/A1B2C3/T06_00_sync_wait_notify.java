package com.mashibing.juc.c_026_00_interview.A1B2C3;


public class T06_00_sync_wait_notify {
    public static void main(String[] args) {
        final Object o = new Object();

        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(()->{
            synchronized (o) {
                for(char c : aI) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait(); //璁╁嚭閿�
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify(); //蹇呴』锛屽惁鍒欐棤娉曞仠姝㈢▼搴�
            }

        }, "t1").start();

        new Thread(()->{
            synchronized (o) {
                for(char c : aC) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify();
            }
        }, "t2").start();
    }
}

//濡傛灉鎴戞兂淇濊瘉t2鍦╰1涔嬪墠鎵撳嵃锛屼篃灏辨槸璇翠繚璇侀鍏堣緭鍑虹殑鏄疉鑰屼笉鏄�1锛岃繖涓椂鍊欒濡備綍鍋氾紵
