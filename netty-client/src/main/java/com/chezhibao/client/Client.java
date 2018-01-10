/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Client.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: Netty客户端类  
 */
package com.chezhibao.client;

import com.chezhibao.coder.RequestEncoder;
import com.chezhibao.coder.ResponseDecoder;
import com.chezhibao.handler.client.ClientHandler;
import com.chezhibao.model.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * Netty客户端类
 *
 * @author zhangdanji
 */
@Component("client")
public class Client {

    private Logger logger = LoggerFactory.getLogger(Client.class);
    //服务类
    private Bootstrap bootstrap = new Bootstrap();
    //会话管道
    private Channel channel;
    //线程池
    private EventLoopGroup worker = new NioEventLoopGroup();

    /**
     * 初始化服务类
     *
     * **/
    @PostConstruct
    private void init(){
        //设置线程池
        bootstrap.group(worker);
        //管道工厂
        bootstrap.channel(NioSocketChannel.class);
        //设置管道
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast("encoder",new RequestEncoder());
                socketChannel.pipeline().addLast("decoder", new ResponseDecoder());
                socketChannel.pipeline().addLast("clientHandler", new ClientHandler());
            }
        });
    }

    /**
     * 连接服务器
     *
     * **/
    public void connect(){
        String serverAddress = null;
        String serverPort = null;
        try {
            Properties properties = Resources.getResourceAsProperties("database.properties");
            serverAddress = properties.getProperty("netty.server.address");
            serverPort = properties.getProperty("netty.server.port");
        } catch (IOException e) {
            e.printStackTrace();
            serverAddress = "127.0.0.1";
            serverPort = "10101";
        }
        if(StringUtils.isEmpty(serverAddress)){
            serverAddress = "127.0.0.1";
        }
        if(StringUtils.isEmpty(serverPort)){
            serverPort = "10101";
        }
        //连接服务器
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(serverAddress, Integer.parseInt(serverPort)));
        try {
            connect.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel = connect.channel();
        logger.info("connect to Server " + serverAddress);
    }

    /**
     * 关闭
     *
     * **/
    public void shutdown(){
        worker.shutdownGracefully();
    }

    /**
     * 获取连接
     * @return 连接
     *
     * **/
    public Channel getChannel(){
        return channel;
    }

    /**
     * 发送请求
     * @param request 请求对象
     *
     * **/
    public void sendRequest(Request request){
        if(channel == null || !channel.isActive()){
            connect();
        }
        channel.writeAndFlush(request);
    }
}
