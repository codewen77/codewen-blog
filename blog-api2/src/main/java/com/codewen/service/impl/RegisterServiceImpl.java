package com.codewen.service.impl;

import com.alibaba.fastjson.JSON;
import com.codewen.pojo.SysUser;
import com.codewen.service.RegisterService;
import com.codewen.service.SysUserService;
import com.codewen.utils.ErrorCode;
import com.codewen.utils.JwtUtil;
import com.codewen.utils.RedisUtil;
import com.codewen.vo.Result;
import com.codewen.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author codewen77
 * @date 2022-08-31
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    /**
     * 加密盐
     */
    private static String SALT = "12";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 注册步骤(全部过程都必须要在事务中)：
         * 1.先检查注册数据是否合法（为空则注册失败）
         * 2.从数据库查找该账号是否被注册 是则注册失败
         * 3.没有被注册进行插入操作
         * 4.生成token 存入redis 将token返回
         */
        String account = loginParams.getAccount();
        String nickname = loginParams.getNickname();
        String password = loginParams.getPassword();
        password = DigestUtils.md5Hex(password + SALT);

        if (StringUtils.isBlank(account) || StringUtils.isBlank(nickname) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.REGISTER_PARAMS_INVALID.getCode(), ErrorCode.REGISTER_PARAMS_INVALID.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_IS_REGISTER.getCode(), ErrorCode.ACCOUNT_IS_REGISTER.getMsg());
        }
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setAdmin(0);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        user.setAvatar("/static/img/logo.b3a48c0.png");
        user.setSalt(SALT);

        sysUserService.insertUser(user);

        Map<String, String> map = new HashMap<>(1);
        map.put("account", account);
        String token = JwtUtil.getToken(map);

        redisUtil.set("TOKEN_"+token, JSON.toJSONString(user), 3, TimeUnit.MINUTES);

        return Result.success(token);
    }
}
