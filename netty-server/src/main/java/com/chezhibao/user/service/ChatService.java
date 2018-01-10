/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatService.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天service类  
 */
package com.chezhibao.user.service;

/**
 * 聊天service类
 *
 * @author zhangdanji
 */
public interface ChatService {

    /**
     * 广播聊天
     * @param playerId 玩家id
     * @param content 内容
     *
     * **/
    public void publicChat(int playerId,String content);

    /**
     * 私聊
     * @param playerId 玩家ID
     * @param targetPlayerId 发送的玩家ID
     * @param content 内容
     *
     * **/
    public void privateChat(int playerId,int targetPlayerId,String content);
}
