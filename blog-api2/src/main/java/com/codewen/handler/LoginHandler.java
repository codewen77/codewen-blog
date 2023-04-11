package com.codewen.handler;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codewen.pojo.SysUser;
import com.codewen.utils.ErrorCode;
import com.codewen.utils.JwtUtil;
import com.codewen.utils.RedisUtil;
import com.codewen.utils.UserThreadLocal;
import com.codewen.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author codewen77
 * @date 2022-08-31
 */
@Component
@Slf4j
public class LoginHandler implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.先检查拦截的是否是Handler方法(Controller)
         * 2.先从请求头中获取token
         * 3.校验token是否合法(为空、jwt验证、过期)
         */
        log.info("=================request start===========================");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            Result fail = Result.fail(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        log.info("token:{}", token);
        if (JwtUtil.verify(token) == null) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        String strUser = (String) redisUtil.get("TOKEN_"+token);
        if (StringUtils.isBlank(strUser)) {
            Result fail = Result.fail(ErrorCode.TOKEN_OVERDUE.getCode(), ErrorCode.TOKEN_OVERDUE.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }

        SysUser sysUser = JSON.parseObject(strUser, SysUser.class);
        UserThreadLocal.set(sysUser);
        log.info("=================request end===========================");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在完成响应之后，不在需要使用ThreadLocal中的对象，需要进行手动释放，否则可能会导致内存泄漏
        UserThreadLocal.remove();
    }
}
