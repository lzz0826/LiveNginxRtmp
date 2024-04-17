package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication()
public class PushStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushStreamingApplication.class,args);
        //        //设置rtmp服务器推流地址(写你自己服务器外网地址)
//        String outputPath = "rtmp://127.0.0.1:1935/live/test?token=123456";
//        PushStream recordPush = new PushStream();
//        recordPush.getRecordPush(outputPath, 25);
        System.out.println("Hello world");
    }

}