/**
 * Ô´ÂëÔÄ¶Á
 * https://www.cnblogs.com/fixzd/p/9253203.html
 */
package com.mashibing.juc.c_026_01_ThreadPool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T13_ThreadPoolExecutor {
	public static void main(String[] args) {
		ExecutorService tpe = Executors.newCachedThreadPool();
	}
}
