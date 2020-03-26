package com.mashibing.juc.c_000;

public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println("Hello Lambda!");
        }).start();
    }

}

//璇蜂綘鍛婅瘔鎴戝惎鍔ㄧ嚎绋嬬殑涓夌鏂瑰紡 1锛歍hread 2: Runnable 3:Executors.newCachedThrad
