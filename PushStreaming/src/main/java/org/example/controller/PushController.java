package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.common.BaseResp;
import org.example.controller.req.CreatePushRtmpMp4Req;
import org.example.enums.NginxRtmpEnum;
import org.example.service.PushMp4;
import org.example.service.UploadFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController()
@RequestMapping("/push")
@Slf4j
public class PushController {



    @GetMapping("/createPushRtmpMp4")
    public BaseResp<String> createPushRtmpMp4(@RequestBody @Valid CreatePushRtmpMp4Req req) throws Exception {

        //TODO 认证或其他处理(MP4地址)
        String userId = req.getUserId();
        String mp4Id = req.getMp4Id();
        HashMap<String, String> reqMap = req.getParameters();


        String streamName = req.getStreamName();
        if (StringUtils.isBlank(streamName)){
            streamName = NginxRtmpEnum.Localhost.name;
        }

        HashMap<String, String> map = new HashMap<>();
        if(reqMap != null && !reqMap.isEmpty()){
            map.putAll(reqMap);
        }

        String rtmpUrl = NginxRtmpEnum.Localhost.getRtmpUrl(streamName,map);

        new Thread(()->{
            try {
                PushMp4.createMp4Push("/Users/admin/IdeaProjects/LiveNginxRtmp/PushStreaming/mp4/test99.mp4", rtmpUrl);
            } catch (Exception e) {
                //TODO 目前失败也会直接返回 BaseResp.ok(rtmpUrl)
                log.error("推送MP4文件时出现异常：", e);
            }
        }).start();

        return BaseResp.ok(rtmpUrl);
    }


}
