/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Order.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import java.io.Serializable;

/**
 * @author zhangdanji
 */
public class Order implements Serializable,Comparable<Order>{
    private static final long serialVersionUID = -6639250784025357058L;
    private int id;
    private String createTime;
    private Car car;
    private String serviceName;
    private String destName;

    @Override
    public int compareTo(Order o) {
        return o == null || o.getId() == 0 ? 1 : Integer.compare(this.id,o.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }
}
