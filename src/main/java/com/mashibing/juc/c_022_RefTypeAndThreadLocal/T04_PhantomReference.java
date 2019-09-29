/**
 *
 *
 *     一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，
 *     也无法通过虚引用来获取一个对象的实例。
 *     为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。
 *     虚引用和弱引用对关联对象的回收都不会产生影响，如果只有虚引用活着弱引用关联着对象，
 *     那么这个对象就会被回收。它们的不同之处在于弱引用的get方法，虚引用的get方法始终返回null,
 *     弱引用可以使用ReferenceQueue,虚引用必须配合ReferenceQueue使用。
 *
 *     jdk中直接内存的回收就用到虚引用，由于jvm自动内存管理的范围是堆内存，
 *     而直接内存是在堆内存之外（其实是内存映射文件，自行去理解虚拟内存空间的相关概念），
 *     所以直接内存的分配和回收都是有Unsafe类去操作，java在申请一块直接内存之后，
 *     会在堆内存分配一个对象保存这个堆外内存的引用，
 *     这个对象被垃圾收集器管理，一旦这个对象被回收，
 *     相应的用户线程会收到通知并对直接内存进行清理工作。
 *
 *     事实上，虚引用有一个很重要的用途就是用来做堆外内存的释放，
 *     DirectByteBuffer就是通过虚引用来实现堆外内存的释放的。
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
                    System.out.println("--- 虚引用对象被jvm回收了 ---- " + poll);
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

