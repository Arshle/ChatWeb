/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Server.java
 * Author:   zhangdanji
 * Date:     2017年12月01日
 * Description:
 */
package com.chezhibao.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangdanji
 */
public class Server {
    private ExecutorService executor;
    private AsynchronousChannelGroup threadGroup;
    public AsynchronousServerSocketChannel assc;

    public Server(int port){
        try {
            executor = Executors.newCachedThreadPool();
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executor,1);
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            assc.bind(new InetSocketAddress(port));
            assc.accept(this,new Handler());
            System.out.println("start");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
