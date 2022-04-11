package com.xing.shop.user;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.model.Account;
import com.xing.shop.domain.model.UserInfo;
import com.xing.shop.domain.request.RegisterRequest;
import com.xing.shop.repository.UserInfoRepository;
import com.xing.shop.service.LoginService;
import com.xing.shop.repository.AccountRepository;
import com.xing.shop.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:54
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

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

    @Override
    public Result<Boolean> register(RegisterRequest registerInfo) {

        Account account = accountRepository.getAccountByUsernameEquals(registerInfo.getUsername());
        if (account != null) {
            return Result.fail(ResultCode.REGISTER_ERROR);
        }
        account = new Account();
        account.setUsername(registerInfo.getUsername());
        account.setPassword(registerInfo.getPassword());
        account.setStatus(1);
        account.setGmtCreate(Instant.now());
        account.setGmtModified(Instant.now());
        Account accountSave = accountRepository.save(account);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(accountSave.getId());
        userInfo.setNickname(registerInfo.getNickname());
        userInfo.setAddress(registerInfo.getAddress());
        userInfo.setGmtCreate(Instant.now());
        userInfo.setGmtModified(Instant.now());
        userInfo.setImg("http://192.168.31.42:7002/img/default");
        UserInfo userSave = userInfoRepository.save(userInfo);

        return Result.success(true);
    }
}
