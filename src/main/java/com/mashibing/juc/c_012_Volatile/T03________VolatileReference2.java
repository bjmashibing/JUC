/**
 * volatile 寮曠敤绫诲瀷锛堝寘鎷暟缁勶級鍙兘淇濊瘉寮曠敤鏈韩鐨勫彲瑙佹�э紝涓嶈兘淇濊瘉鍐呴儴瀛楁鐨勫彲瑙佹��
 */
package com.mashibing.juc.c_012_Volatile;

public class T03________VolatileReference2 {

    private static class Data {
        int a, b;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    volatile static Data data;

    public static void main(String[] args) {
        Thread writer = new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                data = new Data(i, i);
            }
        });

        Thread reader = new Thread(()->{
            while (data == null) {}
            int x = data.a;
            int y = data.b;
            if(x != y) {
                System.out.printf("a = %s, b=%s%n", x, y);
            }
        });

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }
}
