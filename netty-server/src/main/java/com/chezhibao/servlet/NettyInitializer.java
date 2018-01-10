/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: NettyInitializer.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: Netty服务启动类  
 */
package com.chezhibao.servlet;

import com.chezhibao.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Netty服务启动类
 *
 * @author zhangdanji
 */
public class NettyInitializer extends HttpServlet {

    private static final long serialVersionUID = 7016659623449998228L;

    @Override
    public void init() throws ServletException {
        Server.start();
    }
}
