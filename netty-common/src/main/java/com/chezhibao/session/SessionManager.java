/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SessionManager.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 会话管理类  
 */
package com.chezhibao.session;

import com.chezhibao.model.Response;
import com.chezhibao.serializer.Serializer;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 会话管理类
 *
 * @author zhangdanji
 */
public class SessionManager {

    //在线的玩家
    private static final ConcurrentMap<Integer, Session> ONLINE_SESSONS = new ConcurrentHashMap<>();

    /**
     * 加入会话
     * @param playerId 玩家ID
     * @param session 会话
     * @return 是否成功
     *
     * **/
    public static boolean putSession(int playerId,Session session){

        return !ONLINE_SESSONS.containsKey(playerId) && ONLINE_SESSONS.putIfAbsent(playerId, session) != null;
    }

    /**
     * 移除会话
     * @param playerId 玩家ID
     * @return 移除的会话
     *
     * **/
    public static Session removeSession(int playerId){
        return ONLINE_SESSONS.remove(playerId);
    }

    /**
     * 发送消息
     * @param playerId 玩家ID
     * @param module 模块号
     * @param cmd 命令号
     * @param message 发送的消息
     *
     * **/
    public static <T extends Serializer> void sendMessage(int playerId,int module,int cmd,T message){
        Session session = ONLINE_SESSONS.get(playerId);
        if(session != null && session.isConnected()){
            Response response = new Response(module, cmd, message.getBytes());
            session.write(response);
        }
    }

    /**
     * 是否在线
     * @param playerId 玩家ID
     * @return 是否在线
     *
     * **/
    public static boolean isOnlinePlayer(int playerId){
        return ONLINE_SESSONS.containsKey(playerId);
    }

    /**
     * 获取所有在线玩家
     * @return 在线玩家ID
     *
     * **/
    public static Set<Integer> getOnlinePlayers(){
        return Collections.unmodifiableSet(ONLINE_SESSONS.keySet());
    }
}
