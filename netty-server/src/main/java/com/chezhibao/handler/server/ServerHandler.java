/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ServerHandler.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 服务端拦截器类  
 */
package com.chezhibao.handler.server;

import com.chezhibao.entity.Player;
import com.chezhibao.invoker.Invoker;
import com.chezhibao.invoker.InvokerHolder;
import com.chezhibao.model.Request;
import com.chezhibao.model.Response;
import com.chezhibao.model.Result;
import com.chezhibao.serializer.Serializer;
import com.chezhibao.session.Session;
import com.chezhibao.session.SessionImpl;
import com.chezhibao.session.SessionManager;
import com.chezhibao.value.Module;
import com.chezhibao.value.ResultCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端拦截器类
 *
 * @author zhangdanji
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {

    /**
     * 接受消息
     *
     * **/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        //处理消息
        handlerMessage(new SessionImpl(channelHandlerContext.channel()),request);
    }

    /**
     * 处理消息
     * @param session 会话
     * @param request 请求对象
     *
     * **/
    private void handlerMessage(Session session,Request request){
        //响应类
        Response response = new Response(request);
        //获取命令执行器
        Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());

        if(invoker != null){
            try {
                Result<?> result = null;
                //玩家模块传入channel，否则传入playerId
                if(request.getModule() == Module.PLAYER){
                    result = (Result<?>) invoker.invoke(session,request.getData());
                }else{
                    Object attachment = session.getAttachment();
                    if(attachment != null){
                        Player player = (Player) attachment;
                        result = (Result<?>) invoker.invoke(player.getPlayerId(), request.getData());
                    }else{
                        response.setStateCode(ResultCode.LOGIN_PLEASE);
                        session.write(response);
                        return;
                    }
                }

                //判断请求是否成功
                if(result.getResultCode() == ResultCode.SUCCESS){
                    //回写数据
                    Object object = result.getContent();
                    if(object != null){
                        Serializer content = (Serializer)object;
                        response.setData(content.getBytes());
                    }
                    session.write(response);
                }else{
                    //返回错误码
                    response.setStateCode(result.getResultCode());
                    session.write(response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
                session.write(response);
            }

        }else{
            //未找到执行者
            response.setStateCode(ResultCode.NO_INVOKER);
            session.write(response);
        }
    }

    /**
     * 玩家掉线时处理会话
     *
     * **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //移除会话
        Session session = new SessionImpl(ctx.channel());
        Object attachment = session.getAttachment();
        if(attachment != null){
            Player player = (Player)attachment;
            SessionManager.removeSession(player.getPlayerId());
        }
    }


}
