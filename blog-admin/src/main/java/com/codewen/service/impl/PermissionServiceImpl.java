package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codewen.mapper.PermissionMapper;
import com.codewen.pojo.Permission;
import com.codewen.service.PermissionService;
import com.codewen.vo.PageResult;
import com.codewen.vo.Result;
import com.codewen.vo.params.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codewen77
 * @date 2022-09-12
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result permissionList(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(pageParam.getQueryString())) {
            queryWrapper.eq(Permission::getName, pageParam.getQueryString());
        }

        Page<Permission> selectPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>(selectPage.getRecords(), selectPage.getTotal());

        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);

        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);

        return Result.success(null);
    }
}
