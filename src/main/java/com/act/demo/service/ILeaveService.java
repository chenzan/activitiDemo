package com.act.demo.service;

import com.act.demo.domain.SysLeave;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ILeaveService {

    /**
     * 申请请假
     *
     * @param sysLeave
     */
    void applyLeave(SysLeave sysLeave);

    /**
     * 审批任务
     *
     * @param leaveId   请假业务id
     * @param taskId
     * @param variables
     * @param comment
     */
    void approvalLeave(Integer leaveId, String taskId, Map<String, Object> variables, String comment);

    /**
     * 查询所有请假信息
     *
     * @return
     */
    List<SysLeave> selectAll(Integer userId);

    /**
     * 根据用户id查询
     *
     * @param userId
     * @return
     */
    List<SysLeave> selectByUserId(int userId);

    /**
     * 获取请假记录 批注
     *
     * @param leaveId
     * @param map
     * @return
     */
    List<Comment> selectLeaveLog(Integer leaveId, Map map);

    /**
     * 获取某个请假记录
     *
     * @param integer
     * @return
     */
    SysLeave selectByLeaveId(Integer integer);

    /**
     * 获取个人所有请假记录
     * 和审批批注
     */
    List<SysLeave> getPersonalLeaves();

    /**
     * 获取我的当前请假任务
     * @return
     */
    List<Task> getPersonalLeaveTasks();

}
