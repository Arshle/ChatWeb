/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: DateUtil.java
 * Author:   zhangdanji
 * Date:     2017年10月26日
 * Description: 日期管理类  
 */
package com.chezhibao.utils;

import com.chezhibao.code.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期管理类
 *
 * @author zhangdanji
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 字符串转日期类型
     * @param date 日期字符串
     * @return 日期
     * **/
    public static Date parseDate(String date){
        return parseDate(date, DateFormat.YMDHMS);
    }

    /**
     * 字符串转日期类型
     * @param pattern 格式
     * @param date 日期字符串
     * @return 日期
     * **/
    public static Date parseDate(String date,DateFormat pattern){
        try {
            return new SimpleDateFormat(pattern.getCode()).parse(date);
        } catch (ParseException e) {
            logger.error("parse date error :",e);
        }
        return null;
    }
}
