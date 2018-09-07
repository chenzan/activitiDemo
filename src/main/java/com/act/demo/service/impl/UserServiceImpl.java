package com.act.demo.service.impl;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysUser;
import com.act.demo.mapper.SysUserMapper;
import com.act.demo.service.IUserService;
import com.act.demo.support.BaseService;
import com.act.demo.support.ResponseResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl extends BaseService<SysUser, SysUserMapper> implements IUserService {
    @Autowired
    SessionContext sessionContext;

    public SysUser selectByNamePwd(String username, String password) {
        return mMapper.selectByNamePwd(username, password);
    }

    @Override
    public SysUser selectByUserId(Integer userId) {
        return this.selectByPrimaryKey(userId);
    }

    @Override
    public void insertUser(SysUser sysUser) {
        mMapper.insertSelective(sysUser);
    }

    @Override
    public JSONObject loginAction(String username, String password, HttpSession session) {
        SysUser sysUser = selectByNamePwd(username, password);
        if (sysUser == null) {
            return ResponseResult.error("用户不存在");
        }
        sessionContext.putSessionValue(ConstantValue.CURRENT_USER, sysUser);
        return ResponseResult.success();
    }
}
