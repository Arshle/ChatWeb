/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerHandlerImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家操作实现类  
 */
package com.chezhibao.handler.impl.player;

import com.chezhibao.handler.player.PlayerHandler;
import org.springframework.stereotype.Component;

/**
 * 玩家操作实现类
 *
 * @author zhangdanji
 */
@Component("playerHandler")
public class PlayerHandlerImpl implements PlayerHandler {

    /**
     * 创建并登陆
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @Override
    public void registerAndLogin(int resultCode, byte[] data) {

    }

    /**
     * 登陆
     * @param resultCode 状态码
     * @param data 数据
     *
     * **/
    @Override
    public void login(int resultCode, byte[] data) {

    }
}
