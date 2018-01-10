/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerHandler.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家操作类  
 */
package com.chezhibao.handler.player;

import com.chezhibao.annotion.SocketCmd;
import com.chezhibao.annotion.SocketModule;
import com.chezhibao.model.Result;
import com.chezhibao.module.player.response.PlayerResponse;
import com.chezhibao.session.Session;
import com.chezhibao.value.Module;
import com.chezhibao.value.PlayerCmd;

/**
 * 玩家操作类
 *
 * @author zhangdanji
 */
@SocketModule(module = Module.PLAYER)
public interface PlayerHandler {

    /**
     * 注册并登陆
     * @param session 会话
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @SocketCmd(cmd = PlayerCmd.REGISTER_AND_LOGIN)
    public Result<PlayerResponse> registerAndLogin(Session session,byte[] data);

    /**
     * 登陆
     * @param session 会话
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @SocketCmd(cmd = PlayerCmd.LOGIN)
    public Result<PlayerResponse> login(Session session,byte[] data);
}
