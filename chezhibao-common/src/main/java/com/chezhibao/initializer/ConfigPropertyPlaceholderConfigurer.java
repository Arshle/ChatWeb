/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ConfigPropertyPlaceholderConfigurer.java
 * Author:   zhangdanji
 * Date:     2017年10月23日
 * Description: 加载参数列表类  
 */
package com.chezhibao.initializer;

import com.chezhibao.utils.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 加载参数列表类
 *
 * @author zhangdanji
 */
public class ConfigPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(ConfigPropertyPlaceholderConfigurer.class);

    @Override
    public void setLocations(Resource... locations) {
        List<Resource> existsResource = new ArrayList<>(1);
        for(Resource resource : locations){
            try {
                if(resource.getURL().toString().contains("jar:file:")){
                    existsResource.add(resource);
                    continue;
                }
                if(resource.getFile().exists()){
                    existsResource.add(resource);
                }
            } catch (Exception e) {
                logger.error("load resource error : ",e);
            }
        }
        if(existsResource.size() > 0){
            Resource[] loadResources = new Resource[existsResource.size()];
            for(int i = 0; i < existsResource.size(); i ++){
                loadResources[i] = existsResource.get(i);
            }
            super.setLocations(loadResources);
        }
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        //将参数加载到参数列表中
        ParameterUtil.init();
        for(String name : props.stringPropertyNames()){
            ParameterUtil.loadParameter(name,props.getProperty(name));
        }

    }
}
