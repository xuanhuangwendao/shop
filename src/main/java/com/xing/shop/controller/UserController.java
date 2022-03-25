package com.xing.shop.controller;

import com.xing.shop.domain.Result;
import com.xing.shop.domain.request.RegisterRequest;
import com.xing.shop.service.LoginService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 2:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        return loginService.login(username, password);
    }

    @RequestMapping("/register")
    public Result<Boolean> register(@RequestBody RegisterRequest request) {

        return Result.success(true);

    }

}
