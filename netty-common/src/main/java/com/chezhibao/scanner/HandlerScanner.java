/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: HandlerScanner.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 执行方法扫描类  
 */
package com.chezhibao.scanner;

import com.chezhibao.annotion.SocketCmd;
import com.chezhibao.annotion.SocketModule;
import com.chezhibao.invoker.Invoker;
import com.chezhibao.invoker.InvokerHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 执行方法扫描类
 *
 * @author zhangdanji
 */
@Component("handlerScanner")
public class HandlerScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //获取当前类所有接口
        Class<? extends Object> clazz = bean.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();

        if(interfaces != null && interfaces.length > 0){
            //扫描类的所有接口父类
            for (Class<?> interFace : interfaces) {
                //判断是否为socketModule接口类
                SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
                if (socketModule == null) {
                    continue;
                }

                //找出命令方法
                Method[] methods = interFace.getMethods();
                if(methods != null && methods.length > 0){
                    for(Method method : methods){
                        SocketCmd socketCmd = method.getAnnotation(SocketCmd.class);
                        if(socketCmd == null){
                            continue;
                        }

                        int module = socketModule.module();
                        int cmd = socketCmd.cmd();

                        if(InvokerHolder.getInvoker(module, cmd) == null){
                            InvokerHolder.addInvoker(module, cmd, Invoker.valueOf(bean, method));
                        }else{
                            throw new RuntimeException("Duplicate Invoker");
                        }
                    }
                }

            }
        }
        return bean;
    }
}
