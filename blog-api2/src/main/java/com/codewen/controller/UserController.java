package com.codewen.controller;

import com.codewen.service.LoginService;
import com.codewen.service.RegisterService;
import com.codewen.service.SysUserService;
import com.codewen.vo.Result;
import com.codewen.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author codewen77
 * @date 2022-08-30
 */
@RestController
public class UserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams) {

        return loginService.login(loginParams);
    }

    @GetMapping("/users/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {

        return sysUserService.currentUser(token);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {

        return loginService.logout(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody LoginParams loginParams) {

        return registerService.register(loginParams);
    }
}
