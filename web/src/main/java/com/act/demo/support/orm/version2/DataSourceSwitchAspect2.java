package com.act.demo.support.orm.version2;

import com.act.demo._holder.SpringApplicationContextHelper;
import com.act.demo.support.orm.annotation.DataSourceSwitch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@DependsOn("dataSource")
public class DataSourceSwitchAspect2 {
    SwitcherDataSource switchDataSource;
    //自动切换
    private boolean autoSwitch = true;

    DataSourceSwitchAspect2() {
        switchDataSource = SpringApplicationContextHelper.getBean("dataSource", SwitcherDataSource.class);
    }

    @Pointcut("execution(* com.act.demo.service.impl.*.select*(..))")
    public void pointCut() {

    }

    @Pointcut("@annotation(com.act.demo.support.orm.annotation.DataSourceSwitch)")
    public void pointCut1() {

    }

    @Around("pointCut()||pointCut1()")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            if (this.autoSwitch) {
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                Method method = signature.getMethod();
                DataSourceSwitch dataSourceAnnotation = method.getAnnotation(DataSourceSwitch.class);
                if (dataSourceAnnotation != null) {
                    DataSourceSwitch.Source dataSource = dataSourceAnnotation.dataSource();
                    String nameType = dataSource.getNameType();
                    switchDataSource.switchTo(nameType);
                } else {
                    switchDataSource.switchToSlave();
                }
            }
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        } finally {
            switchDataSource.restoreDefaultDataSource();
        }
    }

}
