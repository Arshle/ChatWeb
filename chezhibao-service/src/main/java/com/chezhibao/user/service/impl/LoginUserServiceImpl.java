/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: LoginUserServiceImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 用户service实现类  
 */
package com.chezhibao.user.service.impl;

import com.chezhibao.code.BizCode;
import com.chezhibao.encrypt.Des;
import com.chezhibao.entity.user.LoginUser;
import com.chezhibao.user.mappers.LoginUserDao;
import com.chezhibao.user.service.LoginUserService;
import com.chezhibao.utils.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

/**
 * 用户service实现类
 *
 * @author zhangdanji
 */
@Service("loginUserService")
public class LoginUserServiceImpl implements LoginUserService{

    private final static Logger logger = LoggerFactory.getLogger(LoginUserServiceImpl.class);
    @Resource
    private LoginUserDao loginUserDao;

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    @Override
    public boolean userLogin(String loginAccount, String password) {
        try {
            logger.info("begin Invoke userLogin,params:{},{}",loginAccount,password);
            if(StringUtils.isEmpty(loginAccount) || StringUtils.isEmpty(password)){
                return false;
            }
            LoginUser login = new LoginUser();
            login.setLoginAccount(loginAccount);
            LoginUser user = loginUserDao.getUser(login);
            Redis.put(BizCode.user_login,"user_login",user);
            return user != null && password.equals(Des.decrypt(user.getPassword()));
        } catch (Exception e) {
            logger.error("userLogin error : {}",e);
            return false;
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    @Override
    public boolean addUser(LoginUser user) {
        if(!checkUser(user)){
            return false;
        }
        user.setPassword(Des.encrypt(user.getPassword()));
        return loginUserDao.addUser(user) > 0;
    }

    @Transactional
    @Override
    public boolean deleteUser(LoginUser user) {
        return false;
    }

    @Transactional
    @Override
    public boolean updateUser(LoginUser user) {
        return false;
    }

    @Override
    public List<LoginUser> getAllUsers() {
        return null;
    }

    /**
     * 校验用户信息
     * @param user 用户信息
     * @return 是否通过
     * **/
    private boolean checkUser(LoginUser user){
        return !(StringUtils.isEmpty(user.getLoginAccount())
                || StringUtils.isEmpty(user.getUserName())
                || StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getMobile()));
    }
}
