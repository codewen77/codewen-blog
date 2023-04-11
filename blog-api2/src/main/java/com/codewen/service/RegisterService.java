package com.codewen.service;

import com.codewen.vo.Result;
import com.codewen.vo.params.LoginParams;

/**
 * @author codewen77
 * @date 2022-08-31
 */
public interface RegisterService {

    Result register(LoginParams loginParams);
}
