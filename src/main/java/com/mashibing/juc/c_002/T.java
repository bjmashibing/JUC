/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */

package com.mashibing.juc.c_002;

public class T {
	
	private int count = 10;
	
	public void m() {
		synchronized(this) { //任何线程要执行下面的代码，必须先拿到this的锁
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

