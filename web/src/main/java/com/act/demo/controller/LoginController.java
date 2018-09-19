package com.act.demo.controller;

import com.act.demo.aop.annotation.Log;
import com.act.demo.service.IUserService;
import com.act.demo.support.BaseController;
import com.act.demo.support.annotation.IgnoreLogin;
import com.act.demo.support.annotation.RequestList;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Slf4j
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
    public JSONObject loginAction(HttpSession session, String username, String password, Date nowDate,
                                  @RequestList(name = "aaa") List aaas) {
        log.info(nowDate.toString());
        return userService.loginAction(username, password, session);
    }

    @RequestMapping(
            value = {"/logout"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String doLogout(HttpSession session) {
        userService.executeLoginOut(session);
        return "redirect:/login";
    }
}
