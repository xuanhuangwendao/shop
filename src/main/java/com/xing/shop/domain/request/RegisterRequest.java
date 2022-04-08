package com.xing.shop.domain.request;

import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/26 0:02
 */
@Data
public class RegisterRequest {

    public String username;

    public String password;

    public String nickname;

    public String address;
}
