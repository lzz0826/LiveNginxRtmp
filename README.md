# LiveNginxRtmp

# 基於Nginx 推拉流服務實現直播
<br />
![image](https://raw.githubusercontent.com/lzz0826/LiveNginxRtmp/main/img/m2.png)
圖片來源:https://github.com/twtrubiks/nginx-rtmp-tutorial
<br />
<br />
## 運行項目:
### Docker Compose 運行前需要加上虛擬內部網域
docker network create --subnet=192.168.200.0/24 redis-cluster-net<br />
<br />
<br />
## PushStreaming 推流服務:
### 實現的功能如下：
- MP4上傳<br />
- 創建推流碼<br />
- 查詢推流碼<br />
- MP4直播<br />
- 攝像頭直播(未完成)<br />


![image](https://raw.githubusercontent.com/lzz0826/LiveNginxRtmp/main/img/m1.jpg)

圖片來源:https://github.com/twtrubiks/nginx-rtmp-tutorial
<br />
<br />
## PullStreaming 拉流服務:
### 實現的功能如下：
- 拉取推流碼播放<br />

