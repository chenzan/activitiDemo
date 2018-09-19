package com.act.demo.service.impl;

import com.act.demo.domain.SysUserRole;
import com.act.demo.mapper.SysUserRoleMapper;
import com.act.demo.service.IUserRoleService;
import com.act.demo.support.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends BaseService<SysUserRole, SysUserRoleMapper> implements IUserRoleService {

    @Override
    public SysUserRole selectByRoleId(int roleId) {
        return mMapper.selectByPrimaryKey(Integer.valueOf(roleId + ""));
    }
}
