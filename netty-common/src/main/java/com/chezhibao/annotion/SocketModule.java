/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SocketModule.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 模块号注解  
 */
package com.chezhibao.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块号注解
 *
 * @author zhangdanji
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {

    /**
     * 模块号
     * @return 模块号
     *
     * **/
    int module();
}
