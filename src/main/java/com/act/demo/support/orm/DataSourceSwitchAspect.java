package com.act.demo.support.orm;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceSwitchAspect{

    @Pointcut("execution(* com.act.demo.service.impl.*.select*(..))")
    public void pointCut() {

    }
    @Pointcut("@annotation(com.act.demo.support.orm.annotation.DataSourceSwitch)")
    public void pointCut1() {

    }
    @Around("pointCut()||pointCut1()")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DbContextHolder.clearDbType();
        }
    }

}
