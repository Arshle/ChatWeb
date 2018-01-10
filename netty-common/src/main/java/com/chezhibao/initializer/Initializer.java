/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Initializer.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 项目启动类  
 */
package com.chezhibao.initializer;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.chezhibao.redis.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * 项目启动类
 *
 * @author zhangdanji
 */
public class Initializer extends HttpServlet {

    private static final long serialVersionUID = 5114673664493168056L;
    private static Logger logger;
    //优先读取linux下日志
    private static final String LINUX_CONFIG_PATH = "/opt/logback/";
    //默认项目下文件名
    private static final String DEFAULT_LOGBACK_NAME = "logback.xml";

    @Override
    public void init() throws ServletException {
        /*logback初始化*/
        LoggerContext loggerContext = null;
        //初始化logback上下文环境
        if(LoggerContext.class.isInstance(LoggerFactory.getILoggerFactory())){
            loggerContext = LoggerContext.class.cast(LoggerFactory.getILoggerFactory());
        }
        if(loggerContext == null){
            loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        }
        try {
            //加载logback配置
            JoranConfigurator configurator = new JoranConfigurator();
            //清除原logback状态
            loggerContext.getStatusManager().clear();
            loggerContext.reset();
            //设置上下文
            configurator.setContext(loggerContext);
            //优先加载/opt/logback下,其次本地编译目录下
            File logbackFile = new File(LINUX_CONFIG_PATH + this.getInitParameter("logbackFileName"));
            if(!logbackFile.exists()){
                logbackFile = new File(this.getClass().getClassLoader().getResource("/").getPath() + DEFAULT_LOGBACK_NAME);
            }
            configurator.doConfigure(logbackFile);
            logger = LoggerFactory.getLogger(Initializer.class);
            logger.info("logback init success");
        } catch (Exception e) {
            if(logger == null){
                logger = LoggerFactory.getLogger(Initializer.class);
            }
            logger.error("logback init failed");
        }

        logger.info("redis is ok:" + Redis.isAvailable());
    }

    @Override
    public void destroy() {
        if(logger == null){
            logger = LoggerFactory.getLogger(Initializer.class);
        }

        super.destroy();

        if (logger.isInfoEnabled())
        {
            logger.info("web destroy begin");
        }
    }
}
