package com.act.demo.activiti.task;

import com.act.demo._holder.SpringApplicationContextHelper;
import com.act.demo.common.ConstantValue;
import com.act.demo.domain.SysUser;
import com.act.demo.service.IUserService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.io.Serializable;

public class ActManagerTask implements TaskListener, Serializable {

    @Override
    public void notify(DelegateTask delegateTask) {
        IUserService userService = SpringApplicationContextHelper.getBean(IUserService.class);
        SysUser sysUser = userService.selectByRole(ConstantValue.ROLE_MANAGER);
        delegateTask.setAssignee(sysUser.getUsername());
    }
}
