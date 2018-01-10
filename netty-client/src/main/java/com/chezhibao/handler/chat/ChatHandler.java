/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatHandler.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天handler类  
 */
package com.chezhibao.handler.chat;

import com.chezhibao.annotion.SocketCmd;
import com.chezhibao.annotion.SocketModule;
import com.chezhibao.value.ChatCmd;
import com.chezhibao.value.Module;

/**
 * 聊天handler类
 *
 * @author zhangdanji
 */
@SocketModule(module = Module.CHAT)
public interface ChatHandler {

    /**
     * 广播聊天
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @SocketCmd(cmd = ChatCmd.PUBLIC_CHAT)
    public void publicChat(int resultCode,byte[] data);

    /**
     * 私聊
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @SocketCmd(cmd = ChatCmd.PRIVATE_CHAT)
    public void privateChat(int resultCode,byte[] data);
}
