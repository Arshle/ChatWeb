/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: AMQMessageListener.java
 * Author:   zhangdanji
 * Date:     2017年10月24日
 * Description: AMQ消息监听类  
 */
package com.chezhibao.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * AMQ消息监听类
 *
 * @author zhangdanji
 */
public class AMQMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(AMQMessageListener.class);

    @Override
    public void onMessage(Message message) {
        if(TextMessage.class.isInstance(message)){
            try {
                TextMessage textMessage = TextMessage.class.cast(message);
                logger.info("接收MQ消息:" + textMessage.getText());
            } catch (JMSException e) {
                logger.error("{}",e);
            }
        }
    }
}
