package com.act.demo.service;

import com.act.demo.domain.SysUserRole;

public interface IUserRoleService {
    /**
     * 根据角色id获取角色关系
     * @param roleId
     * @return
     */
    SysUserRole selectByRoleId(int roleId);
}
