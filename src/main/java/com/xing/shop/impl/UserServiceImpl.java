package com.xing.shop.impl;

import com.xing.shop.config.ResultCode;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;
import com.xing.shop.domain.entity.Wallet;
import com.xing.shop.domain.response.LoginResponse;
import com.xing.shop.domain.entity.Account;
import com.xing.shop.domain.entity.UserInfo;
import com.xing.shop.domain.request.RegisterRequest;
import com.xing.shop.domain.response.RegisterResponse;
import com.xing.shop.domain.response.UploadFileResponse;
import com.xing.shop.domain.response.UserInfoResponse;
import com.xing.shop.repository.AccountRepository;
import com.xing.shop.repository.UserInfoRepository;
import com.xing.shop.repository.WalletRepository;
import com.xing.shop.service.UserService;
import com.xing.shop.util.JwtUtils;
import com.xing.shop.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:54
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String DEFAULT_PIC = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fc99e193eb61eb3b4f412f4f84864f4ff67138fd65a56-lF6ywc_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652459264&t=cff2563f5eeedcf45aa310cee6c514d1";

    public static final String BASE_URL = "http://192.168.31.42:7002/img/";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Result<LoginResponse> login(String userName, String password) {
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
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return Result.success(response);
    }

    @Override
    public Result<RegisterResponse> register(RegisterRequest registerInfo) {

        Account account = accountRepository.getAccountByUsernameEquals(registerInfo.getUsername());
        if (account != null) {
            return Result.fail(ResultCode.REGISTER_ERROR);
        }
        account = new Account();
        account.setUsername(registerInfo.getUsername());
        account.setPassword(registerInfo.getPassword());
        account.setStatus(0);
        account.setGmtCreate(Instant.now());
        account.setGmtModified(Instant.now());
        Account accountSave = accountRepository.save(account);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(accountSave.getId());
        userInfo.setNickname(registerInfo.getNickname());
        userInfo.setAddress(registerInfo.getAddress());
        userInfo.setProfilePic(DEFAULT_PIC);
        userInfo.setSign(registerInfo.getSign());
        userInfo.setGmtCreate(Instant.now());
        userInfo.setGmtModified(Instant.now());
        UserInfo userSave = userInfoRepository.save(userInfo);

        Wallet wallet = new Wallet();
        wallet.setId(accountSave.getId());
        wallet.setSeller(0);
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setGmtCreate(Instant.now());
        wallet.setGmtModified(Instant.now());
        walletRepository.save(wallet);

        RegisterResponse response = new RegisterResponse();
        return Result.success(response);
    }

    @Override
    public Result<UserInfoResponse> getUserInfo(long userId) {
        Optional<UserInfo> one = userInfoRepository.findById(userId);
        if (one.isEmpty()) {
            return Result.fail(ResultCode.USER_NOT_EXIT);
        }
        UserInfo userInfo = one.get();
        UserInfoResponse response = new UserInfoResponse();
        response.setNickName(userInfo.getNickname());
        response.setSign(userInfo.getSign());
        response.setAddress(userInfo.getAddress());
        response.setProfilePic(userInfo.getProfilePic());

        return Result.success(response);
    }

    @Override
    public Result<UploadFileResponse> uploadUserPic(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail(ResultCode.UPLOAD_FAIL);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static\\img\\";

            File img = new File(path);
            if (!img.exists()) {
                img.mkdir();
            }
            file.transferTo(new File(path + uuid + ".jpg"));
            Context context = ThreadUtils.context.get();
            UserInfo userInfo = userInfoRepository.getById(context.getUserId());
            userInfo.setProfilePic(BASE_URL + uuid + ".jpg");
            userInfoRepository.save(userInfo);

        } catch (Exception e) {
            log.error("upload error", e);
        }
        UploadFileResponse response = new UploadFileResponse();
        return Result.success(response);
    }
}
