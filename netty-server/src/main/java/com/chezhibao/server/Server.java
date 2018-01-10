/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Server.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: netty服务端启动类  
 */
package com.chezhibao.server;

import com.chezhibao.coder.RequestDecoder;
import com.chezhibao.coder.ResponseEncoder;
import com.chezhibao.handler.server.ServerHandler;
import com.chezhibao.parameter.ParameterUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * netty服务端启动类
 *
 * @author zhangdanji
 */
@Component("server")
public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * 启动服务端
     *
     * **/
    public static void start(){
        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boss和worker
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置循环线程组时间
            bootstrap.group(boss, worker);
            //设置管道工厂
            bootstrap.channel(NioServerSocketChannel.class);
            //设置管道
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel){
                    socketChannel.pipeline().addLast("decoder",new RequestDecoder());
                    socketChannel.pipeline().addLast("encoder", new ResponseEncoder());
                    socketChannel.pipeline().addLast("serverHandler", new ServerHandler());
                }
            });

            //连接缓冲池队列大小
            bootstrap.option(ChannelOption.SO_BACKLOG, 2048);

            String port = ParameterUtils.getParameter("netty.server.port");
            if(StringUtils.isEmpty(port)){
                port = "10101";
            }

            bootstrap.bind(new InetSocketAddress(Integer.parseInt(port))).sync();
            logger.info("Netty Server Start...");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
