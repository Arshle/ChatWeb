/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PropertyPlaceholderConfigurer.java
 * Author:   zhangdanji
 * Date:     2017年10月21日
 * Description: 配置文件位置信息  
 */
package com.chezhibao.initializer;

import com.chezhibao.parameter.ParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 配置文件位置信息
 *
 * @author zhangdanji
 */
public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer implements ServletContextAware{

    private final static Logger logger = LoggerFactory.getLogger(PropertyPlaceholderConfigurer.class);
    /**
     * 配置文件位置
     * **/
    private Resource[] locations;
    private ServletContext servletContext;

    @Override
    public void setLocations(Resource[] locations) {
        this.locations = locations;

        List<Resource> resources = new ArrayList<>(1);
        for(Resource resource : locations){
            try {
                if(!resource.getFile().exists()){
                    continue;
                }
                resources.add(resource);
            } catch (IOException e) {
                logger.error("load parameter error:" + e.getMessage());
            }
        }
        Resource[] loadResources = new Resource[resources.size()];
        for(int i = 0; i < resources.size(); i ++){
            try {
                FileSystemResource resource = new FileSystemResource(resources.get(i).getFile());
                loadResources[i] = resource;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.setLocations(loadResources);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ParameterUtils.init();
        for(String name : props.stringPropertyNames()){
            ParameterUtils.loadParameter(name,props.getProperty(name));
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
