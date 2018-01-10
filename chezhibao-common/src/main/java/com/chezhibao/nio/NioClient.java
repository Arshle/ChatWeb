/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: NioClient.java
 * Author:   zhangdanji
 * Date:     2017年12月01日
 * Description:
 */
package com.chezhibao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zhangdanji
 */
public class NioClient {
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8765);
        SocketChannel sc;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            sc = SocketChannel.open();
            sc.connect(address);
            while(true){
                //定义一个字节数组，然后使用系统录入功能：
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                //把数据放到缓冲区中
                byteBuffer.put(bytes);
                //对缓冲区进行复位
                byteBuffer.flip();
                //写出数据
                sc.write(byteBuffer);
                //清空缓冲区数据
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
