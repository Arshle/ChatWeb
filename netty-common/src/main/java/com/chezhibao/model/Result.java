/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Result.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 结果对象  
 */
package com.chezhibao.model;

import com.chezhibao.serializer.Serializer;
import com.chezhibao.value.ResultCode;

/**
 * 结果对象
 *
 * @author zhangdanji
 */
public class Result<T extends Serializer> {

    //结果码
    private int resultCode;
    //结果内容
    private T content;

    /**
     * 返回成功的结果对象
     * @param content 结果内容
     * @return 结果对象
     *
     * **/
    public static <T extends Serializer> Result<T> SUCCESS(T content){
        Result<T> result = new Result<>();
        result.resultCode = ResultCode.SUCCESS;
        result.content = content;
        return result;
    }

    /**
     * 返回成功无内容的结果对象
     * @return 结果对象
     *
     * **/
    public static <T extends Serializer> Result<T> SUCCESS(){
        Result<T> result = new Result<T>();
        result.resultCode = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 返回错误的结果对象
     * @param resultCode 结果码
     * @return 结果对象
     *
     * **/
    public static <T extends Serializer> Result<T> ERROR(int resultCode){
        Result<T> result = new Result<T>();
        result.resultCode = resultCode;
        return result;
    }

    /**
     * 静态构造方法
     * @param resultCode 结果码
     * @param content 内容
     * @return 结果对象
     *
     * **/
    public static <T extends Serializer> Result<T> valueOf(int resultCode, T content){
        Result<T> result = new Result<T>();
        result.resultCode = resultCode;
        result.content = content;
        return result;
    }

    /**
     * Getters、Setters
     *
     * **/
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess(){
        return this.resultCode == ResultCode.SUCCESS;
    }
}
