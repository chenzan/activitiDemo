package com.act.demo.service;

import com.act.demo.aop.LogAspect;
import com.act.demo.aop.annotation.Log;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public interface ILogService {
    /**
     * AOP日志记录
     *
     * @param method
     * @param log
     * @param requestLogWrapper
     */
    void log(Method method, Log log, LogAspect.RequestLogWrapper requestLogWrapper);

    void saveLog(String content);

}
