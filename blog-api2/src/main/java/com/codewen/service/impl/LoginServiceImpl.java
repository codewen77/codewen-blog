package com.codewen.service.impl;

import com.alibaba.fastjson.JSON;
import com.codewen.pojo.SysUser;
import com.codewen.service.LoginService;
import com.codewen.service.SysUserService;
import com.codewen.utils.ErrorCode;
import com.codewen.utils.JwtUtil;
import com.codewen.utils.RedisUtil;
import com.codewen.vo.Result;
import com.codewen.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author codewen77
 * @date 2022-08-30
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    /**
     * 加密盐
     */
    private static String SALT = "12";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 登录流程：
         * 1.检查账号密码是否合法（为空）
         * 2.查询数据中账号密码是否匹配成功
         * 3.失败则直接返回，成功则需先生成token并且存入redis然后返回token
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.ACCOUNT_PASSWORD_INVALID.getCode(), ErrorCode.ACCOUNT_PASSWORD_INVALID.getMsg());
        }
        // 查询账号密码是否正确
        // md5加密
        String md5Pwd = DigestUtils.md5Hex(password + SALT);
        SysUser sysUser = sysUserService.findUser(account, md5Pwd);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PASSWORD_ERROR.getCode(), ErrorCode.ACCOUNT_PASSWORD_ERROR.getMsg());
        }
        // 生成token
        Map<String, String> map = new HashMap<>(1);
        // account
        map.put("account", sysUser.getAccount());
        String token = JwtUtil.getToken(map);
        log.info("token:"+token);

        // 将token存入redis
        redisUtil.set("TOKEN_"+token, JSON.toJSONString(sysUser), 3, TimeUnit.MINUTES);

        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        /**
         * 直接将token从redis中移除即可
         */
        redisUtil.del("TOKEN_"+token);
        return Result.success(null);
    }
}
