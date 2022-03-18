package com.xing.shop.login.impl;

import com.xing.shop.domain.User;
import com.xing.shop.login.LoginService;
import com.xing.shop.repository.UserRepository;
import com.xing.shop.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:54
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserRepository userRepository;
    @Override
    public String login(String userName, String password) {
        User user = userRepository.findUserByUserNameEquals(userName);
        if (user == null) {
            return null;
        }
        if (!StringUtils.equals(user.getPassword(), password)) {
            return null;
        }
        return JwtUtils.createToken(String.valueOf(user.getId()));

    }
}
