package com.qishon.pc.domain.service;

import com.aliyun.openservices.oss.OSSClient;
import com.qishon.pc.domain.util.OSSApplication;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * Created by yongwei.chen on 2017/1/3.
 */
@RabbitListener(queues = "uploadReceiver")
public class UploadReceiverService {
    @Value("OSS.ACCESS_ID")
    private String ACCESS_ID;
    @Value("OSS.OSS_ENDPOINT")
    private String OSS_ENDPOINT;
    @Value("OSS.ACCESS_KEY")
    private String ACCESS_KEY;
    public void process(){
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
    }
}
