package cn.gw.demo2.service.impl;


import cn.gw.demo2.service.MessageSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 往rabbit中发送延时消息
 */
@Service
public class MessageSendServiceImpl implements MessageSendService {

    private Logger logger = LoggerFactory.getLogger(MessageSendServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 插件延时队列
     *
     * @param msgBody
     * @param exchange
     * @param timeOut
     */
    @Override
    public void sendMessage(String msgBody, String exchange, int timeOut) {
        logger.info("推送延时rabbit消息...");
        rabbitTemplate.convertAndSend(exchange, "#", msgBody, message -> {
            if (timeOut > 0) {
                message.getMessageProperties().setHeader("x-delay", timeOut);
            }
            return message;
        });
    }


}
