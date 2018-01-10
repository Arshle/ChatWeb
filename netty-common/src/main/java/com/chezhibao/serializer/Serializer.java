/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Serializer.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 自定义序列化类  
 */
package com.chezhibao.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化类
 *
 * @author zhangdanji
 */
public abstract class Serializer {

    //字符编码
    public static final Charset CHARSET = Charset.forName("UTF-8");
    //写buffer对象
    protected ByteBuf writeBuffer;
    //读buffer对象
    protected ByteBuf readBuffer;

    /**
     * 反序列化具体实现
     *
     */
    protected abstract void read();

    /**
     * 序列化具体实现
     *
     */
    protected abstract void write();

    /**
     * 从byte数组获取数据
     * @param bytes	读取的数组
     * @return 序列化对象
     *
     */
    public Serializer readFromBytes(byte[] bytes) {
        readBuffer = BufferFactory.getBuffer(bytes);
        read();
        readBuffer.clear();
        //释放buffer
        ReferenceCountUtil.release(readBuffer);
        return this;
    }

    /**
     * 从buff获取数据
     * @param readBuffer
     *
     */
    public void readFromBuffer(ByteBuf readBuffer) {
        this.readBuffer = readBuffer;
        read();
    }

    /**
     * 写入本地buff
     * @return byteBuf对象
     *
     */
    public ByteBuf writeToLocalBuff(){
        writeBuffer = BufferFactory.getBuffer();
        write();
        return writeBuffer;
    }

    /**
     * 写入目标buff
     * @param buffer 目标buf对象
     * @return 写入数据后的buf对象
     *
     */
    public ByteBuf writeToTargetBuff(ByteBuf buffer){
        writeBuffer = buffer;
        write();
        return writeBuffer;
    }

    /**
     * 返回buffer数组
     * @return 字节数组
     *
     */
    public byte[] getBytes() {
        writeToLocalBuff();
        byte[] bytes = null;
        if (writeBuffer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        writeBuffer.clear();
        //释放buffer
        ReferenceCountUtil.release(writeBuffer);
        return bytes;
    }

    /**
     * 读取列表
     * @param clazz 列表的泛型
     * @return 读取后的列表
     *
     * **/
    public <T> List<T> readList(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            list.add(read(clazz));
        }
        return list;
    }

    /**
     * 读取map
     * @param keyClazz 键对象类型
     * @param valueClazz 值对象类型
     * @return 读取后的map
     *
     * **/
    public <K,V> Map<K,V> readMap(Class<K> keyClazz, Class<V> valueClazz) {
        Map<K,V> map = new HashMap<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            K key = read(keyClazz);
            V value = read(valueClazz);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 读取对象
     * @param clazz 读取对象类型
     * @return 读取后的对象
     *
     * **/
    @SuppressWarnings("unchecked")
    public <I> I read(Class<I> clazz) {
        Object t = null;
        if ( clazz == int.class || clazz == Integer.class) {
            t = this.readInt();
        } else if (clazz == byte.class || clazz == Byte.class){
            t = this.readByte();
        } else if (clazz == short.class || clazz == Short.class){
            t = this.readShort();
        } else if (clazz == long.class || clazz == Long.class){
            t = this.readLong();
        } else if (clazz == float.class || clazz == Float.class){
            t = readFloat();
        } else if (clazz == double.class || clazz == Double.class){
            t = readDouble();
        } else if (clazz == String.class ){
            t = readString();
        } else if (Serializer.class.isAssignableFrom(clazz)){
            try {
                byte hasObject = this.readBuffer.readByte();
                if(hasObject == 1){
                    Serializer temp = (Serializer)clazz.newInstance();
                    temp.readFromBuffer(this.readBuffer);
                    t = temp;
                }else{
                    t = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new RuntimeException(String.format("不支持类型:[%s]", clazz));
        }
        return (I) t;
    }

    /**
     * 序列化map对象
     * @param map map参数
     * @return 序列化后的对象
     *
     * **/
    public <K,V> Serializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    /**
     * 序列化对象
     * @param object 序列化的对象
     * @return 序列化后的对象
     *
     * **/
    public Serializer writeObject(Object object) {

        if(object == null){
            writeByte((byte)0);
        }else{
            if (object instanceof Integer) {
                writeInt((int) object);
                return this;
            }

            if (object instanceof Long) {
                writeLong((long) object);
                return this;
            }

            if (object instanceof Short) {
                writeShort((short) object);
                return this;
            }

            if (object instanceof Byte) {
                writeByte((byte) object);
                return this;
            }

            if (object instanceof String) {
                String value = (String) object;
                writeString(value);
                return this;
            }
            if (object instanceof Serializer) {
                writeByte((byte)1);
                Serializer value = (Serializer) object;
                value.writeToTargetBuff(writeBuffer);
                return this;
            }

            throw new RuntimeException("不可序列化的类型:" + object.getClass());
        }

        return this;
    }

    /**
     * 是否为空
     * @param c 集合
     * @return 是否为空
     *
     * **/
    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    /**
     * 是否为空
     * @param c map对象
     * @return 是否为空
     *
     * **/
    public <K,V> boolean isEmpty(Map<K,V> c) {
        return c == null || c.size() == 0;
    }

    /**
     * 基本数据类型的读和写
     *
     * **/
    public byte readByte() {
        return readBuffer.readByte();
    }

    public short readShort() {
        return readBuffer.readShort();
    }

    public int readInt() {
        return readBuffer.readInt();
    }

    public long readLong() {
        return readBuffer.readLong();
    }

    public float readFloat() {
        return readBuffer.readFloat();
    }

    public double readDouble() {
        return readBuffer.readDouble();
    }

    public String readString() {
        int size = readBuffer.readInt();
        if (size <= 0) {
            return "";
        }

        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);
        return new String(bytes, CHARSET);
    }

    public Serializer writeByte(Byte value) {
        writeBuffer.writeByte(value);
        return this;
    }

    public Serializer writeShort(Short value) {
        writeBuffer.writeShort(value);
        return this;
    }

    public Serializer writeInt(Integer value) {
        writeBuffer.writeInt(value);
        return this;
    }

    public Serializer writeLong(Long value) {
        writeBuffer.writeLong(value);
        return this;
    }

    public Serializer writeFloat(Float value) {
        writeBuffer.writeFloat(value);
        return this;
    }

    public Serializer writeDouble(Double value) {
        writeBuffer.writeDouble(value);
        return this;
    }

    public <T> Serializer writeList(List<T> list) {
        if (isEmpty(list)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) list.size());
        for (T item : list) {
            writeObject(item);
        }
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeInt(0);
            return this;
        }
        byte data[] = value.getBytes(CHARSET);
        int len = data.length;
        writeBuffer.writeInt(len);
        writeBuffer.writeBytes(data);
        return this;
    }
}
