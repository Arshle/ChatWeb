/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ZookeeperWatcher.java
 * Author:   zhangdanji
 * Date:     2017年10月16日
 * Description: Zookeeper监视  
 */
package com.chezhibao.zookeeper.watcher;

import org.apache.zookeeper.*;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper监视
 *
 * @author zhangdanji
 */
public class ZookeeperWatcher implements Watcher {

    /**
     * zookeeper客户端
     * **/
    private ZooKeeper zooKeeper;
    /**
     * zookeeper连接同步工具
     * **/
    private final static CountDownLatch CONNECT = new CountDownLatch(1);

    /**
     * 创建与zookeeper的连接
     * **/
    public void createConnection(){
        this.releaseConnection();
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("zookeeper.properties");
            properties.load(is);
            zooKeeper = new ZooKeeper(properties.getProperty("zookeeper.connect.addr"),Integer.parseInt(properties.getProperty("zookeeper.session.timeout")),this);
            System.out.println("connect to zookeeper server...");
            CONNECT.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭zookeeper连接
     * **/
    public void releaseConnection(){
        try {
            if(zooKeeper != null){
                zooKeeper.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建zookeeper节点
     * @param path 节点路劲
     * @param data 节点内容
     * @param watch 是否需要监视
     * @return 是否成功
     *
     * **/
    public boolean createPath(String path,String data,boolean watch){
        try {
            zooKeeper.exists(path, watch);
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        Event.EventType type = event.getType();
        if(state == Event.KeeperState.SyncConnected){
            if(type == Event.EventType.None){
                CONNECT.countDown();
                System.out.println("connected success.");
            } else if(type == Event.EventType.NodeCreated){
                System.out.println("created node " + event.getPath());
            }
        }
    }

    public static void main(String[] args) {
        try {
            ZookeeperWatcher watcher = new ZookeeperWatcher();
            watcher.createConnection();

            watcher.createPath("/data", "zhangdanji", true);

            Thread.sleep(10000);
            watcher.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
