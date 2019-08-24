package com.mashibing.juc.c_012;

import java.util.concurrent.TimeUnit;

public class TestVolatile {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends  Thread {
        @Override
        public void run() {
            while(!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
