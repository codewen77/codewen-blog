package com.codewen.utils;

import com.codewen.pojo.SysUser;

/**
 * @author codewen77
 * @date 2022-08-31
 */
public class UserThreadLocal {

    private UserThreadLocal() {

    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void set(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
