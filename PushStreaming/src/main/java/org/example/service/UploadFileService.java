package org.example.service;


import org.example.enums.UploadType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class UploadFileService {

    @Resource
    private PathService pathService;


    /** 上傳檔案
     * */
    public String uploadFile(String userId, String username, String fileName,String fileMd5,byte[] fileBytes ,
                             UploadType uploadType) throws IOException {
        String packLocalUploadPath = pathService.getPackLocalUploadFilePath(userId,username,fileMd5,uploadType);
        File filePath = new File(packLocalUploadPath);
        File file = new File(filePath, fileName);
        if (!filePath.exists()) {
            Files.createDirectories(filePath.toPath());
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileBytes);
        }finally {
            if(fileOutputStream == null){
                fileOutputStream.close();
            }
        }
        return file.getPath();
    }




}
