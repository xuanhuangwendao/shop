package com.xing.shop.config;

import com.alibaba.fastjson.JSON;
import com.xing.shop.domain.Context;
import com.xing.shop.domain.Result;
import com.xing.shop.util.JwtUtils;
import com.xing.shop.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:16
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static final boolean useToken = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.error("uri:" + uri);
        if (!useToken) {
            Context context = new Context();
            context.setUserId(1L);
            ThreadUtils.context.set(context);
            return true;
        }
        if (StringUtils.startsWith(uri, "/img/")) {
            return true;
        }
        if (StringUtils.equalsAny(uri, "/user/login", "/user/register")) {
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            writeResponse(response, Result.fail(ResultCode.TOKEN_FAIL));
            return false;
        }
        String userId = JwtUtils.verifyToken(token);
        if (StringUtils.isBlank(userId)) {
            writeResponse(response, Result.fail(ResultCode.TOKEN_FAIL));
            return false;
        }
        Context context = new Context();
        context.setUserId(NumberUtils.toLong(userId));
        ThreadUtils.context.set(context);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public <T>void writeResponse(HttpServletResponse response, Result<T> result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
