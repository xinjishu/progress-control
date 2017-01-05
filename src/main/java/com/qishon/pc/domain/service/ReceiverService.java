package com.qishon.pc.domain.service;

import com.aliyun.openservices.oss.OSSClient;
import com.qishon.pc.domain.util.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by xm on 2017/1/3.
 */
@Component
@RabbitListener(queues = "progressControl")
public class ReceiverService {
    @Value("OSS.ACCESS_ID")
    private String ACCESS_ID;
    @Value("OSS.OSS_ENDPOINT")
    private String OSS_ENDPOINT;
    @Value("OSS.ACCESS_KEY")
    private String ACCESS_KEY;

    @RabbitHandler
    public void process(String msg) {
        System.out.println("ReceiverService : " + msg);
        String[] strArr = msg.split(",");
        int girardId = Integer.parseInt(strArr[0]);
        String modelName = strArr[1];
        String taskName = strArr[2];
        String filePath  = strArr[3];
        if(taskName.equals("渲染")){

        }else if(taskName.equals("压缩")){

        }else if(taskName.equals("上传文件服务器")){
            //阿里云BUCKET_NAME  OSS
            String BUCKET_NAME = "qishonpic";
            String Objectkey = "abc/123.jpg";
            String uploadFilePath = "D:\\pic\\upload\\photo2.jpg";
            String downloadFilePath = "D:\\pic\\DOWNLOAD11\\photo2.jpg";
            String uploadRoot = "D:\\ANTA\\DEMO\\DEMO\\DEMO\\ANTA24";
            String downloadRoot = "D:\\pic\\download";
            System.out.print(uploadRoot);
            OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
            File Root = new File(uploadRoot);
            System.out.print("\nenter showDir");
            OSSApplication.showDir(client,Root);
        }else if(taskName.equals("保存到数据库")){

        }
    }
}
