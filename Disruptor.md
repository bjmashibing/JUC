# Disruptor

作者：马士兵 http://www.mashibing.com

最近更新：2019年10月22日

## 介绍

主页：http://lmax-exchange.github.io/disruptor/

源码：https://github.com/LMAX-Exchange/disruptor

GettingStarted: https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started

api: http://lmax-exchange.github.io/disruptor/docs/index.html

maven: https://mvnrepository.com/artifact/com.lmax/disruptor

## Disruptor的特点

对比ConcurrentLinkedQueue : 链表实现

JDK中没有ConcurrentArrayQueue

Disruptor是数组实现的

无锁，高并发，使用环形Buffer，直接覆盖（不用清除）旧的数据，降低GC频率

实现了基于事件的生产者消费者模式（观察者模式）

## RingBuffer

环形队列

RingBuffer的序号，指向下一个可用的元素

采用数组实现，没有首尾指针

对比ConcurrentLinkedQueue，用数组实现的速度更快

> 假如长度为8，当添加到第12个元素的时候在哪个序号上呢？用12%8决定
>
> 当Buffer被填满的时候到底是覆盖还是等待，由Producer决定
>
> 长度设为2的n次幂，利于二进制计算，例如：12%8 = 12 & (8 - 1)  pos = num & (size -1)

## Disruptor开发步骤

1. 定义Event - 队列中需要处理的元素

2. 定义Event工厂，用于填充队列

   > 这里牵扯到效率问题：disruptor初始化的时候，会调用Event工厂，对ringBuffer进行内存的提前分配
   >
   > GC产频率会降低

3. 定义EventHandler（消费者），处理容器中的元素

## 事件发布模板

```java
long sequence = ringBuffer.next();  // Grab the next sequence
try {
    LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
    // for the sequence
    event.set(8888L);  // Fill with data
} finally {
    ringBuffer.publish(sequence);
}
```

## 使用EventTranslator发布事件

```java
//===============================================================
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.set(8888L);
            }
        };

        ringBuffer.publishEvent(translator1);

        //===============================================================
        EventTranslatorOneArg<LongEvent, Long> translator2 = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l) {
                event.set(l);
            }
        };

        ringBuffer.publishEvent(translator2, 7777L);

        //===============================================================
        EventTranslatorTwoArg<LongEvent, Long, Long> translator3 = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l1, Long l2) {
                event.set(l1 + l2);
            }
        };

        ringBuffer.publishEvent(translator3, 10000L, 10000L);

        //===============================================================
        EventTranslatorThreeArg<LongEvent, Long, Long, Long> translator4 = new EventTranslatorThreeArg<LongEvent, Long, Long, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l1, Long l2, Long l3) {
                event.set(l1 + l2 + l3);
            }
        };

        ringBuffer.publishEvent(translator4, 10000L, 10000L, 1000L);

        //===============================================================
        EventTranslatorVararg<LongEvent> translator5 = new EventTranslatorVararg<LongEvent>() {

            @Override
            public void translateTo(LongEvent event, long sequence, Object... objects) {
                long result = 0;
                for(Object o : objects) {
                    long l = (Long)o;
                    result += l;
                }
                event.set(result);
            }
        };

        ringBuffer.publishEvent(translator5, 10000L, 10000L, 10000L, 10000L);
```

## 使用Lamda表达式

```java
package com.mashibing.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class Main03
{
    public static void main(String[] args) throws Exception
    {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();


        ringBuffer.publishEvent((event, sequence) -> event.set(10000L));

        System.in.read();
    }
}
```

## ProducerType生产者线程模式

> ProducerType有两种模式 Producer.MULTI和Producer.SINGLE
>
> 默认是MULTI，表示在多线程模式下产生sequence
>
> 如果确认是单线程生产者，那么可以指定SINGLE，效率会提升
>
> 如果是多个生产者（多线程），但模式指定为SINGLE，会出什么问题呢？

## 等待策略

1，(常用）BlockingWaitStrategy：通过线程阻塞的方式，等待生产者唤醒，被唤醒后，再循环检查依赖的sequence是否已经消费。

2，BusySpinWaitStrategy：线程一直自旋等待，可能比较耗cpu

3，LiteBlockingWaitStrategy：线程阻塞等待生产者唤醒，与BlockingWaitStrategy相比，区别在signalNeeded.getAndSet,如果两个线程同时访问一个访问waitfor,一个访问signalAll时，可以减少lock加锁次数.

4，LiteTimeoutBlockingWaitStrategy：与LiteBlockingWaitStrategy相比，设置了阻塞时间，超过时间后抛异常。

5，PhasedBackoffWaitStrategy：根据时间参数和传入的等待策略来决定使用哪种等待策略

6，TimeoutBlockingWaitStrategy：相对于BlockingWaitStrategy来说，设置了等待时间，超过后抛异常

7，（常用）YieldingWaitStrategy：尝试100次，然后Thread.yield()让出cpu

8. （常用）SleepingWaitStrategy : sleep

## 消费者异常处理

默认：disruptor.setDefaultExceptionHandler()

覆盖：disruptor.handleExceptionFor().with()

## 依赖处理

