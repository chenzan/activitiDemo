package com.act.demo.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IProcessService {
    /**
     * 获取部署信息
     */
    List<Deployment> getDeploymentProcess();

    /**
     * 获取流程定义
     */
    List<ProcessDefinition> getDefinitionProcess();

    /**
     * 获取流程定义中的图
     *
     * @param deploymentId
     * @param resourceName
     * @return
     */
    InputStream getProcessDefResourceImg(String deploymentId, String resourceName);

    /**
     * 删除部署信息
     *
     * @param deploymentId 部署id
     */
    void deleteDeployment(String deploymentId);

    /**
     * 开启一个流程实例
     *
     * @param obj         实力对象
     * @param businessKey 业务key
     * @param assignee    处理人
     */
    void startProcessInstance(Object obj, String businessKey, String assignee);

    /**
     * 获取个人任务列表
     *
     * @param name         个人名称
     * @param processDefId 流程定义id
     * @return
     */
    List<Task> getPersonalTasks(String name, String processDefId);

    /**
     * 完成任务
     *
     * @param taskId    任务id
     * @param variables 变量
     * @param comment   意见
     * @return processInstanceFinish 流程实例是否结束
     */
    boolean completeTask(String taskId, Map<String, Object> variables, String comment);

    /**
     * 根据任务id获取业务id
     *
     * @param taskId 任务id
     * @return
     */
    String getBusinessIdByTaskId(String taskId);

    /**
     * taskId获取ProcessDefinitionEntity 获取连线名称
     *
     * @param taskId
     * @return
     */
    List<String> getOutComeLineListByTaskId(String taskId);

    /**
     * 根据当前任务id获取历史批注信息
     *
     * @param taskId
     * @return
     */
    List<Comment> getCommentByTaskId(String taskId);

    /**
     * 根据业务key查询历史
     * @param businessKey
     * @return
     */
    List<Comment> getHistoryCommentByBusinessId(String businessKey);

    /**
     * 根据任务id查询流程图片
     * @param taskId
     * @return
     */
    InputStream getProcessImgByTaskId(String taskId);
}
