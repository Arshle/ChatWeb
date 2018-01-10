/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerResponse.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家响应类  
 */
package com.chezhibao.module.player.response;

import com.chezhibao.serializer.Serializer;

/**
 * 玩家响应类
 *
 * @author zhangdanji
 */
public class PlayerResponse extends Serializer {

    //玩家ID
    private int playerId;
    //玩家姓名
    private String playerName;
    //玩家等级
    private int level;
    //经验值
    private int exp;

    @Override
    protected void read() {
        this.playerId = readInt();
        this.playerName = readString();
        this.level = readInt();
        this.exp = readInt();
    }

    @Override
    protected void write() {
        writeInt(playerId);
        writeString(playerName);
        writeInt(level);
        writeInt(exp);
    }

    /**
     * Getters、Setters
     *
     * **/
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
