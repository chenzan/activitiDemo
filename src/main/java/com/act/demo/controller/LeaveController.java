package com.act.demo.controller;

import com.act.demo.domain.SysLeave;
import com.act.demo.domain.SysUser;
import com.act.demo.service.ILeaveService;
import com.act.demo.service.IUserService;
import com.act.demo.service.IProcessService;
import com.act.demo.support.BaseController;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {
    @Resource
    ILeaveService leaveService;
    @Resource
    IUserService userService;
    @Resource
    IProcessService processService;

    /**
     * 开始请假页面
     *
     * @return
     */
    @RequestMapping("/leaveBegin")
    public String leaveBeginAction() {
        return "leave/leaveBegin";
    }

    /**
     * 当前任务
     *
     * @return
     */
    @RequestMapping("/currentTask")
    public String currentTask(Map map) {
        List<Task> tasks = leaveService.getPersonalLeaveTasks();
        map.put("tasks", tasks);
        return "leave/leaveTask";
    }

    /**
     * 请假流程批注列表
     *
     * @return
     */
    @RequestMapping("/leaveLog")
    public String leaveLogAction(Integer leaveId, Map map) {
        List<Comment> comments = leaveService.selectLeaveLog(leaveId, map);
        map.put("comments",comments);
        return "leave/leaveLog";
    }

    /**
     * 请假记录列表
     *
     * @return
     */
    @RequestMapping("/leaveList")
    public String leaveListAction(Map map) {
        List<SysLeave> leaves = leaveService.getPersonalLeaves();
        map.put("leaves", leaves);
        return "leave/leaveList";
    }

    /**
     * 请假申请
     *
     * @param userId
     * @param day
     * @param remark
     * @param username
     * @return
     */
    @RequestMapping("/apply")
    public String applyLeave(Integer userId, String day, String remark, String username) {
        SysLeave sysLeave = new SysLeave();
        sysLeave.setLeaveDay(day);
        sysLeave.setUserId(userId);
        sysLeave.setLeaveReason(remark);
        sysLeave.setLeaveStartTime(new Date());
        sysLeave.setAssigneeName(username);
        sysLeave.setState(1);
        leaveService.applyLeave(sysLeave);
        return "forward:/leaveList";
    }

    /**
     * 审批
     *
     * @param businessId
     * @param taskId
     * @param btnName
     * @param comment
     * @return
     */
    @RequestMapping("/approval")
    public String approvalLeave(Integer businessId, String taskId, String btnName, String comment) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("operation", btnName);
        leaveService.approvalLeave(businessId, taskId, variables, comment);
        return "redirect:/main/index";
    }

    @RequestMapping("/handlerLeave")
    public String handlerTask(String taskId, Map<String, Object> map) {
        String businessId = processService.getBusinessIdByTaskId(taskId);
        List<String> outComNames = processService.getOutComeLineListByTaskId(taskId);
        SysLeave sysLeave = leaveService.selectByLeaveId(Integer.valueOf(businessId));
        SysUser sysUser = userService.selectByUserId(sysLeave.getUserId());
        map.put("taskId", taskId);
        map.put("transitionNames", outComNames);
        map.put("sysLeave", sysLeave);
        map.put("sysUser", sysUser);
        List<Comment> commentByTaskId = processService.getCommentByTaskId(taskId);
        map.put("comments", commentByTaskId);
        return "leave/leave";
    }
}
