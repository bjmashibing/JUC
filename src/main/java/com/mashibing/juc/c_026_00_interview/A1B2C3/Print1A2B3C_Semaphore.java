package com.mashibing.juc.c_026_00_interview.A1B2C3;

import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore的方式，它本质上也是用了“许可”的概念。跟使用LockSupport、BlockingQueue完成此功能是相似的。
 * 通常来说Semaphore的用法是：多个线程执行一个被同一个semaphore的 semaphore.acquire()和 semaphore.release() 包围的一段代码以控制同时执行这段代码的线程数量【哪个线程acquire了semaphore的许可，哪个线程就release许可】 
 * 但现在是一个线程中多次执行semaphore.acquire() 导致该semaphore许可用尽而阻塞，然后等待其他的线程中进行该semaphore.release()许可回收。
 */
public class Print1A2B3C_Semaphore {

	public static void main(String[] args) {
		//创建两个Semaphore用于线程间通信
		Semaphore s1 = new Semaphore(1);
		Semaphore s2 = new Semaphore(1);

		//t1线程输出的数字
		char[] aI = "1234567".toCharArray();
		
		//t2线程输出的字母
		char[] aC = "ABCDEFG".toCharArray();
		
		
		Thread t1 = new Thread(() -> {
			for(char c : aI) {
				//t1 直接进入循环，先s1.acquire()，第一次循环时不会阻塞
				try { s1.acquire(); } catch (Exception e) {}
				
				System.out.print(c);
				//t1 输出一个元素后，释放s2，让t2执行
				try { s2.release(); } catch (Exception e) {}
			}
		});
		
		Thread t2 = new Thread(() -> {
			//因为控制t1先输出，所以t2要控制住一开始不能输出，循环执行先s2.acquire()，注意此时不会阻塞
			try { s2.acquire(); } catch (Exception e) {}
			
			//然后进入循环
			for(char c : aC) {
				try { s2.acquire(); } catch (Exception e) {}
				System.out.print(c + " ");
				try { s1.release(); } catch (Exception e) {}
			}
		});
		
		//启动两个线程
		t1.start();
		t2.start();
	}
	
}
