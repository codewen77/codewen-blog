package com.codewen.service;

import com.codewen.vo.Result;
import com.codewen.vo.params.LoginParams;

/**
 * @author codewen77
 * @date 2022-08-30
 */
public interface LoginService {

    /**
     * 用户登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
