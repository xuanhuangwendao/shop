package com.xing.shop.domain;

import com.xing.shop.config.ResultCode;
import lombok.Data;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 0:42
 */
@Data
public class Result<T>{

    public boolean success;

    public T model;

    public Integer code;

    public String message;

    public static <T>Result<T> getInstance(boolean success, T model, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(success);
        result.setModel(model);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T>Result<T> success(T model) {
        return getInstance(true, model, ResultCode.SUCCESS.code, ResultCode.SUCCESS.message);
    }

    public static <T>Result<T> fail(T model) {
        return getInstance(false, model, ResultCode.SERVICE_ERROR.code, ResultCode.SERVICE_ERROR.message);
    }

    public static <T>Result<T> fail(Integer code, String message) {
        return getInstance(false, null, code, message);
    }

    public static <T>Result<T> fail(ResultCode code) {
        return getInstance(false, null, code.code, code.message);
    }


}
