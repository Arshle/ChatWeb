/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: BufferTest.java
 * Author:   zhangdanji
 * Date:     2017年11月28日
 * Description:   
 */
package com.chezhibao.nio;

import java.nio.IntBuffer;

/**
 * @author zhangdanji
 */
public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(12);
        intBuffer.put(15);
        intBuffer.put(22);
        intBuffer.flip();
        System.out.println(intBuffer);
        System.out.println(intBuffer.get(0));
        System.out.println(intBuffer.get(intBuffer.limit()-1));
        intBuffer.rewind();
        System.out.println(intBuffer);
        System.out.println(intBuffer.get(1));
        intBuffer.reset();
        System.out.println(intBuffer);
    }
}
