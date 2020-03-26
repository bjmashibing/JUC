/**
 * 绋嬪簭鍦ㄦ墽琛岃繃绋嬩腑锛屽鏋滃嚭鐜板紓甯革紝榛樿鎯呭喌閿佷細琚噴鏀�
 * 鎵�浠ワ紝鍦ㄥ苟鍙戝鐞嗙殑杩囩▼涓紝鏈夊紓甯歌澶氬姞灏忓績锛屼笉鐒跺彲鑳戒細鍙戠敓涓嶄竴鑷寸殑鎯呭喌銆�
 * 姣斿锛屽湪涓�涓獁eb app澶勭悊杩囩▼涓紝澶氫釜servlet绾跨▼鍏卞悓璁块棶鍚屼竴涓祫婧愶紝杩欐椂濡傛灉寮傚父澶勭悊涓嶅悎閫傦紝
 * 鍦ㄧ涓�涓嚎绋嬩腑鎶涘嚭寮傚父锛屽叾浠栫嚎绋嬪氨浼氳繘鍏ュ悓姝ヤ唬鐮佸尯锛屾湁鍙兘浼氳闂埌寮傚父浜х敓鏃剁殑鏁版嵁銆�
 * 鍥犳瑕侀潪甯稿皬蹇冪殑澶勭悊鍚屾涓氬姟閫昏緫涓殑寮傚父
 * @author mashibing
 */
package com.mashibing.juc.c_011;

import java.util.concurrent.TimeUnit;

public class T {
	int count = 0;
	synchronized void m() {
		System.out.println(Thread.currentThread().getName() + " start");
		while(true) {
			count ++;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
			try {
				TimeUnit.SECONDS.sleep(1);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(count == 5) {
				int i = 1/0; //姝ゅ鎶涘嚭寮傚父锛岄攣灏嗚閲婃斁锛岃鎯充笉琚噴鏀撅紝鍙互鍦ㄨ繖閲岃繘琛宑atch锛岀劧鍚庤寰幆缁х画
				System.out.println(i);
			}
		}
	}
	
	public static void main(String[] args) {
		T t = new T();
		Runnable r = new Runnable() {

			@Override
			public void run() {
				t.m();
			}
			
		};
		new Thread(r, "t1").start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(r, "t2").start();
	}
	
}


