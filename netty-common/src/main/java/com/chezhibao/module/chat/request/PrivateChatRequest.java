/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PrivateChatRequest.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 私聊请求类  
 */
package com.chezhibao.module.chat.request;

import com.chezhibao.serializer.Serializer;

/**
 * 私聊请求类
 *
 * @author zhangdanji
 */
public class PrivateChatRequest extends Serializer {

    //目标玩家ID
    private int targetPlayerId;
    //内容
    private String context;

    @Override
    protected void read() {
        this.targetPlayerId = readInt();
        this.context = readString();
    }

    @Override
    protected void write() {
        writeInt(targetPlayerId);
        writeString(context);
    }

    /**
     * Getters、Setters
     *
     * **/
    public int getTargetPlayerId() {
        return targetPlayerId;
    }

    public void setTargetPlayerId(int targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
