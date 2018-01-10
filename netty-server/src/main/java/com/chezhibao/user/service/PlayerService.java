/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerService.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家Service类  
 */
package com.chezhibao.user.service;

import com.chezhibao.module.player.response.PlayerResponse;
import com.chezhibao.session.Session;

/**
 * 玩家Service类
 *
 * @author zhangdanji
 */
public interface PlayerService {

    /**
     * 注册并登陆
     * @param session 会话
     * @param playerName 玩家姓名
     * @param password 密码
     * @return 玩家响应类
     *
     * **/
    public PlayerResponse registerAndLogin(Session session,String playerName,String password);

    /**
     * 登陆
     * @param session 会话
     * @param playerName 玩家姓名
     * @param password 密码
     * @return 玩家响应类
     *
     * **/
    public PlayerResponse login(Session session,String playerName,String password);
}
