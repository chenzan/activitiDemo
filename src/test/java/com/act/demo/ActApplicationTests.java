package com.act.demo;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActApplicationTests {
    @Resource
    RepositoryService repositoryService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    TaskService taskService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void queryAllDeploymentProcess() {
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .orderByDeploymentId()
                .asc()
                .list();
        list.stream().map(Deployment::getId).forEach(System.out::println);
    }

    @Test
    public void startProcess() {
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");
        System.out.println("流程实例ID:" + leave.getId());
        System.out.println("流程定义ID:" + leave.getProcessDefinitionId());
    }
    @Test
    public void queryPersonalTask() {
        String assignee = "";
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateOrAssigned(assignee)
                .orderByTaskCreateTime()
                .desc()
                .list();
        for (Task task:list){
            System.out.println("ID:" + task.getId());
            System.out.println("NAME:" + task.getName());
            System.out.println("ASSIGNEE:" + task.getAssignee());
        }
    }
    @Test
    public void executeObject() {
    }
}
