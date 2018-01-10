/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: MyQueue.java
 * Author:   zhangdanji
 * Date:     2017年11月22日
 * Description:   
 */
package com.chezhibao.thread;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangdanji
 */
public class MyQueue {

    private LinkedList<Object> list = new LinkedList<>();

    private AtomicInteger count = new AtomicInteger(0);

    private final int minSize = 0;

    private final int maxSize;

    private final Object LOCK = new Object();

    public MyQueue(int size){
        this.maxSize = size;
    }

    public void put(Object obj){
        synchronized (LOCK){
            while (count.get() == maxSize){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.incrementAndGet();
            System.out.println("新加入的元素为:" + obj);
            LOCK.notify();
        }
    }

    public Object take(){
        Object ret;
        synchronized (LOCK){
            while(count.get() == this.minSize){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ret = list.removeFirst();
            count.decrementAndGet();
            System.out.println("拿出一个元素:" + ret);
            LOCK.notify();
        }
        return ret;
    }

    public int getSize(){
        return this.count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final MyQueue mq = new MyQueue(5);
        mq.put("a");
        mq.put("b");
        mq.put("c");
        mq.put("d");
        mq.put("e");
        System.out.println(mq.getSize());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mq.put("f");
                mq.put("g");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Object take = mq.take();
                System.out.println("移除的元素为:" + take);
                Object take2 = mq.take();
                System.out.println("移除的元素为:" + take2);
            }
        });
        t.start();

        TimeUnit.SECONDS.sleep(2);

        t2.start();
    }
}
