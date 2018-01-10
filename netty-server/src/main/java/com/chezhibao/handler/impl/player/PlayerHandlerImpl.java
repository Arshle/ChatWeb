/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerHandlerImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家操作实现类  
 */
package com.chezhibao.handler.impl.player;

import com.chezhibao.exception.ErrorCodeException;
import com.chezhibao.handler.player.PlayerHandler;
import com.chezhibao.model.Result;
import com.chezhibao.module.player.request.LoginRequest;
import com.chezhibao.module.player.request.RegisterRequest;
import com.chezhibao.module.player.response.PlayerResponse;
import com.chezhibao.user.service.PlayerService;
import com.chezhibao.session.Session;
import com.chezhibao.value.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * 玩家操作实现类
 *
 * @author zhangdanji
 */
@Component("playerHandler")
public class PlayerHandlerImpl implements PlayerHandler {

    @Resource
    private PlayerService playerService;

    /**
     * 注册并登陆
     * @param session 会话
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @Override
    public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            //反序列化请求对象
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.readFromBytes(data);
            //判断玩家名或密码是否为空
            if(StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())){
                throw new ErrorCodeException(ResultCode.PLAYERNAME_NULL);
            }
            playerResponse = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassword());

        } catch (Exception e) {

            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS(playerResponse);
    }

    /**
     * 登陆
     * @param session 会话
     * @param data 数据
     * @return 结果对象
     *
     * **/
    @Override
    public Result<PlayerResponse> login(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.readFromBytes(data);

            //用户名或密码是否为空
            if(StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassword())){
                throw new ErrorCodeException(ResultCode.PLAYERNAME_NULL);
            }
            playerResponse = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassword());
        } catch (Exception e) {
            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS(playerResponse);
    }
}
