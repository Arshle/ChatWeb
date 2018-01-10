/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: CuratorLock.java
 * Author:   zhangdanji
 * Date:     2017年10月16日
 * Description:   
 */
package com.chezhibao.curator.lock;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhangdanji
 */
public class CuratorLock {
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = CuratorLock.class.getClassLoader().getResourceAsStream("zookeeper.properties");
            properties.load(is);
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(200,3);
            CuratorFramework curator = CuratorFrameworkFactory.newClient(properties.getProperty("zookeeper.connect.addr"),retryPolicy);
            curator.start();
            CuratorZookeeperClient zookeeperClient = curator.getZookeeperClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
