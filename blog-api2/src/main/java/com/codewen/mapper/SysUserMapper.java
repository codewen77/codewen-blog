package com.codewen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codewen.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Component
public interface SysUserMapper extends BaseMapper<SysUser> {
}
