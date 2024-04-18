package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseResp;
import org.example.common.StatusCode;
import org.example.controller.req.CreatePushRtmpMp4Req;
import org.example.controller.req.GetPushRtmpReq;
import org.example.controller.req.RemoveRtmp;
import org.example.enums.NginxRtmpEnum;
import org.example.enums.UploadType;
import org.example.service.PathService;
import org.example.service.PushMp4;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

import static org.example.map.StreamNameRtmpUrlMap.*;
import static org.example.utils.FileUtil.checkFilePath;

@RestController()
@RequestMapping("/push")
@Slf4j
public class PushController {

    @Resource
    private PathService pathService;


    /**
     * 創建MAP推流(MP4暫時存本地)
     * streamName = userId + username
     */
    @GetMapping("/createPushRtmpMp4")
    public BaseResp<String> createPushRtmpMp4(@RequestBody @Valid CreatePushRtmpMp4Req req) throws Exception {

        String userId = req.getUserId();
        String username = req.getUsername();
        String mp4Name = req.getFileName();
        String fileMd5 = req.getFileMd5();
        HashMap<String, String> reqMap = req.getParameters();

        String streamName = userId+username;
        HashMap<String, String> map = new HashMap<>();
        if(reqMap != null && !reqMap.isEmpty()){
            map.putAll(reqMap);
        }

        String rtmpUrl = NginxRtmpEnum.Localhost.getRtmpUrl(streamName,map);
        String mptPath = pathService.getPackLocalUploadFilePath(userId,username,fileMd5, UploadType.Mp4)+"/"+mp4Name;

        if (!checkFilePath(mptPath)){
            return BaseResp.fail(StatusCode.FilePathNotFund);
        }

        if (checkHasRtmpMapExist(rtmpUrl)){
            return BaseResp.fail(StatusCode.RtmpUrlIsExist);
        }

        new Thread(()->{
            try {
                setRtmpMap(streamName,rtmpUrl);
                PushMp4.createMp4Push(mptPath, rtmpUrl);
            } catch (Exception e) {
                //TODO 目前失败也会直接返回 BaseResp.ok(rtmpUrl)
                removeRtmpMap(streamName);
                log.error("推送MP4文件时出现异常：", e);
            }
        }).start();

        return BaseResp.ok(rtmpUrl);
    }

    /**
     * 取得推流碼
     * streamName = userId + username
     */
    @GetMapping("/getPushRtmp")
    public BaseResp<String> getPushRtmp(@RequestBody @Valid GetPushRtmpReq req){
        String userId = req.getUserId();
        String username = req.getUsername();
        String streamName = userId+username;
        if(!checkHasRtmpMapExistByStreamName(streamName)){
            return BaseResp.fail(StatusCode.RtmpUrlIsNotExist);
        }
        return BaseResp.ok(getRtmpMap(streamName));
    }


    @GetMapping("/removeRtmp")
    public BaseResp<String> removeRtmp(@RequestBody @Valid RemoveRtmp req){

        String userId = req.getUserId();
        String username = req.getUsername();
        String streamName = userId+username;
        removeRtmpMap(streamName);

        return BaseResp.ok(StatusCode.Success);
    }


}
