/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ZkClientBase.java
 * Author:   zhangdanji
 * Date:     2017年10月16日
 * Description:   
 */
package com.chezhibao.zkclient.base;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.ZkLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhangdanji
 */
public class ZkClientBase {
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = ZkClientBase.class.getClassLoader().getResourceAsStream("zookeeper.properties");
            properties.load(is);
            ZkClient zkClient = new ZkClient(new ZkConnection(properties.getProperty("zookeeper.connect.addr")),Integer.parseInt(properties.getProperty("zookeeper.session.timeout")));
            zkClient.createEphemeral("/temp","zhangdanji");
            zkClient.createPersistent("/data","zhangdanji");
            ZkLock eventLock = zkClient.getEventLock();


            zkClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
