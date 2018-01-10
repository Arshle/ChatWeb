/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerDao.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家dao类  
 */
package com.chezhibao.mappers;

import com.chezhibao.entity.Player;
import org.springframework.stereotype.Repository;

/**
 * 玩家dao类
 *
 * @author zhangdanji
 */
@Repository("playerDao")
public interface PlayerDao {

    /**
     * 根据id获取玩家
     * @param playerId 玩家ID
     * @return 玩家对象
     *
     * **/
    public Player getPlayerById(int playerId);

    /**
     * 根据玩家名称获取玩家
     * @param playerName 玩家姓名
     * @return 玩家对象
     *
     * **/
    public Player getPlayerByName(String playerName);

    /**
     * 创建玩家
     * @param player 玩家对象
     * @return 创建的玩家对象
     *
     * **/
    public int insertPlayer(Player player);
}
