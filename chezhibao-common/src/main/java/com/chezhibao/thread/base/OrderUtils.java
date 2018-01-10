/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: OrderUtils.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import com.lmax.disruptor.EventFactory;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangdanji
 */
public class OrderUtils {

    private static AtomicInteger index = new AtomicInteger(0);

    static EventFactory<Order> getOrderFactory(){
        return new EventFactory<Order>() {
            @Override
            public Order newInstance() {
                Order order = new Order();
                order.setCar(new Car());
                return order;
            }
        };
    }

    static int getThreadNum(){
        return 100;
    }

    static int getRingBufferSize(){
        return 64;
    }

    static ThreadFactory getThreadFactory(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"worker-" + index.incrementAndGet());
            }
        };
    }

    static ExecutorService getThreadPoolExecutor(){
        return new ThreadPoolExecutor(getThreadNum(),getThreadNum() * 2,0L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(getThreadNum()),getThreadFactory());
    }
}
