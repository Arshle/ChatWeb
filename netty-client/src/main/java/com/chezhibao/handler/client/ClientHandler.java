/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ClientHandler.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 客户端消息处理器  
 */
package com.chezhibao.handler.client;

import com.chezhibao.invoker.Invoker;
import com.chezhibao.invoker.InvokerHolder;
import com.chezhibao.model.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端消息处理器
 *
 * @author zhangdanji
 */
public class ClientHandler extends SimpleChannelInboundHandler<Response> {

    /**
     * 获取服务器消息
     *
     * **/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        //处理消息
        handlerMessage(response);
    }

    /**
     * 处理消息
     * @param response
     *
     * **/
    private void handlerMessage(Response response){
        Invoker invoker = InvokerHolder.getInvoker(response.getModule(), response.getCmd());
        if(invoker != null){
            try {
                invoker.invoke(response.getStateCode(), response.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException("cannot find a Invoker");
        }
    }

    /**
     * 与服务器断开连接
     *
     * **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}
