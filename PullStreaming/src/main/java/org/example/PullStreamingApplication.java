package org.example;

import org.example.service.PullStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication()
public class PullStreamingApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PullStreamingApplication.class,args);
        //rtmp服务器拉流地址(自己服务器外网地址)
//        String inputPath = "rtmp://127.0.0.1:1935/live/test?token=123456";
//        PullStream pullStream = new PullStream();
//        pullStream.getPullStream(inputPath);

        String inputPath = "rtmp://127.0.0.1:1935/live/test?token=123456"; // 您要拉取的视频流的URL

        PullStream pullStream = new PullStream();
        try {
            pullStream.getPullStream(inputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}