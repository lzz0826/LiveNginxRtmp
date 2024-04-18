package org.example.map;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

public class StreamNameRtmpUrlMap {

    /** 推流碼緩存
     * Key : streamName 頻道名 streamName = userid + username
     * Value : rtmpUrl 推流碼URL
     */
    private final static ConcurrentHashMap<String,String> rtmpMap = new ConcurrentHashMap<>();


    public static String getRtmpMap(String streamName){
        return rtmpMap.get(streamName);
    }

    public static void setRtmpMap(String streamName , String rtmpUrl){
        rtmpMap.put(streamName,rtmpUrl);
    }

    public static void removeRtmpMap(String streamName){
        rtmpMap.remove(streamName);
    }

    public static boolean checkHasRtmpMapExist(String rtmpUrl){
        return rtmpMap.contains(rtmpUrl);
    }

    public static boolean checkHasRtmpMapExistByStreamName(String streamName){
        return !StringUtils.isBlank(getRtmpMap(streamName));
    }






}
