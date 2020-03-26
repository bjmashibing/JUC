/**
 * 浣跨敤future杩涜寮傛缂栫▼
 * 缂虹偣锛�
 * 涓嶇煡閬撲綍鏃剁粨鏉�
 * 闃诲鑾峰彇缁撴灉
 */

package com.mashibing.juc.c_027_future_to_loom;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class T01_Future {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(()->{
            return 8;
        });

        int i = future.get();

        System.out.println(i);
    }

}

