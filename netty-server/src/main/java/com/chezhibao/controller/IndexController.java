/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: IndexController.java
 * Author:   zhangdanji
 * Date:     2017年10月20日
 * Description: 首页controller  
 */
package com.chezhibao.controller;

import com.chezhibao.activemq.sender.JmsSender;
import com.chezhibao.entity.Player;
import com.chezhibao.parameter.ParameterUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页controller
 *
 * @author zhangdanji
 */
@Controller("indexController")
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/send")
    @ResponseBody
    public Map<String, Object> sendAMQMessage(String message) {
        JmsSender.sendTextTopic(message);
        Map<String, Object> result = new HashMap<>(1);
        result.put("flag", "success");
        return result;
    }

    @RequestMapping("/sendObject")
    @ResponseBody
    public Map<String,Object> sendObjectMessage(String playerName,String password){
        Player player = new Player();
        player.setExp(0);
        player.setLevel(1);
        player.setPlayerId(1);
        player.setPlayerName(playerName);
        player.setPassword(password);
        JmsSender.sendGroupObjectMessage("A",player);
        return null;
    }
}
