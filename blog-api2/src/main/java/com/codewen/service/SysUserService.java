package com.codewen.service;

import com.codewen.pojo.SysUser;
import com.codewen.vo.Result;

/**
 * @author codewen77
 * @date 2022-08-25
 */
public interface SysUserService {

    /**
     * 根据作者ID查找作者的用户信息
     * @param authorId
     * @return
     */
    SysUser findUserById(Long authorId);

    /**
     * 根据账号密码查找用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token获取当前用户信息
     * @param token
     * @return
     */
    Result currentUser(String token);

    /**
     * 根据账户获取用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 增加一个用户
     * @param user
     */
    void insertUser(SysUser user);
}
