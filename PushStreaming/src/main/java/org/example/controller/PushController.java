package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.common.BaseResp;
import org.example.common.StatusCode;
import org.example.controller.req.CreatePushRtmpMp4Req;
import org.example.enums.NginxRtmpEnum;
import org.example.enums.UploadType;
import org.example.exception.FilePathNotFundException;
import org.example.service.PathService;
import org.example.service.PushMp4;
import org.example.service.UploadFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

import static org.example.utils.FileUtil.CheckFilePath;

@RestController()
@RequestMapping("/push")
@Slf4j
public class PushController {

    @Resource
    private PathService pathService;


    /**
     * 創建MAP推流(MP4暫時存本地)
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

        boolean b = CheckFilePath(mptPath);
        if (!b){
            return BaseResp.fail(StatusCode.FilePathNotFund);
        }

        new Thread(()->{
            try {
                PushMp4.createMp4Push(mptPath, rtmpUrl);
            } catch (Exception e) {
                //TODO 目前失败也会直接返回 BaseResp.ok(rtmpUrl)
                log.error("推送MP4文件时出现异常：", e);
            }
        }).start();

        return BaseResp.ok(rtmpUrl);
    }


}
