/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatHandler.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天操作类  
 */
package com.chezhibao.handler.chat;

import com.chezhibao.annotion.SocketCmd;
import com.chezhibao.annotion.SocketModule;
import com.chezhibao.model.Result;
import com.chezhibao.value.ChatCmd;
import com.chezhibao.value.Module;

/**
 * 聊天操作类
 *
 * @author zhangdanji
 */
@SocketModule(module = Module.CHAT)
public interface ChatHandler {

    /**
     * 广播消息
     * @param playerId 玩家ID
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @SocketCmd(cmd = ChatCmd.PUBLIC_CHAT)
    public Result<?> publicChat(int playerId,byte[] data);

    /**
     * 私聊
     * @param playerId 玩家ID
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @SocketCmd(cmd = ChatCmd.PRIVATE_CHAT)
    public Result<?> privateChat(int playerId,byte[] data);
}
