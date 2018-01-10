/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Invoker.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 方法执行器  
 */
package com.chezhibao.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法执行器
 *
 * @author zhangdanji
 */
public class Invoker {

    //目标对象
    private Object target;
    //方法
    private Method method;

    /**
     * 私有化构造方法
     *
     * **/
    private Invoker(Object target,Method method){
        this.target = target;
        this.method = method;
    }

    /**
     * 静态构造方法
     * @param target 目标对象
     * @param method 执行方法
     * @return 方法执行器
     *
     * **/
    public static Invoker valueOf(Object target,Method method){
        return new Invoker(target,method);
    }

    /**
     * 执行方法
     * @param params 参数
     * @return 方法返回对象
     *
     * **/
    public Object invoke(Object... params){
        try {
            return method.invoke(target, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getters、Setters
     *
     * **/
    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
