package com.xing.shop.service;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.response.LoginResponse;
import com.xing.shop.domain.request.RegisterRequest;
import com.xing.shop.domain.response.RegisterResponse;
import com.xing.shop.domain.response.UploadFileResponse;
import com.xing.shop.domain.response.UserInfoResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:53
 */
public interface UserService {

    /**
     * 用户登录
     * @param userName 用户登录名
     * @param password 用户登录密码
     * @return token
     */
    Result<LoginResponse> login(String userName, String password, int userType);

    Result<RegisterResponse> register(RegisterRequest registerInfo);

    Result<UserInfoResponse> getUserInfo(long userId);

    Result<UploadFileResponse> uploadUserPic(MultipartFile file);

}
