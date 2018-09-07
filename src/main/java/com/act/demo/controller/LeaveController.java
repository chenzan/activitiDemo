package com.act.demo.controller;

import com.act.demo.domain.SysLeave;
import com.act.demo.domain.SysUser;
import com.act.demo.service.ILeaveService;
import com.act.demo.service.IUserService;
import com.act.demo.service.IWorkFlowService;
import com.act.demo.support.BaseController;
import org.activiti.engine.task.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {
    @Resource
    ILeaveService leaveService;
    @Resource
    IUserService userService;
    @Resource
    IWorkFlowService workFlowService;

    /**
     * 请假记录列表
     *
     * @return
     */
    @RequestMapping("/leaveLog")
    public String leaveListAction(Integer leaveId, Map map) {
        List<Comment> comments = leaveService.selectLeaveLog(leaveId,map);
        return "leave/leaveLog";
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
        return "redirect:/main/index";
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
        String businessId = workFlowService.getBusinessIdByTaskId(taskId);
        List<String> outComNames = workFlowService.getOutComeLineListByTaskId(taskId);
        SysLeave sysLeave = leaveService.selectByLeaveId(Integer.valueOf(businessId));
        SysUser sysUser = userService.selectByUserId(sysLeave.getUserId());
        map.put("taskId", taskId);
        map.put("transitionNames", outComNames);
        map.put("sysLeave", sysLeave);
        map.put("sysUser", sysUser);
        List<Comment> commentByTaskId = workFlowService.getCommentByTaskId(taskId);
        map.put("comments", commentByTaskId);
        return "leave/leave";
    }
}
