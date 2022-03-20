package com.xing.shop.user.impl;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Account;
import com.xing.shop.user.LoginService;
import com.xing.shop.repository.AccountRepository;
import com.xing.shop.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:54
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Result<String> login(String userName, String password) {
        Account account = accountRepository.getAccountByUsernameEquals(userName);
        if (account == null) {
            return Result.fail(ResultCode.USER_NOT_EXIT);
        }
        if (!StringUtils.equals(password, account.getPassword())) {
            return Result.fail(ResultCode.PASSWORD_ERROR);
        }
        if (account.getStatus() < 0) {
            return Result.fail(ResultCode.USER_STATUS_ERROR);
        }
        String token = JwtUtils.createToken(String.valueOf(account.getId()));
        return Result.success(token);
    }
}
