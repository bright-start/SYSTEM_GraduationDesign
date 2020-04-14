package com.cys.sso.controller;

import com.cys.sso.pojo.Result;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private FastFileStorageClient storageClient;

    private static final String PHOTO_DOMAIN = "http://10.211.55.33:8888/";

    private static final String[] CONTENT_TYPE_LIST = {"image/jpeg","image/png","image/gif"};

    @PostMapping("/upload")
    public Result storePhoto(MultipartFile file,HttpServletRequest request) throws IOException {
        if(file == null){
            return new Result().error(400,"图片上传不能未空");
        }
        String contentType = file.getContentType();
        if(contentType == null){
            return new Result().success("上传文件类型无法识别");
        }else{
            for (String type : CONTENT_TYPE_LIST) {
                if(type.equalsIgnoreCase(contentType)){
                    return new Result().success(savePathToServer(file));
                }
            }
        }
        return new Result().success("请确保上传文件类型为jpg,png,gif");
    }

    private String savePathToServer(MultipartFile file) throws IOException {
        String photoPath = file.getOriginalFilename();
        // 上传并且生成缩略图
        StorePath storePath = storageClient.uploadFile(
                file.getInputStream(), file.getSize(), "png", null);
        return PHOTO_DOMAIN + storePath.getFullPath();
    }

}
