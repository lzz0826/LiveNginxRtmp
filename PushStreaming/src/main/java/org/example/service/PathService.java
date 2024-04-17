package org.example.service;

import org.example.enums.UploadType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathService {


  @Value("${tmp.upload.file-path}")
  public String localUploadFilePath;


  /** 創建路徑
   * userId 上傳者ID
   * username 上傳者明稱
   * md5 檔案MD5當作唯一路徑
   * uploadType 上傳類型
   * */
  public String getPackLocalUploadFilePath(String userId, String username, String md5,
      UploadType uploadType) {
    return localUploadFilePath + "/" + userId + "_" + username + "/" + uploadType.code + "/" + md5;
  }



}
