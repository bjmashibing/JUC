/**
 *
 */
package com.mashibing.juc.c_026_01_ThreadPool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T11_WorkStealingPool {
	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newWorkStealingPool();
		System.out.println(Runtime.getRuntime().availableProcessors());

		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000)); //daemon
		service.execute(new R(2000));
		
		//鐢变簬浜х敓鐨勬槸绮剧伒绾跨▼锛堝畧鎶ょ嚎绋嬨�佸悗鍙扮嚎绋嬶級锛屼富绾跨▼涓嶉樆濉炵殑璇濓紝鐪嬩笉鍒拌緭鍑�
		System.in.read(); 
	}

	static class R implements Runnable {

		int time;

		R(int t) {
			this.time = t;
		}

		@Override
		public void run() {
			
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(time  + " " + Thread.currentThread().getName());
			
		}

	}
}
