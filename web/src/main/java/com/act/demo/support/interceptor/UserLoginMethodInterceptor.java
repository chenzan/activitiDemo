package com.act.demo.support.interceptor;

import com.act.demo.service.ILogService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 代理类切面拦截
 */
@Component("userLoginMethodInterceptor")
public class UserLoginMethodInterceptor implements MethodInterceptor/*MethodBeforeAdvice*/ {
    @Autowired
    ILogService logService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        logService.saveLog("登录");
        return result;
    }
}
