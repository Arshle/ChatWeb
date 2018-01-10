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
     * 创建并登陆
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @SocketCmd(cmd = PlayerCmd.REGISTER_AND_LOGIN)
    public void registerAndLogin(int resultCode,byte[] data);

    /**
     * 登陆
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @SocketCmd(cmd = PlayerCmd.LOGIN)
    public void login(int resultCode,byte[] data);
}
