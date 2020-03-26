/**
 * synchronized浼樺寲
 * 鍚屾浠ｇ爜鍧椾腑鐨勮鍙ヨ秺灏戣秺濂�
 * 姣旇緝m1鍜宮2
 * @author mashibing
 */
package com.mashibing.juc.c_016_LockOptimization;

import java.util.concurrent.TimeUnit;


public class FineCoarseLock {
	
	int count = 0;

	synchronized void m1() {
		//do sth need not sync
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//涓氬姟閫昏緫涓彧鏈変笅闈㈣繖鍙ラ渶瑕乻ync锛岃繖鏃朵笉搴旇缁欐暣涓柟娉曚笂閿�
		count ++;
		
		//do sth need not sync
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void m2() {
		//do sth need not sync
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//涓氬姟閫昏緫涓彧鏈変笅闈㈣繖鍙ラ渶瑕乻ync锛岃繖鏃朵笉搴旇缁欐暣涓柟娉曚笂閿�
		//閲囩敤缁嗙矑搴︾殑閿侊紝鍙互浣跨嚎绋嬩簤鐢ㄦ椂闂村彉鐭紝浠庤�屾彁楂樻晥鐜�
		synchronized(this) {
			count ++;
		}
		//do sth need not sync
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

}
