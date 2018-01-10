/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: UserLoginController.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 用户登陆controller  
 */
package com.chezhibao.controller.login;

import com.chezhibao.entity.user.LoginUser;
import com.chezhibao.user.service.LoginUserService;
import com.chezhibao.utils.DateUtil;
import com.chezhibao.utils.JmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登陆controller
 *
 * @author zhangdanji
 */
@Controller("userOptionController")
@RequestMapping("/user")
public class UserOptionController {

    private final Logger logger = LoggerFactory.getLogger(UserOptionController.class);

    @Resource
    private LoginUserService loginUserService;

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "/page/register.jsp";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> register(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try {
            String loginAccount = request.getParameter("loginAccount");
            String password = request.getParameter("password");
            String interests = request.getParameter("interests");
            String birthday = request.getParameter("birthday");
            String userName = request.getParameter("userName");
            String identity = request.getParameter("identity");
            String mobile = request.getParameter("mobile");
            LoginUser user = new LoginUser();
            user.setLoginAccount(loginAccount);
            user.setPassword(password);
            user.setUserName(userName);
            user.setInterests(interests);
            user.setIdentity(identity);
            user.setMobile(mobile);
            user.setBirthday(DateUtil.parseDate(birthday));
            if(loginUserService.addUser(user)){
                result.put("flag", true);
            }else{
                result.put("flag", false);
            }
        } catch (Exception e) {
            logger.error("register error:",e);
        }
        return result;
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> userLogin(String loginAccount,String password){
        Map<String, Object> result = new HashMap<>();
        result.put("flag", false);
        try {
            if(loginUserService.userLogin(loginAccount,password)) {
                JmsSender.sendTextMessage("用户" + loginAccount + "登录成功");
                result.put("flag", true);
            }
        } catch (Exception e) {
            logger.error("{}",e);
        }
        return result;
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess(){
        return "/page/success.jsp";
    }
}
