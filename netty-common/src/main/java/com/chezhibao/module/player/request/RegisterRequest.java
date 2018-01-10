/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: RegisterRequest.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 注册请求  
 */
package com.chezhibao.module.player.request;

import com.chezhibao.serializer.Serializer;

/**
 * 注册请求
 *
 * @author zhangdanji
 */
public class RegisterRequest extends Serializer {

    //玩家姓名
    private String playerName;
    //密码
    private String password;

    @Override
    protected void read() {
        this.playerName = readString();
        this.password = readString();
    }

    @Override
    protected void write() {
        writeString(playerName);
        writeString(password);
    }

    /**
     * Getters、Setters
     *
     * **/
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
