/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Car.java
 * Author:   zhangdanji
 * Date:     2017年11月24日
 * Description:   
 */
package com.chezhibao.thread.base;

import java.io.Serializable;

/**
 * @author zhangdanji
 */
public class Car implements Serializable,Comparable<Car>{
    private static final long serialVersionUID = -8459133620069211498L;
    private int id;
    private String brand;
    private String model;
    private String type;
    private String bak;
    private String customerName;

    @Override
    public int compareTo(Car o) {
        return o == null || o.getId() == 0 ? 1 : Integer.compare(this.id,o.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
