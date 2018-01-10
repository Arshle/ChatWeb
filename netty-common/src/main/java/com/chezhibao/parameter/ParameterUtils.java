/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ParameterUtils.java
 * Author:   zhangdanji
 * Date:     2017年10月21日
 * Description: 参数公共工具  
 */
package com.chezhibao.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数公共工具
 *
 * @author zhangdanji
 */
public class ParameterUtils{

    private static final Logger logger = LoggerFactory.getLogger(ParameterUtils.class);
    /**
     * 参数集合
     * **/
    private final static Map<String, String> PARAMS = new HashMap<>();

    /**
     * 私有化构造函数
     * **/
    private ParameterUtils(){
        throw new RuntimeException("ParameterUtils.class cannot be constructed.");
    }

    /**
     * 初始化参数列表
     * **/
    public static void init(){
        PARAMS.clear();
    }

    /**
     * 加载参数列表
     * **/
    public static void loadParameter(String paramName,String paramValue){
        logger.info("load parameter:" + paramName + "->" + paramValue);
        ParameterUtils.PARAMS.put(paramName, paramValue);
    }

    /**
     * 获取参数
     * @param name 参数名称
     * @return 参数
     * **/
    public static String getParameter(String name){
        return PARAMS.get(name);
    }
}
