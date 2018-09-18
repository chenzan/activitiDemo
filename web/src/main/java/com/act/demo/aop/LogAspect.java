package com.act.demo.aop;

import com.act.demo.aop.annotation.Log;
import com.act.demo.service.ILogService;
import com.act.demo.support.WebApplicationHelper;
import com.act.demo.util.HttpUtil;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {
    @Autowired
    ILogService logService;


    @After("@annotation(com.act.demo.aop.annotation.Log)")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log log = signature.getMethod().getAnnotation(Log.class);
        if (log != null) {
            logService.log(signature.getMethod(), log, ParseRequest(WebApplicationHelper.getRequest()));
        }
    }

    /**
     * request的log包装
     */
    @Data
    public class RequestLogWrapper {
        public String remoteAddr;
        public String browser;
        public String platform;
    }

    private RequestLogWrapper ParseRequest(HttpServletRequest request) {
        RequestLogWrapper requestLogWrapper = new RequestLogWrapper();
        requestLogWrapper.setRemoteAddr(HttpUtil.getRequestAddr(request));
        requestLogWrapper.setBrowser(HttpUtil.getBrowser(request));
        requestLogWrapper.setPlatform(HttpUtil.getPlatform(request));
        return requestLogWrapper;
    }
}
