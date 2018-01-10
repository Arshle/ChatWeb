/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: BizCode.java
 * Author:   zhangdanji
 * Date:     2017年10月07日
 * Description:   
 */
package com.chezhibao.value;

/**
 * @author zhangdanji
 */
public enum BizCode {

    login_user("0001");

    BizCode(String code){
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
