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

    /**
     * 根据用户id查询
     *
     * @param userId
     * @return
     */
    SysUser selectByUserId(Integer userId);

    /**
     * 插入用户
     *
     * @param sysUser
     */
    void insertUser(SysUser sysUser);

    /**
     * 退出
     *
     * @param session
     */
    void executeLoginOut(HttpSession session);

    /**
     * 根据角色查询用户
     *
     * @param roleDirector
     * @return
     */
    SysUser selectByRole(int roleDirector);
}
