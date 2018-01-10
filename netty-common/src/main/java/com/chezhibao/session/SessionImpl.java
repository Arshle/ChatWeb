/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SessionImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 会话管理实现类  
 */
package com.chezhibao.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


/**
 * 会话管理实现类
 *
 * @author zhangdanji
 */
public class SessionImpl implements Session {

    //绑定对象key
    private static AttributeKey<Object> ATTRIBUTE_KEY = AttributeKey.valueOf("ATTRIBUTE_KEY");

    //管道类
    private Channel channel;

    public SessionImpl(Channel channel){
        this.channel = channel;
    }

    /**
     * 会话绑定对象
     * @return 绑定对象
     *
     */
    @Override
    public Object getAttachment() {
        return channel.attr(ATTRIBUTE_KEY).get();
    }

    /**
     * 绑定对象
     * @param attachment 绑定对象
     *
     */
    @Override
    public void setAttachment(Object attachment) {
        channel.attr(ATTRIBUTE_KEY).set(attachment);
    }

    /**
     * 移除绑定对象
     *
     */
    @Override
    public void removeAttachment() {
        channel.attr(ATTRIBUTE_KEY).set(null);
    }

    /**
     * 向会话中写入消息
     * @param message 消息
     *
     */
    @Override
    public void write(Object message) {
        channel.writeAndFlush(message);
    }

    /**
     * 判断会话是否在连接中
     * @return 是否连接
     */
    @Override
    public boolean isConnected() {
        return channel.isActive();
    }

    /**
     * 关闭
     *
     */
    @Override
    public void close() {
        channel.close();
    }
}
