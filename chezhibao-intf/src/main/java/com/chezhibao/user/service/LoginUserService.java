/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: UserLoginService.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 用户登陆service接口  
 */
package com.chezhibao.user.service;

import com.chezhibao.entity.user.LoginUser;
import java.util.List;

/**
 * 用户登陆service接口
 *
 * @author zhangdanji
 */
public interface LoginUserService {

    /**
     * 用户登陆
     * @param loginAccount 登陆用户名
     * @param password 密码
     * @return 是否登录成功
     * **/
    public boolean userLogin(String loginAccount, String password);

    /**
     * 新增用户
     * @param user 用户信息
     * @return 是否新增成功
     *
     * **/
    public boolean addUser(LoginUser user);

    /**
     * 删除用户
     * @param user 用户信息
     * @return 是否删除成功
     * **/
    public boolean deleteUser(LoginUser user);

    /**
     * 修改用户
     * @param user 用户信息
     * @return 是否修改成功
     * **/
    public boolean updateUser(LoginUser user);

    /**
     * 获取所有用户列表
     * @return 用户列表
     * **/
    public List<LoginUser> getAllUsers();
}
