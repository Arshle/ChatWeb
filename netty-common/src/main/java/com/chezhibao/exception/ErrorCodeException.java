/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ErrorCodeException.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 状态错误异常  
 */
package com.chezhibao.exception;

/**
 * 状态错误异常
 *
 * @author zhangdanji
 */
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -8467442268867558458L;

    private final int errorCode;

    public ErrorCodeException(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
