/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatHandlerImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天操作实现类  
 */
package com.chezhibao.handler.impl.chat;

import com.chezhibao.handler.chat.ChatHandler;
import com.chezhibao.model.Result;
import com.chezhibao.module.chat.request.PrivateChatRequest;
import com.chezhibao.module.chat.request.PublicChatRequest;
import com.chezhibao.user.service.ChatService;
import com.chezhibao.value.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * 聊天操作实现类
 *
 * @author zhangdanji
 */
@Component("chatHandler")
public class ChatHandlerImpl implements ChatHandler{

    @Resource
    private ChatService chatService;

    /**
     * 广播消息
     * @param playerId 玩家ID
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @Override
    public Result<?> publicChat(int playerId, byte[] data) {
        try {
            //反序列化
            PublicChatRequest publicChatRequest = new PublicChatRequest();
            publicChatRequest.readFromBytes(data);
            //参数校验
            if(StringUtils.isEmpty(publicChatRequest.getContext())){
                return Result.ERROR(ResultCode.AGRUMENT_ERROR);
            }
            chatService.publicChat(playerId,publicChatRequest.getContext());
        } catch (Exception e) {
            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS();
    }

    /**
     * 私聊
     * @param playerId 玩家ID
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @Override
    public Result<?> privateChat(int playerId, byte[] data) {
        try {
            //反序列化
            PrivateChatRequest privateChatRequest = new PrivateChatRequest();
            privateChatRequest.readFromBytes(data);
            //参数校验
            if(StringUtils.isEmpty(privateChatRequest.getContext()) || privateChatRequest.getTargetPlayerId() <= 0){
                return Result.ERROR(ResultCode.AGRUMENT_ERROR);
            }
            chatService.privateChat(playerId,privateChatRequest.getTargetPlayerId(),privateChatRequest.getContext());
        } catch (Exception e) {
            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS();
    }
}
