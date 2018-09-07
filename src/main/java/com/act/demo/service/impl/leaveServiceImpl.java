package com.act.demo.service.impl;

import com.act.demo.domain.SysLeave;
import com.act.demo.domain.SysUser;
import com.act.demo.mapper.SysLeaveMapper;
import com.act.demo.service.ILeaveService;
import com.act.demo.service.IUserService;
import com.act.demo.service.IWorkFlowService;
import com.act.demo.support.BaseService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class leaveServiceImpl extends BaseService<SysLeave, SysLeaveMapper> implements ILeaveService {
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IUserService userService;
    @Autowired
    ILeaveService leaveService;

    @Transactional
    public void applyLeave(SysLeave sysLeave) {
        this.insertSelective(sysLeave);
        workFlowService.startProcessInstance(sysLeave, "sysLeave." + sysLeave.getId(), sysLeave.getAssigneeName());
    }

    @Override
    public void approvalLeave(Integer leaveId, String taskId, Map<String, Object> variables, String comment) {
        SysLeave sysLeave = this.selectByPrimaryKey(leaveId);
        variables.put("day", sysLeave.getLeaveDay());
        //完成任务
        boolean processFinish = workFlowService.completeTask(taskId, variables, comment);
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
    public List<Comment> selectLeaveLog(Integer leaveId, Map map) {
        SysLeave sysLeave = this.selectByPrimaryKey(leaveId);
        SysUser sysUser = userService.selectByUserId(sysLeave.getUserId());
        List<Comment> comments = workFlowService.getHistoryCommentByBusinessId("sysLeave." + leaveId);
        map.put("comments", comments);
        map.put("sysLeave", sysLeave);
        map.put("sysUser", sysUser);
        return comments;
    }

}
