/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: RequestEncoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 请求编码器  
 */
package com.chezhibao.coder;

import com.chezhibao.model.Request;
import com.chezhibao.value.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 请求编码器
 *
 * @author zhangdanji
 */
public class RequestEncoder extends MessageToByteEncoder<Request> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        //包头
        byteBuf.writeInt(Constants.HEAD_FLAG);
        //module
        byteBuf.writeInt(request.getModule());
        //cmd
        byteBuf.writeInt(request.getCmd());
        //长度
        int length = request.getData() == null ? 0 : request.getData().length;
        byteBuf.writeInt(length);
        //长度大于0写入数据
        if(length > 0){
            byteBuf.writeBytes(request.getData());
        }
    }
}
