/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SpringUtils.java
 * Author:   zhangdanji
 * Date:     2017年10月21日
 * Description: Spring管理类  
 */
package com.chezhibao.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring管理类
 *
 * @author zhangdanji
 */
@Component("springUtil")
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring上下文环境
     * **/
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if(context == null){
            context = applicationContext;
        }
    }

    /**
     * 从spring上下文中获取bean
     * @param beanName bean的名称
     * @return bean对象
     * **/
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanType){
        return context.getBean(beanType);
    }
}
