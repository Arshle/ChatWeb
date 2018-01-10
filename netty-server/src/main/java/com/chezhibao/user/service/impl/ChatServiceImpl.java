/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatServiceImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天service实现类  
 */
package com.chezhibao.user.service.impl;

import com.chezhibao.entity.Player;
import com.chezhibao.exception.ErrorCodeException;
import com.chezhibao.mappers.PlayerDao;
import com.chezhibao.module.chat.response.ChatResponse;
import com.chezhibao.user.service.ChatService;
import com.chezhibao.session.SessionManager;
import com.chezhibao.value.ChatCmd;
import com.chezhibao.value.Module;
import com.chezhibao.value.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 聊天service实现类
 *
 * @author zhangdanji
 */
@Service("chatService")
public class ChatServiceImpl implements ChatService {

    @Resource
    private PlayerDao playerDao;

    /**
     * 广播聊天
     * @param playerId 玩家id
     * @param content 内容
     *
     * **/
    @Override
    public void publicChat(int playerId, String content) {
        Player player = playerDao.getPlayerById(playerId);
        //获取所有在线玩家ID
        Set<Integer> onlineIds = SessionManager.getOnlinePlayers();

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setSendPlayerId(player.getPlayerId());
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setChatType(ChatCmd.PUBLIC_CHAT);
        chatResponse.setMessage(content);

        for(Integer id : onlineIds){
            SessionManager.sendMessage(id, Module.CHAT, ChatCmd.PUBLIC_CHAT,chatResponse);
        }
    }

    /**
     * 私聊
     * @param playerId 玩家ID
     * @param targetPlayerId 发送的玩家ID
     * @param content 内容
     *
     * **/
    @Override
    public void privateChat(int playerId, int targetPlayerId, String content) {
        //不能和自己私聊
        if(playerId == targetPlayerId){
            throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
        }

        Player player = playerDao.getPlayerById(playerId);
        //判断目标对象是否存在
        Player target = playerDao.getPlayerById(targetPlayerId);
        if(target == null){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
        }
        //判断是否在线
        if(!SessionManager.isOnlinePlayer(targetPlayerId)){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
        }

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatType(ChatCmd.PRIVATE_CHAT);
        chatResponse.setSendPlayerId(player.getPlayerId());
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setTargetPlayerName(target.getPlayerName());
        chatResponse.setMessage(content);

        //给对方发送消息
        SessionManager.sendMessage(target.getPlayerId(),Module.CHAT,ChatCmd.PRIVATE_CHAT,chatResponse);
        //自己也发送一个
        SessionManager.sendMessage(player.getPlayerId(),Module.CHAT,ChatCmd.PRIVATE_CHAT,chatResponse);
    }
}
