package com.codewen.service.impl;

import com.codewen.mapper.AdminMapper;
import com.codewen.service.AdminService;
import com.codewen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codewen77
 * @date 2022-09-12
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result userInfo() {
        return null;
    }
}
