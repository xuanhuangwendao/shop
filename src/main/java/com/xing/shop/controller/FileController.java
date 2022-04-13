package com.xing.shop.controller;

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
}
