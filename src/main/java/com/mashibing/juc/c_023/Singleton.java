/**
 * 绾跨▼瀹夊叏鐨勫崟渚嬫ā寮忥細
 * 
 * 闃呰鏂囩珷锛歨ttp://www.cnblogs.com/xudong-bupt/p/3433643.html
 * 
 * 鏇村ソ鐨勬槸閲囩敤涓嬮潰鐨勬柟寮忥紝鏃笉鐢ㄥ姞閿侊紝涔熻兘瀹炵幇鎳掑姞杞�
 * 
 * @author 椹＋鍏�
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
