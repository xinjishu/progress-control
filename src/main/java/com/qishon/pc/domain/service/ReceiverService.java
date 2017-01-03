package com.qishon.pc.domain.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by xm on 2017/1/3.
 */
@Component
@RabbitListener(queues = "progressControl")
public class ReceiverService {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("ReceiverService : " + msg);
    }
}
