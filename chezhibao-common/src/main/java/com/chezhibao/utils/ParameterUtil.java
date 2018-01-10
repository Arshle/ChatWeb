/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ParameterUtil.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 参数工具类  
 */
package com.chezhibao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 参数工具类
 *
 * @author zhangdanji
 */
public class ParameterUtil {

    private static final Logger logger = LoggerFactory.getLogger(ParameterUtil.class);

    /**
     * 参数列表集合
     * **/
    private static final ConcurrentMap<String, String> PARAMS = new ConcurrentHashMap<>();

    /**
     * 私有化构造函数
     * **/
    private ParameterUtil(){
        throw new RuntimeException("ParameterUtil.class cannot be constructed.");
    }

    /**
     * 初始化参数列表
     * **/
    public static void init(){
        PARAMS.clear();
    }

    /**
     * 加载参数
     * @param paramName 参数名称
     * @param paramValue 参数值
     * **/
    public static void loadParameter(String paramName,String paramValue){
        try {
            PARAMS.put(paramName, paramValue);
            logger.info("load parameter : " + paramName + " -->" + paramValue );
        } catch (Exception e) {
            logger.error("load parameter failed : {}",e);
        }
    }

    /**
     * 获取参数
     * @param name 参数名
     * @return 参数值
     * **/
    public static String getParameter(String name){
        return PARAMS.get(name);
    }
}
