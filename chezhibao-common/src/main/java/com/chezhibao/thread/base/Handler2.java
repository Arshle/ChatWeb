/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Handler2.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import com.lmax.disruptor.EventHandler;

/**
 * @author zhangdanji
 */
public class Handler2 implements EventHandler<Order> {
    @Override
    public void onEvent(Order order, long l, boolean b) throws Exception {
        order.getCar().setBak(order.getCar().getBak() + "经过handler2");
    }
}
