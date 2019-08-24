/**
 * 对比上面一个小程序，分析一下这个程序的输出
 * @author mashibing
 */

package com.mashibing.juc.c_006;

public class T implements Runnable {

	private int count = 10;
	
	public synchronized void run() { 
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		
		for(int i=0; i<5; i++) {
			T t = new T();
			new Thread(t, "THREAD" + i).start();
		}
	}
	
}
