/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: DateFormat.java
 * Author:   zhangdanji
 * Date:     2017年10月26日
 * Description: 日期格式字符串  
 */
package com.chezhibao.code;

/**
 * 日期格式字符串
 *
 * @author zhangdanji
 */
public enum DateFormat {
    YMD("yyyy-MM-dd"),
    YMDHMS("yyyy-MM-dd HH:mm:ss");

    private String code;

    DateFormat(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
