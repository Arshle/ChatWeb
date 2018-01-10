/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: LoginUserDao.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 用户dao类  
 */
package com.chezhibao.user.mappers;

import com.chezhibao.entity.user.LoginUser;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 用户dao类
 *
 * @author zhangdanji
 */
@Repository("loginUserDao")
public interface LoginUserDao {

    /**
     * 根据条件获取用户
     * @param user 用户信息
     * @return 用户封装类
     * **/
    public LoginUser getUser(LoginUser user);

    /**
     * 根据用户名称获取用户列表
     * @param userName 用户名称
     * @return 用户集合
     * **/
    public List<LoginUser> getUsersByUserName(String userName);

    /**
     * 获取所有用户
     * @return 用户集合
     * **/
    public List<LoginUser> getAllUsers();

    /**
     * 新增用户
     * @param user 用户信息
     * @return 新增用户ID
     * **/
    public int addUser(LoginUser user);

    /**
     * 删除用户
     * @param user 用户信息
     * @return 删除用户数量
     * **/
    public int deleteUser(LoginUser user);

    /**
     * 修改用户
     * @param user 用户信息
     * @return 修改行数
     * **/
    public int updateUser(LoginUser user);
}
