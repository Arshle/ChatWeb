/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: InvokerHolder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 方法执行器储存类  
 */
package com.chezhibao.invoker;

import java.util.HashMap;
import java.util.Map;

/**
 * 方法执行器储存类
 *
 * @author zhangdanji
 */
public class InvokerHolder {

    //方法执行器
    private static final Map<Integer, Map<Integer, Invoker>> invokers = new HashMap<>();

    /**
     * 加入执行器
     * @param module 模块号
     * @param cmd 命令号
     * @param invoker 执行器
     *
     * **/
    public static void addInvoker(int module,int cmd,Invoker invoker){
        Map<Integer, Invoker> map = invokers.get(module);
        if(map == null){
            map = new HashMap<>();
            invokers.put(module, map);
        }
        map.put(cmd, invoker);
    }

    /**
     * 获取命令调用
     * @param module
     * @param cmd
     */
    public static Invoker getInvoker(int module, int cmd){
        Map<Integer, Invoker> map = invokers.get(module);
        if(map != null){
            return map.get(cmd);
        }
        return null;
    }
}
