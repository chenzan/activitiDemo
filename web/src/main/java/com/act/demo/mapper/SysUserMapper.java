package com.act.demo.mapper;

import com.act.demo.domain.SysUser;
import com.act.demo.support.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByNamePwd(@Param("username") String username, @Param("password") String password);
}