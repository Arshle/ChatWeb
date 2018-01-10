/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: AMQMessageListener.java
 * Author:   zhangdanji
 * Date:     2017年10月20日
 * Description: activeMQ消息接收  
 */
package com.chezhibao.activemq.listener;

import com.chezhibao.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.*;

/**
 * activeMQ消息接收
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
                logger.info("接受mq队列消息:" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if(ObjectMessage.class.isInstance(message)){
            try {
                ObjectMessage objectMessage = ObjectMessage.class.cast(message);
                Player player = (Player) objectMessage.getObject();
                logger.info("接收mq队列消息:" + player.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
