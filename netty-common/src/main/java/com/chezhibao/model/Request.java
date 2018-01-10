/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Request.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 请求对象  
 */
package com.chezhibao.model;

/**
 * 请求对象
 *
 * @author zhangdanji
 */
public class Request {

    //模块号
    private int module;
    //命令号
    private int cmd;
    //数据包
    private byte[] data;

    /**
     * 私有化构造方法
     *
     * **/
    private Request(int module,int cmd,byte[] data){
        this.module = module;
        this.cmd = cmd;
        this.data = data;
    }

    /**
     * 静态构造方法
     * @param module 模块号
     * @param cmd 命令号
     * @param data 数据内容
     * @return 请求对象
     *
     * **/
    public static Request valueOf(int module,int cmd,byte[] data){
        return new Request(module, cmd, data);
    }

    /**
     * Getters、Setters
     *
     * **/
    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
