/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: OrderProducer.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author zhangdanji
 */
public class OrderProducer implements Runnable{

    private RingBuffer<Order> ringBuffer;
    private Order order;
    private EventTranslatorOneArg<Order,Order> translator = new EventTranslatorOneArg<Order, Order>() {
        @Override
        public void translateTo(Order order, long l, Order order2) {
            order.setId(order2.getId());
            order.setCar(order2.getCar());
            order.setCreateTime(order2.getCreateTime());
            order.setDestName(order2.getDestName());
            order.setServiceName(order2.getServiceName());
        }
    };

    public OrderProducer(RingBuffer<Order> ringBuffer,Order order){
        this.ringBuffer = ringBuffer;
        this.order = order;
    }

    @Override
    public void run() {
        ringBuffer.publishEvent(translator,this.order);
    }
}
