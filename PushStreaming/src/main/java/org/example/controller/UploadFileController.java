package org.example.controller;

import org.example.common.BaseResp;
import org.example.common.StatusCode;
import org.example.controller.req.UploadMp4Req;
import org.example.enums.UploadType;
import org.example.service.UploadFileService;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static org.example.utils.FileUtil.*;

@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    @PostMapping("/uploadMp4")
    public BaseResp<String> uploadMp4(@Valid UploadMp4Req req) throws IOException {

        MultipartFile file = req.getFile();
        String fileName = file.getOriginalFilename();
        String extension = getExtByFileName(fileName);
        if (!supportedComVideo.contains(extension)) {
            return BaseResp.fail(StatusCode.NonSupportExt);
        }

        byte[] fileBytes = file.getBytes();
        String fileMd5 = DigestUtils.md5DigestAsHex(fileBytes);
        String result = uploadFileService.uploadFile(req.getUserId(),req.getUsername(), fileName, fileMd5, fileBytes, UploadType.File);
        return BaseResp.ok(result);
    }




}
