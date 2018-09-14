package com.act.demo.service.impl;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysUser;
import com.act.demo.service.IProcessService;
import com.act.demo.util.ProcessDiagramGeneratorUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@Service
public class ProcessServiceImpl implements IProcessService {
    @Resource
    RepositoryService repositoryService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    TaskService taskService;
    @Resource
    HistoryService historyService;
    @Resource
    FormService formService;
    @Resource
    ProcessEngine processEngine;
    @Resource
    SessionContext sessionContext;

    @Override
    public List<Deployment> getDeploymentProcess() {
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .orderByDeploymenTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public List<ProcessDefinition> getDefinitionProcess() {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        return processDefinitionList;
    }

    @Override
    public InputStream getProcessDefResourceImg(String deploymentId, String resourceName) {
        // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        processDiagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(), new ArrayList<>(), "宋体", "微软雅黑", "黑体", null, 2.0);
        return processDiagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(), new ArrayList<>(), "宋体", "微软雅黑", "黑体", null, 2.0)/*repositoryService.getResourceAsStream(deploymentId, resourceName)*/;
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public void startProcessInstance(Object obj, String businessKey, String assignee) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", assignee);
        String key = obj.getClass().getSimpleName();
        runtimeService.startProcessInstanceByKey(key, businessKey, variables);
    }

    @Override
    public List<Task> getPersonalTasks(String name, String processDefKey) {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(name)
                .processDefinitionKey(processDefKey)
                .orderByTaskCreateTime()
                .asc()
                .list();
        return list;
    }

    @Override
    public boolean completeTask(String taskId, Map<String, Object> variables, String comment) {
        boolean processInstanceFinish = false;
//        String taskId, String processInstance, String message
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
        if (!StringUtils.isEmpty(comment)) {
            Authentication.setAuthenticatedUserId(sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class).getUsername());
            //添加意见
            taskService.addComment(taskId, processInstanceId, comment);
        }
        taskService.complete(taskId, variables);
        //流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (processInstance == null) {
            //流程实例执行结束
            processInstanceFinish = true;
        }
        return processInstanceFinish;
    }

    /**
     * 规则   流程.id
     *
     * @param taskId
     * @return
     */
    @Override
    public String getBusinessIdByTaskId(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
        //流程实例id查询到单一结果
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        String businessKey = processInstance.getBusinessKey();
        String businessId = "";
        if (!StringUtils.isEmpty(businessKey)) {
            businessId = businessKey.split("\\.")[1];
        }
        return businessId;
    }

    @Override
    public List<String> getOutComeLineListByTaskId(String taskId) {
        List<String> sequenceNames = new ArrayList<>();
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        //流程定义实体
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        if (flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
            for (SequenceFlow sequenceFlow : outgoingFlows) {
                String name = sequenceFlow.getName();
                if (!StringUtils.isEmpty(name)) {
                    sequenceNames.add(name);
                } else {
                    sequenceNames.add("默认提交");
                }
            }
        }
        return sequenceNames;
    }

    @Override
    public List<Comment> getCommentByTaskId(String taskId) {
        List<Comment> comments = new ArrayList<>();
        //当前任务查询当前流程历史人物
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
//        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .list();
//        if (list != null && list.size() > 0) {
//            for (HistoricTaskInstance historicTaskInstance : list) {
//                String hisTaskId = historicTaskInstance.getId();
//                List<Comment> hisComments = taskService.getTaskComments(hisTaskId);
//                comments.addAll(hisComments);
//            }
//        }
        comments = taskService.getProcessInstanceComments(processInstanceId);
        Collections.reverse(comments);
        return comments;
    }

    @Override
    public List<Comment> getHistoryCommentByBusinessId(String businessKey) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        List<Comment> processInstanceComments = new ArrayList<>();
        if (historicProcessInstance != null) {
            String hisProcessInstanceId = historicProcessInstance.getId();
            processInstanceComments = taskService.getProcessInstanceComments(hisProcessInstanceId);
            Collections.reverse(processInstanceComments);
        }
        return processInstanceComments;
    }

    @Override
    public InputStream getProcessImgByTaskId(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
        InputStream resourceAsStream = null;
        try {
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            // 获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId()
                    .asc()
                    .list();
            // 高亮已经执行流程节点ID集合
            List<String> highLightedActivitiIds = new ArrayList<>();
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                highLightedActivitiIds.add(historicActivityInstance.getActivityId());
            }
            // 获取bpmnModel
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            // 获取流程已发生流转的线ID集合
            List<String> flowIds = ProcessDiagramGeneratorUtil.getExecutedFlows(bpmnModel, historicActivityInstances);

            // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
            ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
            resourceAsStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, flowIds, "宋体", "微软雅黑", "黑体", null, 2.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceAsStream;
    }
}
