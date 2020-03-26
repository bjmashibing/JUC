/**
 * volatile 寮曠敤绫诲瀷锛堝寘鎷暟缁勶級鍙兘淇濊瘉寮曠敤鏈韩鐨勫彲瑙佹�э紝涓嶈兘淇濊瘉鍐呴儴瀛楁鐨勫彲瑙佹��
 */
package com.mashibing.juc.c_012_Volatile;

import java.util.concurrent.TimeUnit;

public class T02_VolatileReference1 {

    boolean running = true;

    volatile static T02_VolatileReference1 T = new T02_VolatileReference1();


    void m() {
        System.out.println("m start");
        while(running) {
			/*
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        new Thread(T::m, "t1").start();

        //lambda琛ㄨ揪寮� new Thread(new Runnable( run() {m()}

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        T.running = false;
    }
}
