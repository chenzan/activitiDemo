package com.act.demo.controller;

import com.act.demo.aop.annotation.Log;
import com.act.demo.service.IUserService;
import com.act.demo.support.BaseController;
import com.act.demo.support.annotation.IgnoreLogin;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@IgnoreLogin
public class LoginController extends BaseController {
    @Resource
    private IUserService userService;

    @IgnoreLogin
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @Log(name = "login", content = "登录", target = Log.Target.CONSOLE)
    @IgnoreLogin
    @ResponseBody
    @RequestMapping("/loginAction")
    public JSONObject loginAction(HttpSession session, String username, String password) {
        return userService.loginAction(username, password, session);
    }
}
