package com.act.demo.controller;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysUser;
import com.act.demo.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
    @Resource
    SessionContext sessionContext;

    @RequestMapping("/index")
    public String index(Map map) {
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
        return "main/index";
    }
}
