/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PublicChatRequest.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 广播聊天请求类  
 */
package com.chezhibao.module.chat.request;

import com.chezhibao.serializer.Serializer;

/**
 * 广播聊天请求类
 *
 * @author zhangdanji
 */
public class PublicChatRequest extends Serializer {

    //内容
    private String context;

    @Override
    protected void read() {
        this.context = readString();
    }

    @Override
    protected void write() {
        writeString(context);
    }

    /**
     * Getter、Setters
     *
     * **/
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
