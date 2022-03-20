package com.xing.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:15
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor filter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(filter);
    }


}
