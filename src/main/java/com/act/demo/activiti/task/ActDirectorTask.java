package com.act.demo.activiti.task;

import com.act.demo._holder.SpringApplicationContextHelper;
import com.act.demo.service.IUserService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ActDirectorTask implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        IUserService userService = SpringApplicationContextHelper.getBean(IUserService.class);
        delegateTask.setAssignee("wangwu");
    }
}
