package com.xing.shop.config;

import lombok.Getter;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/20 1:14
 */
public enum ResultCode {
    /**
     * 成功状态，以2开头
     */
    SUCCESS(200, "服务调用成功"),

    /**
     * 自定义服务调用失败状态，以6开头
     */
    SERVICE_ERROR(600, "服务调用失败"),

    TOKEN_FAIL(601, "token校验失败"),

    USER_NOT_EXIT(602, "用户不存在"),

    PASSWORD_ERROR(603, "用户密码错误"),

    USER_STATUS_ERROR(604, "账号状态异常"),

    EMPTY_RESULT(605, "结果为空"),

    ITEM_NOT_FOUND(606, "商品不存在"),


    ;

    public final Integer code;

    public final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
