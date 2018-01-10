/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ZookeeperBase.java
 * Author:   zhangdanji
 * Date:     2017年10月14日
 * Description: zookeeper基本连接类  
 */
package com.chezhibao.zookeeper.base;

import org.apache.zookeeper.*;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper基本连接类
 *
 * @author zhangdanji
 */
public class ZookeeperBase {

    private final static CountDownLatch CONNECT = new CountDownLatch(1);

    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = ZookeeperBase.class.getClassLoader().getResourceAsStream("zookeeper.properties");
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(properties.getProperty("zookeeper.connect.addr"), Integer.parseInt(properties.getProperty("zookeeper.session.timeout")), new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.KeeperState state = watchedEvent.getState();
                    Event.EventType type = watchedEvent.getType();
                    if(state == Event.KeeperState.SyncConnected && type == Event.EventType.None){
                        CONNECT.countDown();
                        System.out.println("connected to zookeeper...");
                    }
                }
            });
            CONNECT.await();
            System.out.println(zk.create("/data", "zhangdanji".getBytes("UTF-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));

            byte[] data = zk.getData("/data", false, null);
            System.out.println(new String(data));

            Thread.sleep(10000);
            zk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
