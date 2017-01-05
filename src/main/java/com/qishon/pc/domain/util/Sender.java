package com.qishon.pc.domain.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by xm on 2016/12/25.
 */
@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送信息
     * @param girardId 款号Id
     * @param modelName 模型名称
     * @param taskName 执行的任务
     */
    public void send(int girardId,String modelName,String taskName,String sendType) {
        String stepsName = GirardEnum.findByKey(girardId).getFileName();
        String context = girardId+","+stepsName+"," + modelName + ","+taskName+","+sendType;
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(sendType, context);
    }
}