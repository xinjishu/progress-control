package com.qishon.pc.domain.service;

import com.qishon.pc.domain.util.JointUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by yongwei.chen on 2017/1/3.
 */
@RabbitListener(queues = "jointReceiver")
public class JointReceiverService {
    @Value("run.path")
    private String runPath;

    public void process(String msg){
        String[] strArr = msg.split(",");
        int girardId = Integer.parseInt(strArr[0]);
        String modelName = strArr[1];
        String taskName = strArr[2];
        String filePath  = strArr[3];
        String path= runPath + "\\" + filePath;
        boolean isX=true;
        JointUtil.SplitJoint(path,isX);
    }
}
