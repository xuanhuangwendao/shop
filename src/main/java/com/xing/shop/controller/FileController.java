package com.xing.shop.controller;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.entity.UserInfo;
import com.xing.shop.domain.response.UploadFileResponse;
import com.xing.shop.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/4/9 13:44
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    public static final String BASE_URL = "http://123.60.77.134:7002/img/";

    @RequestMapping("/upload")
    public String file(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static\\img\\";

            File img = new File(path);
            if (!img.exists()) {
                img.mkdir();
            }
            file.transferTo(new File(path + uuid + ".jpg"));
        } catch (Exception e) {
           log.error("upload error", e);
        }
        return uuid;
    }

    @RequestMapping("/save")
    public Result<String> save(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail(ResultCode.UPLOAD_FAIL);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String result = null;
        try {
            String path = "/home/img/";

            File img = new File(path);
            if (!img.exists()) {
                img.mkdir();
            }
            result = path + uuid + ".jpg";
            file.transferTo(new File(result));
        } catch (Exception e) {
            log.error("upload error", e);
        }
        return Result.success(BASE_URL + uuid + ".jpg");
    }


}
