package com.act.demo.controller;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysLeave;
import com.act.demo.domain.SysUser;
import com.act.demo.service.ILeaveService;
import com.act.demo.service.IWorkFlowService;
import com.act.demo.support.BaseController;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
    @Resource
    IWorkFlowService workFlowService;
    @Resource
    SessionContext sessionContext;
    @Resource
    ILeaveService leaveService;

    @RequestMapping("/index")
    public String index(Map map) {
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
        List<Deployment> deploymentProcess = workFlowService.getDeploymentProcess();
        List<ProcessDefinition> definitionProcess = workFlowService.getDefinitionProcess();
        List<SysLeave> sysLeaves = leaveService.selectByUserId(1);
        List<Task> tasks = workFlowService.getPersonalTasks(user.getUsername(),SysLeave.class.getSimpleName());
        map.put("deployments", deploymentProcess);
        map.put("definitionProcess", definitionProcess);
        map.put("definitionProcess", definitionProcess);
        map.put("tasks", tasks);
        map.put("leaves", sysLeaves);
        return "main/index";
    }
}
