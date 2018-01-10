/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: JmsQueueSender.java
 * Author:   zhangdanji
 * Date:     2017年10月20日
 * Description: activeMQ队列消息发送
 */
package com.chezhibao.activemq.sender;

import com.chezhibao.parameter.ParameterUtils;
import com.chezhibao.spring.SpringUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import javax.jms.*;
import java.io.Serializable;

/**
 * @author zhangdanji
 * activeMQ队列消息发送
 */
public class JmsSender {

    private static final Logger logger = LoggerFactory.getLogger(JmsSender.class);
    private final static String JMS_DEFAULT_QUEUE_NAME = "activemq.default.queue";
    private final static String JMS_DEFAULT_TOPIC_NAME = "activemq.default.topic";
    private final static String JMS_GROUP_PROP_NAME = "JMSXGroupID";
    /**
     * jmsTemplate
     * **/
    private static JmsTemplate jmsTemplate;

    /**
     * 初始化jmsTemplate
     * **/
    static{
        init();
    }

    /**
     * 私有化构造函数
     * **/
    private JmsSender(){
        throw new RuntimeException("JmsSender.class cannot be constructed");
    }

    /**
     * 初始化加载
     * **/
    private static void init(){
        try {
            jmsTemplate = SpringUtils.getBean(JmsTemplate.class);
        } catch (BeansException e) {
            logger.error("jmsTemplate SpringBean Init Failed:{}",e);
            jmsTemplate = null;
        }
    }

    /**
     * 发送activemq消息到默认队列
     * @param message 消息内容
     * **/
    public static void sendTextMessage(String message){
        sendTextMessage(ParameterUtils.getParameter(JMS_DEFAULT_QUEUE_NAME),message);
    }

    /**
     * 发送activemq到指定队列名
     * @param queueName 队列名称
     * @param message 消息内容
     * **/
    public static void sendTextMessage(String queueName,String message){
        sendTextMessage(new ActiveMQQueue(queueName), message);
    }

    /**
     * 发送activemq到指定目的地
     * @param destination 目的地
     * @param message 消息内容
     * **/
    public static void sendTextMessage(Destination destination, final String message){
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(message);
                }
            });
        } catch (Exception e) {
            logger.error("TextMessage send failed:{}",e);
        }
    }

    /**
     * 发送对象消息
     * @param message 对象消息
     *
     * **/
    public static void sendObjectMessage(Serializable message){
        sendObjectMessage(ParameterUtils.getParameter(JMS_DEFAULT_QUEUE_NAME),message);
    }

    /**
     * 发送对象消息
     * @param queueName 队列名称
     * @param message 对象消息
     *
     * **/
    public static void sendObjectMessage(String queueName,Serializable message){
        sendObjectMessage(new ActiveMQQueue(queueName),message);
    }

    /**
     * 发送对象消息
     * @param destination 目的地
     * @param message 对象消息
     *
     * **/
    public static void sendObjectMessage(Destination destination, final Serializable message){
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(message);
                }
            });
        } catch (Exception e) {
            logger.error("Jms send failed:{}",e);
        }
    }

    /**
     * 发送文本主题
     * @param message 消息
     * **/
    public static void sendTextTopic(String message){
        sendTextTopic(ParameterUtils.getParameter(JMS_DEFAULT_TOPIC_NAME),message);
    }

    /**
     * 发送文本主题
     * @param topicName 主题名称
     * @param message 消息
     * **/
    public static void sendTextTopic(String topicName,String message){
        sendTextMessage(new ActiveMQTopic(topicName),message);
    }

    /**
     * 发送对象主题
     * @param message 对象信息
     * **/
    public static void sendObjectTopic(Serializable message){
        sendObjectTopic(ParameterUtils.getParameter(JMS_DEFAULT_TOPIC_NAME),message);
    }

    /**
     * 发送对象主题
     * @param topicName 主题名称
     * @param message 对象信息
     * **/
    public static void sendObjectTopic(String topicName,Serializable message){
        sendObjectMessage(new ActiveMQTopic(topicName),message);
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param message 消息
     * **/
    public static void sendGroupTextMessage(final String groupName,String message){
        sendGroupTextMessage(groupName,ParameterUtils.getParameter(JMS_DEFAULT_QUEUE_NAME),message);
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param queueName 队列名称
     * @param message 消息
     * **/
    public static void sendGroupTextMessage(final String groupName,String queueName,String message){
        sendGroupTextMessage(groupName,new ActiveMQQueue(queueName),message);
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param destination 目的地
     * @param message 消息
     * **/
    public static void sendGroupTextMessage(final String groupName, Destination destination, final String message){
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(message);
                    textMessage.setStringProperty(JMS_GROUP_PROP_NAME,groupName);
                    return textMessage;
                }
            });
        } catch (Exception e) {
            logger.error("Jms GroupTextMessage send failed:{}",e);
        }
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param message 对象消息
     * **/
    public static void sendGroupObjectMessage(String groupName,Serializable message){
        sendGroupObjectMessage(groupName,ParameterUtils.getParameter(JMS_DEFAULT_QUEUE_NAME),message);
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param queueName 队列名称
     * @param message 对象消息
     * **/
    public static void sendGroupObjectMessage(String groupName,String queueName,Serializable message){
        sendGroupObjectMessage(groupName,new ActiveMQQueue(queueName),message);
    }

    /**
     * 发送分组消息
     * @param groupName 分组名称
     * @param destination 目的地
     * @param message 对象消息
     * **/
    public static void sendGroupObjectMessage(final String groupName,Destination destination,final Serializable message){
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    ObjectMessage objectMessage = session.createObjectMessage(message);
                    objectMessage.setStringProperty(JMS_GROUP_PROP_NAME,groupName);
                    return objectMessage;
                }
            });
        } catch (Exception e) {
            logger.error("Jms GroupObjectMessage send failed:{}",e);
        }
    }
}
