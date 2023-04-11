package com.codewen.service;

import com.codewen.pojo.Permission;
import com.codewen.vo.Result;
import com.codewen.vo.params.PageParam;

/**
 * @author codewen77
 * @date 2022-09-12
 */
public interface PermissionService {

    /**
     * 查询权限列表
     * @param pageParam
     * @return
     */
    Result permissionList(PageParam pageParam);


    /**
     * 权限列表中增加一条记录
     * @param permission
     * @return
     */
    Result add(Permission permission);

    /**
     * 权限列表中删除一条记录
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 权限列表总更新一条记录
     * @param permission
     * @return
     */
    Result update(Permission permission);
}
