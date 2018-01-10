/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: LoginUser.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 用户实体类  
 */
package com.chezhibao.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author zhangdanji
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 8977313569341852532L;

    /**
     * 用户ID
     * **/
    private int id;
    /**
     * 用户名称
     * **/
    private String userName;
    /**
     * 登录名
     * **/
    private String loginAccount;
    /**
     * 密码
     * **/
    private String password;
    /**
     * 生日
     * **/
    private Date birthday;
    /**
     * 等级
     * **/
    private int level;
    /**
     * 经验值
     * **/
    private int exp;
    /**
     * 最后一次登陆时间
     * **/
    private Date lastLoginTime;
    /**
     * 创建时间
     * **/
    private Date createTime;
    /**
     * 更新时间
     * **/
    private Date updateTime;
    /**
     * 兴趣爱好
     * **/
    private String interests;
    /**
     * 身份证号
     * **/
    private String identity;
    /**
     * 手机号
     * **/
    private String mobile;

    /**
     * Getters、Setters
     * **/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
