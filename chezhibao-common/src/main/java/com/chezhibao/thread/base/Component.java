/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Component.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * @author zhangdanji
 */
public class Component {

    private static RingBuffer<Order> ringBuffer;

    public static void startWork() throws InterruptedException {
        ExecutorService threadPoolExecutor = OrderUtils.getThreadPoolExecutor();
        Disruptor<Order> disruptor = new Disruptor<>(OrderUtils.getOrderFactory(),
                OrderUtils.getRingBufferSize(),threadPoolExecutor,
                ProducerType.SINGLE,new YieldingWaitStrategy());

        ringBuffer = disruptor.getRingBuffer();

        Handler1 handler1 = new Handler1();
        Handler2 handler2 = new Handler2();
        Handler3 handler3 = new Handler3();
        EventHandlerGroup<Order> orderEventHandlerGroup = disruptor.handleEventsWith(handler1, handler2);
        EventHandlerGroup<Order> then = orderEventHandlerGroup.then(handler3);
        then.then(new Handler4());
        disruptor.start();

        produce();
    }

    private static void produce(){
        ExecutorService threadPoolExecutor = OrderUtils.getThreadPoolExecutor();
        for(int i = 0; i < 10; i ++){
            Order order = new Order();
            Car car = new Car();
            car.setId(i);
            car.setBrand("品牌" + i);
            car.setModel("车系" + i);
            car.setType("车型" + i);
            car.setCustomerName("客户" + i);
            car.setBak("备注" + i);
            order.setId(i);
            order.setServiceName("客服" + i);
            order.setDestName("目标" + i);
            order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            order.setCar(car);
            threadPoolExecutor.execute(new OrderProducer(ringBuffer,order));
        }
        threadPoolExecutor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        startWork();
    }
}
