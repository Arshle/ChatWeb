/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: BufferFactory.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: ByteBuf工厂  
 */
package com.chezhibao.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * ByteBuf工厂
 *
 * @author zhangdanji
 */
public class BufferFactory {

    //buffer静态工厂类
    private static ByteBufAllocator bufAllocator = PooledByteBufAllocator.DEFAULT;

    /**
     * 获取空的buffer对象
     * @return byteBuf对象
     *
     * **/
    public static ByteBuf getBuffer(){
        return bufAllocator.heapBuffer();
    }

    /**
     * 返回写入字节数组的buffer
     * @param bytes 字节数组
     * @return byteBuf对象
     *
     * **/
    public static ByteBuf getBuffer(byte[] bytes){
        ByteBuf byteBuf = bufAllocator.heapBuffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
