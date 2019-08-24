/**
 * 线程安全的单例模式：
 * 
 * 阅读文章：http://www.cnblogs.com/xudong-bupt/p/3433643.html
 * 
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 * 
 * @author 马士兵
 */
package com.mashibing.juc.c_023;

import java.util.Arrays;

public class Singleton {
	
	private Singleton() {
		System.out.println("single");
	}
	
	private static class Inner {
		private static Singleton s = new Singleton();
	}
	
	public static Singleton getSingle() {
		return Inner.s;
	}
	
	public static void main(String[] args) {
		Thread[] ths = new Thread[200];
		for(int i=0; i<ths.length; i++) {
			ths[i] = new Thread(()->{
				System.out.println(Singleton.getSingle());
			});
		}
		
		Arrays.asList(ths).forEach(o->o.start());
	}
	
}
