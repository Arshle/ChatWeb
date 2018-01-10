/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SocketCmd.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 命令号注解  
 */
package com.chezhibao.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令号注解
 *
 * @author zhangdanji
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCmd {

    /**
     * 命令号注解
     * @return 命令号
     *
     * **/
    int cmd();
}
