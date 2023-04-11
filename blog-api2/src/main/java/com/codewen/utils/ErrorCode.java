package com.codewen.utils;

/**
 * @author codewen77
 * @date 2022-08-30
 */
public enum ErrorCode {

    /**
     * 账号密码错误 登录失败
     */
    ACCOUNT_PASSWORD_ERROR(1001, "账号或者密码错误!"),

    /**
     * 账号密码无效
     */
    ACCOUNT_PASSWORD_INVALID(1002, "账号或者密码为非法!"),

    /**
     * 还未登录
     */
    NO_LOGIN(1003, "还没有登录!"),

    /**
     * token不合法
     */
    TOKEN_INVALID(2001, "token不合法!"),

    /**
     * token已过期
     */
    TOKEN_OVERDUE(2002, "token已过期!"),

    /**
     * 注册参数不合法
     */
    REGISTER_PARAMS_INVALID(3001, "注册参数不合法!"),

    /**
     * 该账号已被注册过
     */
    ACCOUNT_IS_REGISTER(3002, "该账号已被注册过");


    private int code;
    private String msg;

    ErrorCode() {
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg =  msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
