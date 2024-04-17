package org.example.enums;

import java.util.Map;

public enum NginxRtmpEnum {
    Localhost("Localhost","127.0.0.1", "1935"),
    Other("Other","127.0.0.99", "1944");

    public final String name;

    public final String ip;

    public final String port;

    NginxRtmpEnum(String name ,String ip, String point) {
        this.name = name;
        this.ip = ip;
        this.port = point;
    }

    /**
     * 构建RTMP URL，并包含附加参数
     *
     * @param streamName 流名称
     * @param map 包含附加参数的映射
     * @return RTMP URL
     */
    public String getRtmpUrl(String streamName, Map<String,String> map) {
        StringBuilder rep = new StringBuilder();
        rep.append("rtmp://")
                .append(ip)
                .append(":")
                .append(port)
                .append("/live/")
                .append(streamName);

        for (Map.Entry<String, String> m : map.entrySet()) {
            rep.append("?");
            rep.append(m.getKey());
            rep.append("=");
            rep.append(m.getValue());
        }
        return rep.toString();
    }

}