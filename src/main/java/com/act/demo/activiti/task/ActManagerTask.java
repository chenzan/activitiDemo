package com.act.demo.activiti.task;

import com.act.demo._holder.SpringContextHolder;
import com.act.demo.service.IUserService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.io.Serializable;

public class ActManagerTask implements TaskListener, Serializable {

    @Override
    public void notify(DelegateTask delegateTask) {
        IUserService userService = SpringContextHolder.getBean(IUserService.class);
        delegateTask.setAssignee("lisi");
    }
}
