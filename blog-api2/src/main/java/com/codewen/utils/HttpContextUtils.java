package com.codewen.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codewen77
 * @date 2022-09-05
 */
public class HttpContextUtils {


    public static HttpServletRequest getHttpServletRequest() {

        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
