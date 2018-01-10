/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Player.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家实体类  
 */
package com.chezhibao.entity;

import java.io.Serializable;

/**
 * 玩家实体类
 *
 * @author zhangdanji
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -8816804309263819494L;

    //玩家ID
    private int playerId;

    //玩家姓名
    private String playerName;
    //密码
    private String password;
    //玩家等级
    private int level;
    //玩家经验
    private int exp;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "{playerId : " + playerId + ",playerName : " + playerName + ",level : " + level + ",exp : " + exp + "}";
    }
}
