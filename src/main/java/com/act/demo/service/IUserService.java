package com.act.demo.service;

import com.act.demo.domain.SysUser;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

public interface IUserService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    JSONObject loginAction(String username, String password, HttpSession session);

    SysUser selectByUserId(Integer userId);

    void insertUser(SysUser sysUser);
}
