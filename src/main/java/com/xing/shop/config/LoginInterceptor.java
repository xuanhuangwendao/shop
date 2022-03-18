package com.xing.shop.config;

import com.xing.shop.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:16
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (StringUtils.contains(uri, "/login")) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String userId = JwtUtils.verifyToken(token);
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        return true;
    }

}
