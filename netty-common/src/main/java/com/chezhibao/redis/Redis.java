/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Redis.java
 * Author:   zhangdanji
 * Date:     2017年10月07日
 * Description:   
 */
package com.chezhibao.redis;

import com.chezhibao.spring.SpringUtils;
import com.chezhibao.value.BizCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangdanji
 */
public class Redis {

    private final static Logger logger = LoggerFactory.getLogger(Redis.class);
    private final static String REDIS_CONFIG_LOCATION = "classpath:/spring/redis.xml";

    //redisTemplate
    private static RedisTemplate redisTemplate;

    static{

        //初始化redisTemplate
        init();
    }

    /**
     * 防止私有构造
     *
     * **/
    private Redis(){
        throw new RuntimeException("Redis.class cannot be constructed");
    }

    /**
     * 初始化redisTemplate
     *
     * **/
    @SuppressWarnings("unchecked")
    private static void init(){

        try {
            redisTemplate = SpringUtils.getBean(RedisTemplate.class);
        } catch (BeansException e) {
            logger.error("redisTemplate SpringBean Init Failed:" + e.getMessage());
            redisTemplate = null;
        }
    }

    /**
     * 测试redis是否可用
     * @return 是否可用
     *
     * **/
    @SuppressWarnings("unchecked")
    public static boolean isAvailable(){

        if(redisTemplate == null){
            return false;
        }

        try {
            redisTemplate.opsForValue().set("test","test",10,TimeUnit.SECONDS);
            return "test".equals(redisTemplate.opsForValue().get("test"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 放入缓存<string,object>
     * @param code 业务编号
     * @param key 键
     * @param value 值
     *
     * **/
    @SuppressWarnings({"StringBufferReplaceableByString", "unchecked"})
    public static void put(BizCode code, String key, Object value){
        put(code,key,value,365,TimeUnit.DAYS);
    }

    /**
     * 放入缓存<string,object>
     * @param code 业务编号
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void put(BizCode code, String key, Object value, long timeout){
        put(code,key,value,timeout,TimeUnit.SECONDS);
    }

    /**
     * 放入缓存<string,object>
     * @param code 业务编号
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void put(BizCode code, String key, Object value, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(initRedisKey(code,key),value,timeout,timeUnit);
    }

    /**
     * 获取redis值
     * @param code 业务编号
     * @param key 键
     *
     * **/
    public static Object get(BizCode code,String key){
        return redisTemplate.opsForValue().get(initRedisKey(code,key));
    }

    /**
     * 存取缓存为list数据结构的redis
     * @param code 业务编号
     * @param key 键
     * @param collection 集合
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void leftPutList(BizCode code, String key, Collection collection){
        redisTemplate.opsForList().leftPushAll(initRedisKey(code,key),collection);
        expire(code,key,365,TimeUnit.DAYS);
    }

    /**
     * 获取列表下标的对象
     * @param code 业务编号
     * @param key 键
     * @param index 下标
     * @return 对应下标的对象
     *
     * **/
    @SuppressWarnings("unchecked")
    public static Object index(BizCode code, String key, long index){
        return redisTemplate.opsForList().index(initRedisKey(code,key),index);
    }

    /**
     * 获取列表范围的对象
     * @param code 业务编号
     * @param key 键
     * @param start 下标开始
     * @param end 下标结束
     * @return 对应下标的范围的列表
     *
     * **/
    @SuppressWarnings("unchecked")
    public static List<Object> range(BizCode code, String key, long start, long end){
        return redisTemplate.opsForList().range(initRedisKey(code,key),start,end);
    }

    /**
     * 获取列表长度
     * @param code 业务编号
     * @param key 键
     * @return 长度
     *
     * **/
    @SuppressWarnings("unchecked")
    public static long size(BizCode code, String key){
        return redisTemplate.opsForList().size(initRedisKey(code, key));
    }

    /**
     * 从左侧弹出列表对象
     * @param code 业务编号
     * @param key 键
     * @return 左侧的对象
     *
     * **/
    @SuppressWarnings("unchecked")
    public static Object leftPop(BizCode code, String key){
        return redisTemplate.opsForList().leftPop(initRedisKey(code, key));
    }

    /**
     * 延时弹出左侧列表对象
     * @param code 业务编号
     * @param key 键
     * @param timeout 时间
     * @param timeUnit 时间单位
     *
     * **/
    @SuppressWarnings("unchecked")
    public static Object leftPop(BizCode code, String key, long timeout, TimeUnit timeUnit){
        return redisTemplate.opsForList().leftPop(initRedisKey(code, key), timeout, timeUnit);
    }

    /**
     * 删除列表元素的节点
     * @param code 业务编号
     * @param key 键
     * @param index 下标
     * @param arg 参数
     * @return 删除的下标
     *
     * **/
    @SuppressWarnings("unchecked")
    public static long remove(BizCode code, String key, long index, Object arg){
        return redisTemplate.opsForList().remove(initRedisKey(code,key),index,arg);
    }

    /**
     * 存取列表节点
     * @param code 业务编号
     * @param key 键
     * @param node 节点
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void leftPut(BizCode code, String key, Object node){
        redisTemplate.opsForList().leftPush(initRedisKey(code,key),node);
        expire(code, key, 365, TimeUnit.DAYS);
    }

    /**
     * 存取所有节点
     * @param code 业务编号
     * @param key 键
     * @param nodes 节点
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void leftPutAll(BizCode code, String key, Object... nodes){
        redisTemplate.opsForList().leftPushAll(initRedisKey(code,key),nodes);
        expire(code, key, 365, TimeUnit.DAYS);
    }

    /**
     * 是否存在键
     * @param code 业务编号
     * @param key 键
     * @return 是否存在
     *
     * **/
    @SuppressWarnings("unchecked")
    public static boolean exists(BizCode code, String key){
        return redisTemplate.hasKey(initRedisKey(code,key));
    }

    /**
     * 删除redis键
     * @param code 业务编号
     * @param key 键
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void delete(BizCode code, String key){
        redisTemplate.delete(initRedisKey(code,key));
    }

    /**
     * 批量删除redis键
     * @param code 业务编码
     * @param keys 删除键的集合
     *
     * **/
    @SuppressWarnings("unchecked")
    public static void deleteAll(BizCode code, Collection<String> keys){
        List<String> keyList = new ArrayList<>(keys);
        for(String key : keys){
            keyList.add(initRedisKey(code,key));
        }
        redisTemplate.delete(keys);
    }

    /**
     * 获取超时时间
     * @param code 业务编码
     * @param key 键
     * @return 时间，单位秒
     *
     * **/
    public static long getExpired(BizCode code,String key){
        return getExpired(code, key, TimeUnit.SECONDS);
    }

    /**
     * 获取超时时间
     * @param code 业务编码
     * @param key 键
     * @param timeUnit 时间单位
     * @return 时间
     *
     * **/
    @SuppressWarnings("unchecked")
    public static long getExpired(BizCode code, String key, TimeUnit timeUnit){
        return redisTemplate.getExpire(initRedisKey(code,key),timeUnit);
    }

    /**
     * 设置超时时间
     * @param code 业务编号
     * @param key 键
     * @param timeout 超时时间
     * @return 是否成功
     *
     * **/
    public static boolean expire(BizCode code,String key,long timeout){
        return expire(code, key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置超时时间
     * @param code 业务编号
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 是否成功
     *
     * **/
    @SuppressWarnings("unchecked")
    public static boolean expire(BizCode code, String key, long timeout, TimeUnit timeUnit){
        return redisTemplate.expire(initRedisKey(code, key), timeout, timeUnit);
    }

    /**
     * 执行callback命令
     * @param callback 回调借口
     * @return 执行返回对象
     *
     * **/
    @SuppressWarnings("unchecked")
    public static <T> T exec(SessionCallback<T> callback){
        return (T)redisTemplate.execute(callback);
    }

    /**
     * 生成redis键
     * @param code 业务编号
     * @param key 键
     * @return 生成后的redis键
     *
     * **/
    @SuppressWarnings("StringBufferReplaceableByString")
    private static String initRedisKey(BizCode code, String key){
        return new StringBuffer(code.getCode()).append("_").append(key).toString();
    }
}
