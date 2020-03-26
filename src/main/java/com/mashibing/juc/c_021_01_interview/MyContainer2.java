/**
 * 闈㈣瘯棰橈細鍐欎竴涓浐瀹氬閲忓悓姝ュ鍣紝鎷ユ湁put鍜実et鏂规硶锛屼互鍙奼etCount鏂规硶锛�
 * 鑳藉鏀寔2涓敓浜ц�呯嚎绋嬩互鍙�10涓秷璐硅�呯嚎绋嬬殑闃诲璋冪敤
 * 
 * 浣跨敤wait鍜宯otify/notifyAll鏉ュ疄鐜�
 * 
 * 浣跨敤Lock鍜孋ondition鏉ュ疄鐜�
 * 瀵规瘮涓ょ鏂瑰紡锛孋ondition鐨勬柟寮忓彲浠ユ洿鍔犵簿纭殑鎸囧畾鍝簺绾跨▼琚敜閱�
 * 
 * @author mashibing
 */
package com.mashibing.juc.c_021_01_interview;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyContainer2<T> {
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10; //鏈�澶�10涓厓绱�
	private int count = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();
	
	public void put(T t) {
		try {
			lock.lock();
			while(lists.size() == MAX) { //鎯虫兂涓轰粈涔堢敤while鑰屼笉鏄敤if锛�
				producer.await();
			}
			
			lists.add(t);
			++count;
			consumer.signalAll(); //閫氱煡娑堣垂鑰呯嚎绋嬭繘琛屾秷璐�
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public T get() {
		T t = null;
		try {
			lock.lock();
			while(lists.size() == 0) {
				consumer.await();
			}
			t = lists.removeFirst();
			count --;
			producer.signalAll(); //閫氱煡鐢熶骇鑰呰繘琛岀敓浜�
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return t;
	}
	
	public static void main(String[] args) {
		MyContainer2<String> c = new MyContainer2<>();
		//鍚姩娑堣垂鑰呯嚎绋�
		for(int i=0; i<10; i++) {
			new Thread(()->{
				for(int j=0; j<5; j++) System.out.println(c.get());
			}, "c" + i).start();
		}
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//鍚姩鐢熶骇鑰呯嚎绋�
		for(int i=0; i<2; i++) {
			new Thread(()->{
				for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}
	}
}
