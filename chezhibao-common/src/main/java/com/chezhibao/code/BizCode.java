/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: BizCode.java
 * Author:   zhangdanji
 * Date:     2017年10月24日
 * Description: 业务编号  
 */
package com.chezhibao.code;

/**
 * 业务编号
 *
 * @author zhangdanji
 */
public enum BizCode {
    user_login("0001"),
    user_logout("0002");

    private String code;

    BizCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
