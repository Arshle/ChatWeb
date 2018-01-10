/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ResponseEncoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 响应编码器  
 */
package com.chezhibao.coder;

import com.chezhibao.model.Response;
import com.chezhibao.value.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 响应编码器
 *
 * @author zhangdanji
 */
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        //包头
        byteBuf.writeInt(Constants.HEAD_FLAG);
        //module和cmd
        byteBuf.writeInt(response.getModule());
        byteBuf.writeInt(response.getCmd());
        //结果码
        byteBuf.writeInt(response.getStateCode());
        //长度
        int length = response.getData() == null ? 0 : response.getData().length;
        byteBuf.writeInt(length);
        //长度大于0写入数据
        if(length > 0){
            byteBuf.writeBytes(response.getData());
        }
    }
}
