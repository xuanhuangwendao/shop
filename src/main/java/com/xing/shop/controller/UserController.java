package com.xing.shop.controller;

import com.alibaba.fastjson.JSON;
import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;

import com.xing.shop.domain.response.LoginResponse;
import com.xing.shop.domain.response.RegisterResponse;
import com.xing.shop.domain.request.RegisterRequest;
import com.xing.shop.domain.response.UploadFileResponse;
import com.xing.shop.domain.response.UserInfoResponse;
import com.xing.shop.service.UserService;
import com.xing.shop.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 2:20
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param username  登录账号
     * @param password  登录密码
     * @return  token
     */
    @RequestMapping("/login")
    public Result<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    /**
     * 用户注册
     * @param data 注册信息
     * @return 注册是否成功
     */
    @RequestMapping("/register")
    public Result<RegisterResponse> register(@RequestBody String data) {
        RegisterRequest registerInfo = JSON.parseObject(data, RegisterRequest.class);
        return userService.register(registerInfo);
    }

    @RequestMapping("/myUserInfo")
    public Result<UserInfoResponse> myUserInfo() {
        Context context = ThreadUtils.context.get();
        return userService.getUserInfo(context.getUserId());
    }

    @RequestMapping("/changeUserPic")
    public Result<UploadFileResponse> file(@RequestParam(value = "file") MultipartFile file) {
        Result<UploadFileResponse> result = userService.uploadUserPic(file);
        return result;
    }


}
