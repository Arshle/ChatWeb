/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ResponseDecoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 响应解码器  
 */
package com.chezhibao.coder;

import com.chezhibao.model.Response;
import com.chezhibao.value.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 响应解码器
 *
 * @author zhangdanji
 */
public class ResponseDecoder extends ByteToMessageDecoder {

    //数据包最短长度
    private static int BASE_LENGTH = 4 + 4 + 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        while(true){
            if(byteBuf.readableBytes() >= BASE_LENGTH){
                //第一个可读数据包的起始位置
                int beginIndex;

                while(true) {
                    //包头开始游标点
                    beginIndex = byteBuf.readerIndex();
                    //标记初始读游标位置
                    byteBuf.markReaderIndex();
                    //找到包头结束循环
                    if (byteBuf.readInt() == Constants.HEAD_FLAG) {
                        break;
                    }
                    //未读到包头标识略过一个字节
                    byteBuf.resetReaderIndex();
                    byteBuf.readByte();

                    //不满足
                    if(byteBuf.readableBytes() < BASE_LENGTH){
                        return;
                    }
                }
                //读取模块号命令号
                int module = byteBuf.readInt();
                int cmd = byteBuf.readInt();
                //结果码
                int stateCode = byteBuf.readInt();

                //读取数据长度
                int length = byteBuf.readInt();
                if(length < 0 ){
                    channelHandlerContext.channel().close();
                }

                //数据包还没到齐
                if(byteBuf.readableBytes() < length){
                    byteBuf.readerIndex(beginIndex);
                    return ;
                }

                //读数据部分
                byte[] data = new byte[length];
                byteBuf.readBytes(data);

                Response response = new Response();
                response.setModule(module);
                response.setCmd(cmd);
                response.setStateCode(stateCode);
                response.setData(data);
                //解析出消息对象，继续往下面的handler传递
                list.add(response);
            }else{
                break;
            }
        }
    }
}
