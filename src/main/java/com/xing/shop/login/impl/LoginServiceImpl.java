package com.xing.shop.login.impl;

import com.xing.shop.domain.Account;
import com.xing.shop.login.LoginService;
import com.xing.shop.repository.AccountRepository;
import com.xing.shop.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:54
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public String login(String userName, String password) {
        Account account = accountRepository.getAccountByUsernameEquals(userName);
        if (account == null) {
            return null;
        }
        if (!StringUtils.equals(password, account.getPassword())) {
            return null;
        }
        if (account.getStatus() < 0) {
            return null;
        }
        return JwtUtils.createToken(String.valueOf(account.getId()));
    }
}
