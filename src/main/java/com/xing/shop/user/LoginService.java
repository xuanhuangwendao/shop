package com.xing.shop.user;

import com.xing.shop.domain.Result;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:53
 */
public interface LoginService {

    /**
     * 用户登录
     * @param userName 用户登录名
     * @param password 用户登录密码
     * @return token
     */
    Result<String> login(String userName, String password);

}
