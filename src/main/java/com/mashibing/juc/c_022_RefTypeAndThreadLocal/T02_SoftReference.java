/**
 * 杞紩鐢�
 * 杞紩鐢ㄦ槸鐢ㄦ潵鎻忚堪涓�浜涜繕鏈夌敤浣嗗苟闈炲繀椤荤殑瀵硅薄銆�
 * 瀵逛簬杞紩鐢ㄥ叧鑱旂潃鐨勫璞★紝鍦ㄧ郴缁熷皢瑕佸彂鐢熷唴瀛樻孩鍑哄紓甯镐箣鍓嶏紝灏嗕細鎶婅繖浜涘璞″垪杩涘洖鏀惰寖鍥磋繘琛岀浜屾鍥炴敹銆�
 * 濡傛灉杩欐鍥炴敹杩樻病鏈夎冻澶熺殑鍐呭瓨锛屾墠浼氭姏鍑哄唴瀛樻孩鍑哄紓甯搞��
 * -Xmx20M
 */
package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

import java.lang.ref.SoftReference;

public class T02_SoftReference {
    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        //m = null;
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());

        //鍐嶅垎閰嶄竴涓暟缁勶紝heap灏嗚涓嶄笅锛岃繖鏃跺�欑郴缁熶細鍨冨溇鍥炴敹锛屽厛鍥炴敹涓�娆★紝濡傛灉涓嶅锛屼細鎶婅蒋寮曠敤骞叉帀
        byte[] b = new byte[1024*1024*15];
        System.out.println(m.get());
    }
}

//杞紩鐢ㄩ潪甯搁�傚悎缂撳瓨浣跨敤
