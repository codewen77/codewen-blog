package com.codewen.controller;

import com.codewen.pojo.Permission;
import com.codewen.service.AdminService;
import com.codewen.service.PermissionService;
import com.codewen.vo.Result;
import com.codewen.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author codewen77
 * @date 2022-09-09
 */
@RestController
@RequestMapping("/admin/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam) {

        return permissionService.permissionList(pageParam);
    }

    @PostMapping("/user/userInfo")
    public Result userInfo() {

        return adminService.userInfo();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {

        return permissionService.add(permission);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {

        return permissionService.delete(id);
    }

    @PostMapping("update")
    public Result update(@RequestBody Permission permission) {

        return permissionService.update(permission);
    }

}
