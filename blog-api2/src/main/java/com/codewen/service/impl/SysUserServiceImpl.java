package com.codewen.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codewen.mapper.SysUserMapper;
import com.codewen.pojo.SysUser;
import com.codewen.service.SysUserService;
import com.codewen.utils.ErrorCode;
import com.codewen.utils.JwtUtil;
import com.codewen.utils.RedisUtil;
import com.codewen.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUser findUserById(Long authorId) {
        return sysUserMapper.selectById(authorId);
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getNickname, SysUser::getAvatar);
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result currentUser(String token) {
        /**
         * 检查用户的token是否是有效的 (是否为空、是否合法、redis中是否过期)
         */
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMsg());
        }
        if (JwtUtil.verify(token) == null) {
            return Result.fail(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMsg());
        }
        String str = (String) redisUtil.get("TOKEN_"+token);
        SysUser sysUser = JSON.parseObject(str, SysUser.class);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_OVERDUE.getCode(), ErrorCode.TOKEN_OVERDUE.getMsg());
        }
        return Result.success(sysUser);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit "+1);

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void insertUser(SysUser user) {
        sysUserMapper.insert(user);
    }
}
