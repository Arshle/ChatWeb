/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ResultCode.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 结果码  
 */
package com.chezhibao.value;

/**
 * 结果码
 *
 * @author zhangdanji
 */
public class ResultCode {

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 找不到命令
     */
    public static final int NO_INVOKER = 1;

    /**
     * 参数异常
     */
    public static final int AGRUMENT_ERROR = 2;

    /**
     * 未知异常
     */
    public static final int UNKOWN_EXCEPTION = 3;

    /**
     * 玩家名或密码不能为空
     */
    public static final int PLAYERNAME_NULL = 4;

    /**
     * 玩家名已使用
     */
    public static final int PLAYER_EXIST = 5;

    /**
     * 玩家不存在
     */
    public static final int PLAYER_NO_EXIST = 6;

    /**
     * 密码错误
     */
    public static final int PASSWARD_ERROR = 7;

    /**
     * 您已登录
     */
    public static final int HAS_LOGIN = 8;

    /**
     * 登录失败
     */
    public static final int LOGIN_FAIL = 9;

    /**
     * 玩家不在线
     */
    public static final int PLAYER_NO_ONLINE = 10;

    /**
     * 请先登录
     */
    public static final int LOGIN_PLEASE = 11;

    /**
     * 不能私聊自己
     */
    public static final int CAN_CHAT_YOUSELF = 12;
}
