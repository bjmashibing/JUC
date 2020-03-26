/**
 *
 *
 *     涓�涓璞℃槸鍚︽湁铏氬紩鐢ㄧ殑瀛樺湪锛屽畬鍏ㄤ笉浼氬鍏剁敓瀛樻椂闂存瀯鎴愬奖鍝嶏紝
 *     涔熸棤娉曢�氳繃铏氬紩鐢ㄦ潵鑾峰彇涓�涓璞＄殑瀹炰緥銆�
 *     涓轰竴涓璞¤缃櫄寮曠敤鍏宠仈鐨勫敮涓�鐩殑灏辨槸鑳藉湪杩欎釜瀵硅薄琚敹闆嗗櫒鍥炴敹鏃舵敹鍒颁竴涓郴缁熼�氱煡銆�
 *     铏氬紩鐢ㄥ拰寮卞紩鐢ㄥ鍏宠仈瀵硅薄鐨勫洖鏀堕兘涓嶄細浜х敓褰卞搷锛屽鏋滃彧鏈夎櫄寮曠敤娲荤潃寮卞紩鐢ㄥ叧鑱旂潃瀵硅薄锛�
 *     閭ｄ箞杩欎釜瀵硅薄灏变細琚洖鏀躲�傚畠浠殑涓嶅悓涔嬪鍦ㄤ簬寮卞紩鐢ㄧ殑get鏂规硶锛岃櫄寮曠敤鐨刧et鏂规硶濮嬬粓杩斿洖null,
 *     寮卞紩鐢ㄥ彲浠ヤ娇鐢≧eferenceQueue,铏氬紩鐢ㄥ繀椤婚厤鍚圧eferenceQueue浣跨敤銆�
 *
 *     jdk涓洿鎺ュ唴瀛樼殑鍥炴敹灏辩敤鍒拌櫄寮曠敤锛岀敱浜巎vm鑷姩鍐呭瓨绠＄悊鐨勮寖鍥存槸鍫嗗唴瀛橈紝
 *     鑰岀洿鎺ュ唴瀛樻槸鍦ㄥ爢鍐呭瓨涔嬪锛堝叾瀹炴槸鍐呭瓨鏄犲皠鏂囦欢锛岃嚜琛屽幓鐞嗚В铏氭嫙鍐呭瓨绌洪棿鐨勭浉鍏虫蹇碉級锛�
 *     鎵�浠ョ洿鎺ュ唴瀛樼殑鍒嗛厤鍜屽洖鏀堕兘鏄湁Unsafe绫诲幓鎿嶄綔锛宩ava鍦ㄧ敵璇蜂竴鍧楃洿鎺ュ唴瀛樹箣鍚庯紝
 *     浼氬湪鍫嗗唴瀛樺垎閰嶄竴涓璞′繚瀛樿繖涓爢澶栧唴瀛樼殑寮曠敤锛�
 *     杩欎釜瀵硅薄琚瀮鍦炬敹闆嗗櫒绠＄悊锛屼竴鏃﹁繖涓璞¤鍥炴敹锛�
 *     鐩稿簲鐨勭敤鎴风嚎绋嬩細鏀跺埌閫氱煡骞跺鐩存帴鍐呭瓨杩涜娓呯悊宸ヤ綔銆�
 *
 *     浜嬪疄涓婏紝铏氬紩鐢ㄦ湁涓�涓緢閲嶈鐨勭敤閫斿氨鏄敤鏉ュ仛鍫嗗鍐呭瓨鐨勯噴鏀撅紝
 *     DirectByteBuffer灏辨槸閫氳繃铏氬紩鐢ㄦ潵瀹炵幇鍫嗗鍐呭瓨鐨勯噴鏀剧殑銆�
 *
 */


package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class T04_PhantomReference {
    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();



    public static void main(String[] args) {


        PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);


        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends M> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("--- 铏氬紩鐢ㄥ璞¤jvm鍥炴敹浜� ---- " + poll);
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

