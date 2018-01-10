/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Handler.java
 * Author:   zhangdanji
 * Date:     2017年12月01日
 * Description:
 */
package com.chezhibao.aio;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhangdanji
 */
public class Handler implements CompletionHandler<AsynchronousSocketChannel,Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {

    }

    @Override
    public void failed(Throwable exc, Server attachment) {

    }
}
