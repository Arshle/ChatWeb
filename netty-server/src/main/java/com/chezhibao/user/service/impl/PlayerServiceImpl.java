/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerServiceImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家service实现类  
 */
package com.chezhibao.user.service.impl;

import com.chezhibao.entity.Player;
import com.chezhibao.exception.ErrorCodeException;
import com.chezhibao.mappers.PlayerDao;
import com.chezhibao.module.player.response.PlayerResponse;
import com.chezhibao.user.service.PlayerService;
import com.chezhibao.session.Session;
import com.chezhibao.session.SessionManager;
import com.chezhibao.value.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * 玩家service实现类
 *
 * @author zhangdanji
 */
@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

    @Resource
    private PlayerDao playerDao;

    /**
     * 注册并登陆
     * @param session 会话
     * @param playerName 玩家姓名
     * @param password 密码
     * @return 玩家响应类
     *
     * **/
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    @Override
    public PlayerResponse registerAndLogin(Session session, String playerName, String password) {
        Player existsPlayer = playerDao.getPlayerByName(playerName);
        //玩家名被占用
        if(existsPlayer != null){
            throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
        }
        //创建新账号
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPassword(password);
        player.setLevel(1);
        player.setExp(0);
        player.setPlayerId(playerDao.insertPlayer(player));
        return login(session,player.getPlayerName(),player.getPassword());
    }

    /**
     * 登陆
     * @param session 会话
     * @param playerName 玩家姓名
     * @param password 密码
     * @return 玩家响应类
     *
     * **/
    @Override
    public PlayerResponse login(Session session, String playerName, String password) {
        //判断当前会话是否登录
        if(session.getAttachment() != null){
            throw new ErrorCodeException(ResultCode.HAS_LOGIN);
        }
        //玩家不存在
        Player player = playerDao.getPlayerByName(playerName);
        if(player == null){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
        }
        //密码错误
        if(!player.getPassword().equals(password)){
            throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
        }
        //判断是否在其他地方登录
        boolean isOnline = SessionManager.isOnlinePlayer(player.getPlayerId());
        //若已经登录则踢下线
        if(isOnline){
            Session loginSession = SessionManager.removeSession(player.getPlayerId());
            loginSession.removeAttachment();
            loginSession.close();
        }
        //返回请求
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerName(player.getPlayerName());
        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setLevel(player.getLevel());
        playerResponse.setExp(player.getExp());

        return playerResponse;
    }
}
