package com.xing.shop.util;

import com.xing.shop.domain.Context;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:35
 */
public class ThreadUtils {

    public static ThreadLocal<Context> context = new ThreadLocal<>();
}
