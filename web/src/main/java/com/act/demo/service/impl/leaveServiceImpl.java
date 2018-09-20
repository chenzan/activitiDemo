package com.act.demo.service.impl;

import com.act.demo.aop.annotation.Log;
import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysLeave;
import com.act.demo.domain.SysUser;
import com.act.demo.mapper.SysLeaveMapper;
import com.act.demo.service.ILeaveService;
import com.act.demo.service.IUserService;
import com.act.demo.service.IProcessService;
import com.act.demo.support.BaseService;
import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class leaveServiceImpl extends BaseService<SysLeave, SysLeaveMapper> implements ILeaveService {
    @Autowired
    IProcessService processService;
    @Autowired
    IUserService userService;
    @Autowired
    ILeaveService leaveService;
    @Autowired
    SessionContext sessionContext;

    @Transactional
    public void applyLeave(SysLeave sysLeave) {
        this.insertSelective(sysLeave);
        processService.startProcessInstance(sysLeave, "sysLeave." + sysLeave.getId(), sysLeave.getAssigneeName());
    }

    @Override
    public void approvalLeave(Integer leaveId, String taskId, Map<String, Object> variables, String comment) {
        SysLeave sysLeave = this.selectByPrimaryKey(leaveId);
        variables.put("day", sysLeave.getLeaveDay());
        //完成任务
        boolean processFinish = processService.completeTask(taskId, variables, comment);
        if (processFinish) {
            SysLeave sysLeaveUpdate = new SysLeave();
            sysLeaveUpdate.setId(leaveId);
            sysLeaveUpdate.setState(2);
            this.updateByPrimaryKeySelective(sysLeaveUpdate);
        }
    }

    @Override
    public List<SysLeave> selectAll(Integer userId) {
        return mMapper.selectAll(userId);
    }

    @Override
    public List<SysLeave> selectByUserId(int userId) {
        return mMapper.selectByUserId(userId);
    }

    @Override
    public SysLeave selectByLeaveId(Integer integer) {
        return this.selectByPrimaryKey(integer);
    }

    @Override
    public List<SysLeave> getPersonalLeaves() {
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
        List<SysLeave> sysLeaves = selectByUserId(user.getId());
        for (SysLeave sysLeave : sysLeaves) {
            Integer leaveId = sysLeave.getId();
            List<Comment> comments = processService.getHistoryCommentByBusinessId("leave." + leaveId);
            sysLeave.setComments(comments);
        }
        return sysLeaves;
    }

    @Override
    public List<Task> getPersonalLeaveTasks() {
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
        //以当前业务的实体名称为流程定义的key
        List<Task> personalTasks = processService.getPersonalTasks(user.getUsername(), SysLeave.class.getSimpleName());
        return personalTasks;
    }

    @Override
    public JSONObject selectLeaveLog(Integer leaveId,JSONObject jsonObject) {
        SysLeave sysLeave = this.selectByPrimaryKey(leaveId);
        SysUser sysUser = userService.selectByUserId(sysLeave.getUserId());
        List<Comment> comments = processService.getHistoryCommentByBusinessId("sysLeave." + leaveId);
        jsonObject.put("comments", comments);
        jsonObject.put("sysLeave", sysLeave);
        jsonObject.put("sysUser", sysUser);
        return jsonObject;
    }

}
