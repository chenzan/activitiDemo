package com.act.demo.service.impl;

import com.act.demo.aop.LogAspect;
import com.act.demo.aop.annotation.Log;
import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysLog;
import com.act.demo.domain.SysUser;
import com.act.demo.mapper.SysLogMapper;
import com.act.demo.service.ILogService;
import com.act.demo.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Service
public class LogServiceImpl extends BaseService<SysLog, SysLogMapper> implements ILogService {

    @Autowired
    SessionContext sessionContext;

    @Override
    public void log(Method method, Log logAnnotation, LogAspect.RequestLogWrapper requestLogWrapper) {
        String content = logAnnotation.content();
        Log.Target target = logAnnotation.target();
        Log.Level level = logAnnotation.level();
        String logMsg = logAnnotation.name() + ":" + content;
        if (target == Log.Target.CONSOLE || target == Log.Target.ALL) {
            if (level == Log.Level.DEBUG) {
                log.debug(logMsg);
            } else if (level == Log.Level.INFO) {
                log.info(logMsg);
            } else if (level == Log.Level.ERROR) {
                log.error(logMsg);
            }
        }
        if (target == Log.Target.DB || target == Log.Target.ALL) {
            String username = "unKnow";
            SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
            if (null != user) {
                username = user.getUsername();
            }
            SysLog sysLog = new SysLog();
            if (level == Log.Level.DEBUG) {
                sysLog.setLevel(3);
            } else if (level == Log.Level.INFO) {
                sysLog.setLevel(2);
            } else if (level == Log.Level.ERROR) {
                sysLog.setLevel(1);
            }
            sysLog.setContent(content);
            sysLog.setUser(username);
            sysLog.setIp(requestLogWrapper.remoteAddr);
            sysLog.setBrowser(requestLogWrapper.browser);
            sysLog.setPlatform(requestLogWrapper.platform);
            sysLog.setLogTime(new Date());
            sysLog.setSource(method.getDeclaringClass().getSimpleName());
            this.insertSelective(sysLog);
        }
    }

    @Override
    public void saveLog(String content) {
        String username = "unKnow";
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);
        if (null != user) {
            username = user.getUsername();
        }
        SysLog sysLog = new SysLog();
        sysLog.setLevel(2);
        sysLog.setContent(content);
        sysLog.setUser(username);
//        sysLog.setIp(requestLogWrapper.remoteAddr);
//        sysLog.setBrowser(requestLogWrapper.browser);
//        sysLog.setPlatform(requestLogWrapper.platform);
        sysLog.setLogTime(new Date());
//        sysLog.setSource(method.getDeclaringClass().getSimpleName());
        this.insertSelective(sysLog);
    }
}
